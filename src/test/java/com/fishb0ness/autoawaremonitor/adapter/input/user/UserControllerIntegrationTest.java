package com.fishb0ness.autoawaremonitor.adapter.input.user;


import com.fishb0ness.autoawaremonitor.adapter.IntegrationTest;
import com.fishb0ness.autoawaremonitor.application.UserUseCases;
import com.fishb0ness.autoawaremonitor.domain.user.User;
import com.fishb0ness.autoawaremonitor.domain.user.UserId;
import com.fishb0ness.autoawaremonitor.domain.user.UserName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doReturn;


public class UserControllerIntegrationTest extends IntegrationTest {

    @SpyBean
    private UserUseCases userUseCases;
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void testPostUser() throws Exception {
        User user = new User(new UserId(), new UserName("John"));
        doReturn(user).when(userUseCases).createUser(user.getUserName());

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"" + user.getUserName().name() + "\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(user.getId().id().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is(user.getUserName().name())));
    }

    @Test
    public void testGetById() throws Exception {
        UserId userId = new UserId();
        doReturn(Optional.empty()).when(userUseCases).getUserById(userId);

        mockMvc.perform(MockMvcRequestBuilders.get("/user?id=" + userId.id())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testGetByIdFindUser() throws Exception {
        UserId userId = new UserId();
        doReturn(Optional.of(new User(userId, new UserName("John")))).when(userUseCases).getUserById(userId);

        mockMvc.perform(MockMvcRequestBuilders.get("/user?id=" + userId.id())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(userId.id().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is("John")));
    }

    @Test
    public void testGetAll() throws Exception {
        User user = new User(new UserId(), new UserName("John"));
        doReturn(List.of(user)).when(userUseCases).getAllUsers();

        mockMvc.perform(MockMvcRequestBuilders.get("/getAll")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", is(user.getId().id().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", is(user.getUserName().name())));
    }
}
