package mx.uam.ayd.proyecto.presentacion.listarUsuarios;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.http.HttpClient;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import mx.uam.ayd.proyecto.negocio.ServicioUsuario;
import mx.uam.ayd.proyecto.negocio.modelo.Usuario;
import mx.uam.ayd.proyecto.servicios.UsuarioRestController;


@Controller
@Slf4j
public class ListarUsuariosController {
	
	
	@Autowired
	private UsuarioRestController URC;

	/**
	 * 
	 * Método invocado cuando se hace una petición GET a la ruta
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/listarUsuarios", method = RequestMethod.GET)
    public String getListarUsuario(Model model) {
    	
    		log.info("Iniciando Historia de usuario: Listar usuarios");
        
    		//LLamamos el servicio
    		//List <Usuario> usuarios = servicioUsuario.recuperaUsuarios();
    		
    		//model.addAttribute("usuarios",usuarios);
    		
    		/*for ( int i =0; i < usuarios.size(); i++ ) {
    			model.addAttribute("usuario", usuarios.get(i));
    		}*/
    		
    		return "vistaListarUsuarios/ListarUsuarios";
    }
	

}
