package com.example.userapi.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@ExtendWith(OutputCaptureExtension.class)
class UserControllerTest {

    private static final String USER_JSON = """
            {"name": "Ana", "email": "ana@example.com"}
            """;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createUser_returns201AndLogsMessage(CapturedOutput output) throws Exception {
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(USER_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Ana"))
                .andExpect(jsonPath("$.email").value("ana@example.com"));

        assertThat(output).contains("Creando usuario");
    }

    @Test
    void getUser_returns200AndLogsMessage(CapturedOutput output) throws Exception {
        mockMvc.perform(get("/api/users/7"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(7))
                .andExpect(jsonPath("$.name").value("Usuario 7"));

        assertThat(output).contains("Consultando usuario con id 7");
    }

    @Test
    void updateUser_returns200AndLogsMessage(CapturedOutput output) throws Exception {
        mockMvc.perform(put("/api/users/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(USER_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.name").value("Ana"));

        assertThat(output).contains("Actualizando usuario con id 3");
    }

    @Test
    void deleteUser_returns204AndLogsMessage(CapturedOutput output) throws Exception {
        mockMvc.perform(delete("/api/users/5"))
                .andExpect(status().isNoContent());

        assertThat(output).contains("Eliminando usuario con id 5");
    }
}
