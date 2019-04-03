package com.app.rest.greyseal.controller;


import com.app.rest.greyseal.dto.UserDTO;
import com.app.rest.greyseal.model.User;
import com.app.rest.greyseal.service.UserService;
import com.app.rest.greyseal.util.GsonUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.app.rest.greyseal.mockdataloader.MockUserDataLoader.getUsers;
import static com.app.rest.greyseal.mockdataloader.MockUserDataLoader.getUsersDTO;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

/**
 * @WebMvcTest::Loads only the controller layer of the application.
 * This will scan only the @Controller/ @RestController annotation and will not load the fully ApplicationContext.
 * Used for controller layer Unit testing unlike @SpringBootTest which is widely used for Integration Testing purpose.
 * @WebMvcTest does::Auto-configure Spring MVC, Jackson, Gson, Message converters etc.
 * Load relevant components (@Controller, @RestController, @JsonComponent etc)
 * Configure MockMVC
 * @WebMvcTest is limited (bound) to a single controller. UserController.class in this case.
 * and is used in combination with @MockBean to provide mock implementations for required collaborators.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(value = UserController.class, secure = false)
public class UserControllerTest {

    /**
     * Main entry point for server-side Spring MVC test support.
     * This performs a request and return a type that allows chaining further
     * actions, such as asserting expectations, on the result.
     * MockMVC offers a powerful way to quickly test MVC controllers without needing to start a full HTTP server
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * Used to add mock objects to the Spring application context.
     * The mock will replace any existing bean of the same type in the application context.
     */
    @MockBean
    private UserService userService;


    @Test
    public void getAll() throws Exception {
        /**
         * Returns mock users when when(userService.getAll()) is invoked
         * User service is also mocked
         */
        when(userService.getAll()).thenReturn(getUsers());

        /**
         * Build the mock api request with the required parameters
         * Try the actual api to get the required parameters. Use POSTMAN [https://www.getpostman.com/] if required.
         */
        final RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/user/all")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        /**
         * This triggers the function or method under test
         * Provides result of the executed request
         */
        final MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        //This returns the http servlet response with status code, headers, data etc.
        final MockHttpServletResponse response = result.getResponse();

        //Assert to verify the Http Status code
        Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());

        //Assert to verify the content
        JSONAssert.assertEquals(GsonUtil.toJsonString(getUsersDTO()), result.getResponse().getContentAsString(), false);
    }

    @Test
    public void get() throws Exception {
        //Returns a mock user
        final User user = getUsers().stream().findFirst().get();
        final String email = "Email";

        /**
         * Returns mock user when when(userService.getByEmail(anyString())) is invoked
         * User service is also mocked
         */
        when(userService.getByEmail(anyString())).thenReturn(user);

        /**
         * Build the mock api request with the required parameters
         * Try the actual api to get the required parameters. Use POSTMAN [https://www.getpostman.com/] if required.
         */
        final RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user")
                .param("email", email)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        /**
         * This triggers the function or method under test
         * Provides result of the executed request
         */
        final MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        //This returns the http servlet response with status code, headers, data etc.
        final MockHttpServletResponse response = result.getResponse();

        //Assert to verify the Http Status code
        Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());

        //Assert to verify the content
        JSONAssert.assertEquals(GsonUtil.toJsonString(getUsersDTO().stream().findFirst().get()), result.getResponse().getContentAsString(), false);
    }

    @Test
    public void create() throws Exception {
        /**
         * This is the request body::@RequestBody final UserDTO userDTO
         * userDTO.setId(null) as this is the create request
         */
        final UserDTO userDTO = getUsersDTO().stream().findFirst().get();
        userDTO.setId(null);

        /**
         * Returns mock user when userService.create(userDTO) is invoked
         * User service is also mocked
         */
        when(userService.create(any(User.class))).thenReturn(getUsers().stream().findFirst().get());

        /**
         * Build the mock api request with the required parameters
         * Try the actual api to get the required parameters. Use POSTMAN [https://www.getpostman.com/] if required.
         */
        final RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user")
                .content(GsonUtil.toJsonString(userDTO))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        /**
         * This triggers the function or method under test
         * Provides result of the executed request
         */
        final MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        //This returns the http servlet response with status code, headers, data etc.
        final MockHttpServletResponse response = result.getResponse();

        //Assert to verify the HttpStatus
        Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());

        //Assert to verify the Content Type
        Assert.assertEquals(MediaType.APPLICATION_JSON_UTF8_VALUE.toString(), response.getContentType());

        //Assert to verify the content
        JSONAssert.assertEquals(GsonUtil.toJsonString(getUsersDTO().stream().findFirst().get()), result.getResponse().getContentAsString(), false);
    }

    @Test
    public void update() throws Exception {
        /**
         * This is the request body::@RequestBody final UserDTO userDTO
         * userDTO.setId(null) as this is the create request
         */
        final UserDTO userDTO = getUsersDTO().stream().findFirst().get();
        userDTO.setFirstName("Updated First Name");

        //Returns a mock user for userService.getByEmail(userDTO.getEmail()) call
        final User user = getUsers().stream().findFirst().get();

        /**
         * Returns mock user when when(userService.getByEmail(userDTO.getEmail())) is invoked
         * User service is also mocked
         */
        when(userService.getByEmail(userDTO.getEmail())).thenReturn(user);

        /**
         * Returns mock user when when(userService.update(any(User.class))) is invoked
         * User service is also mocked
         */
        when(userService.create(any(User.class))).thenReturn(getUsers().stream().findFirst().get());

        /**
         * Build the mock api request with the required parameters
         * Try the actual api to get the required parameters. Use POSTMAN [https://www.getpostman.com/] if required.
         */
        final RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/user")
                .content(GsonUtil.toJsonString(userDTO))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        /**
         * This triggers the function or method under test
         * Provides result of the executed request
         */
        final MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        //This returns the http servlet response with status code, headers, data etc.
        final MockHttpServletResponse response = result.getResponse();

        //Assert to verify the HttpStatus
        Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());

        //Assert to verify the Content Type
        Assert.assertEquals(MediaType.APPLICATION_JSON_UTF8_VALUE.toString(), response.getContentType());

        //Assert to verify the content
        JSONAssert.assertEquals(GsonUtil.toJsonString(getUsersDTO().stream().findFirst().get()), result.getResponse().getContentAsString(), false);
    }

    @Test
    public void updateUserNotFound() throws Exception {
        /**
         * This is the request body::@RequestBody final UserDTO userDTO
         * userDTO.setId(null) as this is the create request
         */
        final UserDTO userDTO = getUsersDTO().stream().findFirst().get();
        userDTO.setFirstName("Updated First Name");

        //Returns a null for a Bad Request or User not found request
        final User user = null;

        /**
         * Returns mock user when when(userService.getByEmail(userDTO.getEmail())) is invoked
         * User service is also mocked
         */
        when(userService.getByEmail(userDTO.getEmail())).thenReturn(user);

        /**
         * Build the mock api request with the required parameters
         * Try the actual api to get the required parameters. Use POSTMAN [https://www.getpostman.com/] if required.
         */
        final RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/user")
                .content(GsonUtil.toJsonString(userDTO))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        /**
         * This triggers the function or method under test
         * Provides result of the executed request
         */
        final MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        //This returns the http servlet response with status code, headers, data etc.
        final MockHttpServletResponse response = result.getResponse();

        //Assert to verify the HttpStatus
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    public void delete() throws Exception {
        /**
         * This is the request body::@RequestBody final UserDTO userDTO
         * userDTO.setId(null) as this is the create request
         */
        final UserDTO userDTO = getUsersDTO().stream().findFirst().get();

        //Returns a mock user for userService.getByEmail(userDTO.getEmail()) call
        final User user = getUsers().stream().findFirst().get();

        /**
         * Returns mock user when when(userService.getByEmail(userDTO.getEmail())) is invoked
         * User service is also mocked
         */
        when(userService.getByEmail(userDTO.getEmail())).thenReturn(user);

        /**
         * Returns mock user when when(userService.update(any(User.class))) is invoked
         * User service is also mocked
         */
        when(userService.delete(any(User.class))).thenReturn(true);

        /**
         * Build the mock api request with the required parameters
         * Try the actual api to get the required parameters. Use POSTMAN [https://www.getpostman.com/] if required.
         */
        final RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/user")
                .param("email", "Email")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        /**
         * This triggers the function or method under test
         * Provides result of the executed request
         */
        final MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        //This returns the http servlet response with status code, headers, data etc.
        final MockHttpServletResponse response = result.getResponse();

        //Assert to verify the HttpStatus
        Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());

        //Assert to verify the Content Type
        Assert.assertEquals(MediaType.APPLICATION_JSON_UTF8_VALUE.toString(), response.getContentType());

        //Assert to verify the content
        Assert.assertEquals("true", response.getContentAsString());
    }

    @Test
    public void deleteUserNotFound() throws Exception {
        /**
         * This is the request body::@RequestBody final UserDTO userDTO
         * userDTO.setId(null) as this is the create request
         */
        final UserDTO userDTO = getUsersDTO().stream().findFirst().get();
        userDTO.setFirstName("ToDelete");

        //Returns a null for a Bad Request or User not found request
        final User user = null;

        /**
         * Returns mock user when when(userService.getByEmail(userDTO.getEmail())) is invoked
         * User service is also mocked
         */
        when(userService.getByEmail(userDTO.getEmail())).thenReturn(user);

        /**
         * Build the mock api request with the required parameters
         * Try the actual api to get the required parameters. Use POSTMAN [https://www.getpostman.com/] if required.
         */
        final RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/user")
                .param("email", "Email")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        /**
         * This triggers the function or method under test
         * Provides result of the executed request
         */
        final MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        //This returns the http servlet response with status code, headers, data etc.
        final MockHttpServletResponse response = result.getResponse();

        //Assert to verify the HttpStatus
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }
}
