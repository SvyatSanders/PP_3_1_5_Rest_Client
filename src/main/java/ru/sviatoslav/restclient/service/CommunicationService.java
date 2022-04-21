package ru.sviatoslav.restclient.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.sviatoslav.restclient.entity.User;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * source https://www.tutorialspoint.com/spring_boot/spring_boot_rest_template.htm
 */
@Component
public class CommunicationService {

    @PostConstruct
    private void initApp() {
        // 1.   JSESSIONID=D1AA5CFD84B57479859D1A2B1F8E405E; Path=/; HttpOnly
        ResponseEntity<List<User>> allUsers = getAllUsers();
        System.out.println(allUsers.getBody());

        // 2.   5ebfeb
        User newUser = new User(3, "James", "Brown", 29);
        ResponseEntity<String> responseFromSaveUser = saveUser(newUser);
        System.out.println(responseFromSaveUser.getBody());

        // 3.   e7cb97
        newUser.setName("Thomas");
        newUser.setLastName("Shelby");
        ResponseEntity<String> responseFromEditUser = editUser(newUser);
        System.out.println(responseFromEditUser.getBody());

        // 4.   5dfcf9
        ResponseEntity<String> responseFromDeleteUser = deleteUser(3);
        System.out.println(responseFromDeleteUser.getBody());

    }

    @Autowired
    private RestTemplate restTemplate;
    private List<String> cookies;

    private final String URL = "http://94.198.50.185:7081/api/users";

    public ResponseEntity<List<User>> getAllUsers() {
        ResponseEntity<List<User>> responseEntity =
                restTemplate.exchange(URL, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<User>>() {});

        cookies = responseEntity.getHeaders().get("Set-Cookie").stream().peek(x -> System.out.println(x)).collect(Collectors.toList());
        return responseEntity;
    }

    public ResponseEntity<String> saveUser(User user) {

            HttpHeaders headers = new HttpHeaders();
            headers.set("Cookie", cookies.stream().collect(Collectors.joining(";"))); // перевели List<String> cookies в String
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            System.out.println(headers);

            HttpEntity<User> httpEntity = new HttpEntity<>(user, headers);
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(URL, httpEntity, String.class);
            return responseEntity;
    }

    public ResponseEntity<String> editUser(User user) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", cookies.stream().collect(Collectors.joining(";"))); // перевели List<String> cookies в String
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity<User> httpEntity = new HttpEntity<>(user, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.PUT, httpEntity, String.class);
        return responseEntity;
    }

    public ResponseEntity<String> deleteUser(int id) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", cookies.stream().collect(Collectors.joining(";"))); // перевели List<String> cookies в String
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity<User> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL + "/" + id, HttpMethod.DELETE, httpEntity, String.class);
        return responseEntity;
    }
}

/*
 1. Получить список всех пользователей
 2. Когда вы получите ответ на свой первый запрос, вы должны сохранить свой session id, который получен через cookie.
    Вы получите его в заголовке ответа set-cookie. Поскольку все действия происходят в рамках одной сессии,
    все дальнейшие запросы должны использовать полученный session id ( необходимо использовать заголовок в последующих запросах )
 3. Сохранить пользователя с id = 3, name = James, lastName = Brown, age = на ваш выбор.
    В случае успеха вы получите первую часть кода.
 4. Изменить пользователя с id = 3. Необходимо поменять name на Thomas, а lastName на Shelby.
    В случае успеха вы получите еще одну часть кода.
 5. Удалить пользователя с id = 3. В случае успеха вы получите последнюю часть кода.
 */