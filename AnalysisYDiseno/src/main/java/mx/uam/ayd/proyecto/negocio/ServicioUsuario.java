package mx.uam.ayd.proyecto.negocio;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.extern.slf4j.Slf4j;
import mx.uam.ayd.proyecto.datos.GrupoRepository;
import mx.uam.ayd.proyecto.datos.UsuarioRepository;
import mx.uam.ayd.proyecto.dto.UsuarioDto;
import mx.uam.ayd.proyecto.negocio.modelo.Grupo;
import mx.uam.ayd.proyecto.negocio.modelo.Usuario;

@Slf4j
@Service
public class ServicioUsuario {
	
	@Autowired 
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private GrupoRepository grupoRepository;
	
	/**
	 * Permite agregar un usuario
	 *  
	 * @param usuarioDto
	 * @return dto con el id del usuario
	 */
	public UsuarioDto agregaUsuario(UsuarioDto usuarioDto) {
		
		// Regla de negocio: No se permite agregar dos usuarios con el mismo nombre y apellido
		
		String nombre = usuarioDto.getNombre();
		String apellido = usuarioDto.getApellido();
		
		
		Usuario usuario = usuarioRepository.findByNombreAndApellido(nombre, apellido);
		
		if(usuario != null) {
			throw new IllegalArgumentException("Ese usuario ya existe");
		}
		
		Optional <Grupo> optGrupo = grupoRepository.findById(usuarioDto.getGrupo());
		
		if(optGrupo.isEmpty()) {
			throw new IllegalArgumentException("No se encontró el grupo");
		}
				
		Grupo grupo = optGrupo.get();
		
		log.info("Agregando usuario nombre: "+nombre+" apellido:"+apellido);
		
		usuario = new Usuario();
		usuario.setNombre(nombre);
		usuario.setApellido(apellido);
		usuario.setGrupo(grupo);
		
		usuarioRepository.save(usuario);
		
		grupo.addUsuario(usuario);
		
		
		grupoRepository.save(grupo);
		
		usuarioDto.creaDto(usuario);
		
		return usuarioDto;

	}


	/**
	 * Recupera todos los usuarios existentes
	 * 
	 * @return Una lista con los usuarios (o lista vacía)
	 */
	public List <UsuarioDto> recuperaUsuarios() {

		
		System.out.println("usuarioRepository = "+usuarioRepository);
		
		List <UsuarioDto> usuarios = new ArrayList<>();
		
		for(Usuario usuario:usuarioRepository.findAll()) {
			usuarios.add(UsuarioDto.creaDto(usuario));
		}
				
		return usuarios;
	}
	
	public UsuarioDto recuperaUsuario(long idUsuario) {
		
		Optional<Usuario> optUsuario = usuarioRepository.findById(idUsuario);

		if (optUsuario.isEmpty()) {
			throw new IllegalArgumentException("No se encontró el usuario");
		}
		
		
		UsuarioDto usuarioDto = UsuarioDto.creaDto(optUsuario.get());
		
		return usuarioDto;
	}
	
	public boolean borraUsuario(long idUsuario){
		
		
		log.info("Se ba a borrar el usuario con id: " + idUsuario);
		
		Optional<Usuario> optUsuario = usuarioRepository.findById(idUsuario);
		
		
		if (optUsuario.isEmpty()) {
			throw new IllegalArgumentException("No se encontró el usuario");
		}
		
		try {
		
		
		usuarioRepository.deleteById(idUsuario);
		
		
		
		} catch(Exception ex) {
			
			HttpStatus status = HttpStatus.BAD_REQUEST;
			throw new ResponseStatusException(status, ex.getMessage());
		}
		
				
		return true;
	}
	
	
	public UsuarioDto actualizaUsuario(UsuarioDto usuarioDtoNuevo, long idUsuario) {
		
		Optional<Usuario> optUsuario = usuarioRepository.findById(idUsuario);
		UsuarioDto usuarioDtoActulizado = new UsuarioDto();
		
		
		if (optUsuario.isEmpty()) {
			throw new IllegalArgumentException("No se encontró el usuario");
		}
		
		Usuario usuarioActulizado = optUsuario.get();
		
		String nuevoNombre = usuarioDtoNuevo.getNombre();
		
		String nuevoApellido = usuarioDtoNuevo.getApellido();
		
		int nuevaEdad = usuarioDtoNuevo.getEdad();
		
		usuarioActulizado.setNombre(nuevoNombre);
		usuarioActulizado.setApellido(nuevoApellido);
		usuarioActulizado.setEdad(nuevaEdad);
		
		usuarioRepository.save(usuarioActulizado);
		
		usuarioDtoActulizado.creaDto(usuarioActulizado);
		
		
		return usuarioDtoActulizado;
	}

}
