package com.example.study.repository;

import com.example.study.StudyApplicationTests;
import com.example.study.model.entity.Item;
import com.example.study.model.enumClass.ItemStatus;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

public class ItemRepositoryTest extends StudyApplicationTests {

    @Autowired
    private ItemRepository itemRepository;


    @Test
    public void create() {
        Item item = new Item();
        item.setName("삼성 노트북");
        item.setTitle("삼성 노트북 s100");
        item.setStatus(ItemStatus.REGISTERED);
        item.setContent("2019년형입니다.");
        item.setPrice(BigDecimal.valueOf(1500000000));
        item.setBrandName("삼성");
        item.setRegisteredAt(LocalDateTime.now());
        item.setCreatedAt(LocalDateTime.now());
        item.setCreatedBy("Partner01");

        Item newItem = itemRepository.save(item);
        Assert.assertNotNull(newItem);
    }

    @Test
    public void read() {
        Long id = 1L;

        Optional<Item> item = itemRepository.findById(id);

        item.ifPresent(selectedItem -> {
            System.out.println("selectedItem : " + selectedItem);
        });

        Assert.assertNotNull(item);
    }
}
