package git.dimitrikvirik.ws.clients.photoappwebclient.controllers;



import git.dimitrikvirik.ws.clients.photoappwebclient.params.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

@Controller
@RequestMapping("/message")
public class MessageController {


    @Autowired
    RestTemplate restTemplate;

    @PostMapping("/send")
    public String send(@RequestParam String text, HttpServletRequest request) throws URISyntaxException {

  /*

        String response = webClient.post()
                .uri(new URI("http://localhost:8082/message/send"))
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(text))
                .retrieve()
                .bodyToMono(String.class)
                .block();
        String referer = request.getHeader("Referer");
        return "redirect:"+ referer;*/
        return null;
    }
    @GetMapping("/all")
    public ModelAndView getAll(){
     /*   List<MessageDTO> messages =	webClient.get().uri("http://localhost:8082/message/all").retrieve().bodyToMono(
                new ParameterizedTypeReference<List<MessageDTO>>(){}).block();
        ModelAndView modelAndView = new ModelAndView("messages");
        modelAndView.addObject("messages", messages);
*/
        return null;
    }
    @GetMapping("/del/{id}")
    public void deleteMsg(@PathVariable long id, HttpServletResponse httpServletResponse){
        /*	webClient.delete().uri("http://localhost:8082/message/del/" + id).retrieve().bodyToMono(
                new ParameterizedTypeReference<String>(){}).block();
        httpServletResponse.setHeader("Location", "http://localhost:8087/message/all");
        httpServletResponse.setStatus(302);*/

    }

}
