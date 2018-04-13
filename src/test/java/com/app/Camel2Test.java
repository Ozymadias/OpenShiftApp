package com.app;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class Camel2Test {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void sayHelloTest() {
        // Call the REST API
        ResponseEntity<String> response = restTemplate.getForEntity("rest:get:hello?name=A", String.class);
        String s = response.getBody();
        assertThat(s.equals("Hello World"));
    }
}
