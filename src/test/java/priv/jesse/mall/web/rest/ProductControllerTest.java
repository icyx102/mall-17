package priv.jesse.mall.web.rest;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import priv.jesse.mall.service.ClassificationService;
import priv.jesse.mall.service.ProductService;
import priv.jesse.mall.service.TagService;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 单元测试
 * <pre>
 *     测试 Rest 接口
 * </pre>
 */
@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void page() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/api/product/page"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void save() throws Exception {
        String jsonBody = """
                {
                    "title": "商品标题",
                    "marketPrice": 300.0,
                    "shopPrice": 320.0,
                    "image": "商品图片",
                    "desc": "测试商品",
                    "isHot": 1,
                    "pdate": "2024-06-06 17:33:03",
                    "categorySec": {
                        "id": 11
                    },
                    "tags": [
                        {
                            "id": 1
                        },
                        {
                            "id": 4
                        },
                        {
                            "id": 3
                        },
                        {
                            "id": 2
                        }
                    ]
                }
                """;
        mockMvc.perform(MockMvcRequestBuilders.post("/api/product").contentType(MediaType.APPLICATION_JSON).content(jsonBody))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void detail() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/api/product/detail/1"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void delete() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/product/38"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}