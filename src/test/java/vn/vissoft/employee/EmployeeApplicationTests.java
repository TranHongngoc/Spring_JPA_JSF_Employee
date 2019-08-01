package vn.vissoft.employee;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeApplicationTests {

    @Test
    public void contextLoads() {
    }

//    @Test
//    @WithMockUser(roles = "USER")
//    public void getsCookies() throws Exception {
//        mvc.perform(get("/api/cookies")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.[0].flavour", is("chocolate")))
//                .andExpect(jsonPath("$.[1].flavour", is("vanilla")))
//                .andExpect(status().isOk());
//    }

}
