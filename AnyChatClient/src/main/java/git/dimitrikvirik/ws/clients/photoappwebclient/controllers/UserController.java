package git.dimitrikvirik.ws.clients.photoappwebclient.controllers;





import git.dimitrikvirik.ws.clients.photoappwebclient.params.UserDTO;
import git.dimitrikvirik.ws.clients.photoappwebclient.params.UserRegistration;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.ParameterizedTypeReference;

import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.ModelAndView;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedHashMap;


@RestController
@RequestMapping("/user")
public class UserController {



	@Autowired
	WebClient webClient;

	@GetMapping
	public ModelAndView status(){

		ModelAndView modelAndView = new ModelAndView("user");
		String url = "http://localhost:8082/user";

		UserDTO user = webClient.get().uri(url).retrieve().bodyToMono(
				new	ParameterizedTypeReference<UserDTO>() {}
		).block();

		System.out.println(user);
		assert user != null;
		modelAndView.addObject("user",user);

		return modelAndView;
	}
	@GetMapping("/check/dev")
	public String statusDev(){
		String url = "http://localhost:8082/user/check/dev";
		return webClient.get().uri(url).retrieve().bodyToMono(
				new	ParameterizedTypeReference<String>() {}
		).block();
	}
	@PostMapping("/create")
	public String createUser(@ModelAttribute UserRegistration userRegistration) throws URISyntaxException {
		String response = webClient.post()
				.uri(new URI("http://localhost:8082/api/auth/create"))
				.contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromValue(userRegistration))
				.retrieve()
				.bodyToMono(String.class)
				.block();
		assert response != null;
		if(!response.equals("success")){
			return "Something went wrong...";
		}
			return response;
	}

	@GetMapping("/registration")
	public ModelAndView registrationForm(){
		var registration = new ModelAndView("registration");
		registration.getModel().put("UserRegistration", new UserRegistration());
		return registration;
	}
	@GetMapping("/expire")
	public String experience() throws URISyntaxException {
		String response =  webClient.put().uri(new URI("http://localhost:8082/user/expire")).retrieve().bodyToMono(
				new	ParameterizedTypeReference<String>() {}
		).block();
		return  response;

	}

}
