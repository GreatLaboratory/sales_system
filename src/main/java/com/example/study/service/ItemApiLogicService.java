package com.example.study.service;

import com.example.study.model.entity.Item;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.ItemApiRequest;
import com.example.study.model.network.response.ItemApiResponse;
import com.example.study.repository.PartnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemApiLogicService extends BaseService<ItemApiRequest, ItemApiResponse, Item> {


    @Autowired
    private PartnerRepository partnerRepository;

    @Override
    public Header<List<ItemApiResponse>> search(Pageable pageable) {
        Page<Item> items = baseRepository.findAll(pageable);

        List<ItemApiResponse> itemApiResponseList = items.stream()
                .map(user -> responseList(user))
                .collect(Collectors.toList());
        return Header.OK(itemApiResponseList);
    }


    @Override
    public Header<ItemApiResponse> create(Header<ItemApiRequest> request) {

        ItemApiRequest itemApiRequest = request.getData();

        Item item = Item.builder()
                .status(itemApiRequest.getStatus())
                .title(itemApiRequest.getTitle())
                .brandName(itemApiRequest.getBrandName())
                .content(itemApiRequest.getContent())
                .name(itemApiRequest.getName())
                .price(itemApiRequest.getPrice())
                .registeredAt(LocalDateTime.now())
                .partner(partnerRepository.getOne(itemApiRequest.getPartnerId()))
                .build();

        Item newItem = baseRepository.save(item);

        return response(newItem);
    }

    @Override
    public Header<ItemApiResponse> read(Long id) {

        Optional<Item> item = baseRepository.findById(id);

        if (item.isPresent()) {
            return response(item.get());
        }
        return Header.ERROR("존재하지않는 상품입니다.");
    }

    @Override
    public Header<ItemApiResponse> update(Header<ItemApiRequest> request) {

        ItemApiRequest itemApiRequest = request.getData();
        Optional<Item> item = baseRepository.findById(itemApiRequest.getId());

        if (item.isPresent()) {
            item.get()
                    .setName(itemApiRequest.getName())
                    .setTitle(itemApiRequest.getTitle())
                    .setBrandName(itemApiRequest.getBrandName())
                    .setContent(itemApiRequest.getContent())
                    .setPrice(itemApiRequest.getPrice())
                    .setStatus(itemApiRequest.getStatus());

            Item updatedItem = baseRepository.save(item.get());
            return response(updatedItem);
        }
        return Header.ERROR("존재하지않는 상품입니다.");
    }

    @Override
    public Header delete(Long id) {

        Optional<Item> item = baseRepository.findById(id);
        if (item.isPresent()) {
            baseRepository.delete(item.get());
            return Header.OK();
        }
        return Header.ERROR("존재하지않는 상품입니다.");
    }


    public Header<ItemApiResponse> response(Item item) {
        ItemApiResponse itemApiResponse = ItemApiResponse.builder()
                .content(item.getContent())
                .brandName(item.getBrandName())
                .name(item.getName())
                .price(item.getPrice())
                .status(item.getStatus())
                .id(item.getId())
                .title(item.getTitle())
                .unregisteredAt(item.getUnregisteredAt())
                .registeredAt(item.getRegisteredAt())
                .partnerId(item.getPartner().getId())
                .build();

        return Header.OK(itemApiResponse);
    }

    public ItemApiResponse responseList(Item item) {
        ItemApiResponse itemApiResponse = ItemApiResponse.builder()
                .content(item.getContent())
                .brandName(item.getBrandName())
                .name(item.getName())
                .price(item.getPrice())
                .status(item.getStatus())
                .id(item.getId())
                .title(item.getTitle())
                .unregisteredAt(item.getUnregisteredAt())
                .registeredAt(item.getRegisteredAt())
                .partnerId(item.getPartner().getId())
                .build();

        return itemApiResponse;
    }
}
