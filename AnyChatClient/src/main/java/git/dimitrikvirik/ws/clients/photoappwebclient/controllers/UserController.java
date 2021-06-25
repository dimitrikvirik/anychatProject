package git.dimitrikvirik.ws.clients.photoappwebclient.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import git.dimitrikvirik.ws.clients.photoappwebclient.params.UserDTO;
import git.dimitrikvirik.ws.clients.photoappwebclient.params.UserRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.net.URISyntaxException;
import java.util.Map;


@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	RestTemplate restTemplate;


	@GetMapping
	public String status(){


		ModelAndView modelAndView = new ModelAndView("user");
		UserDTO user = null;

		restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor("dima@mail.ru", "123"));
		try {
			user = restTemplate.getForObject("http://localhost:8082/user", UserDTO.class);
		}
		catch (HttpServerErrorException e){
			return 	e.getResponseBodyAsString();
		}
		assert user != null;
		return  user.toString();
		/*

		System.out.println(user);
		assert user != null;
		modelAndView.addObject("user",user);
		return modelAndView;*/
	}
	@GetMapping("/check/dev")
	public String statusDev(){
	/*	String url = "http://localhost:8082/user/check/dev";
		return webClient.get().uri(url).retrieve().bodyToMono(
				new	ParameterizedTypeReference<String>() {}
		).block();*/
		return null;
	}
	@PostMapping("/create")
	public String createUser(@ModelAttribute UserRegistration userRegistration) throws URISyntaxException {
/*		String response = webClient.post()
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
			return response;*/
		return null;
	}

	@GetMapping("/registration")
	public ModelAndView registrationForm(){
		var registration = new ModelAndView("registration");
		registration.getModel().put("UserRegistration", new UserRegistration());
		return registration;
	}
	@GetMapping("/expire")
	public String experience() throws URISyntaxException {
	/*	String response =  webClient.put().uri(new URI("http://localhost:8082/user/expire")).retrieve().bodyToMono(
				new	ParameterizedTypeReference<String>() {}
		).block();
		return  response;*/
		return null;

	}

}
