package com.project.ACG.controller;

import com.project.ACG.entity.Greeting;
import com.project.ACG.entity.Message;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetingController {

  @MessageMapping("/hello")
  @SendTo("/topic/greetings")
  public Greeting greeting(Message message) throws Exception {
    Thread.sleep(1000);

		// 채팅 메세지 형식 구성
    SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
    Date now = new Date();
    String currentTime = format.format(now);

    String greetingMessage = null;

    if (message.getUserId() != null && !message.getUserId().isEmpty()) {
      greetingMessage = "[" + HtmlUtils.htmlEscape(message.getUserId()) + "] ";
    }

    greetingMessage += HtmlUtils.htmlEscape(message.getName() + " - " + currentTime);

    return new Greeting(greetingMessage);
  }
}
