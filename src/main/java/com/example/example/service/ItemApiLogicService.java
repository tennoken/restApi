package com.example.example.service;

import com.example.example.ifs.CrudInterface;
import com.example.example.model.entity.Item;
import com.example.example.model.network.Header;
import com.example.example.model.network.request.ItemApiRequest;
import com.example.example.model.network.response.ItemApiResponse;
import com.example.example.repository.ItemRepository;
import com.example.example.repository.PartnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ItemApiLogicService extends BaseService<ItemApiRequest, ItemApiResponse, Item> {

    @Autowired
    private PartnerRepository partnerRepository;


    @Override
    public Header<ItemApiResponse> create(Header<ItemApiRequest> request) {

        ItemApiRequest body = request.getData();

        Item item = Item.builder()
                .status(body.getStatus())
                .name(body.getName())
                .title(body.getTitle())
                .content(body.getContent())
                .brandName(body.getBrandName())
                .price(body.getPrice())
                .registeredAt(LocalDateTime.now())
                .partner(partnerRepository.getOne(body.getPartnerId()))
                .build();

        Item newItem = baseRepository.save(item);

        return response(newItem);
    }

    @Override
    public Header<ItemApiResponse> read(Long id) {

        return baseRepository.findById(id)
                 .map(item -> response(item))
                 .orElseGet(()->Header.ERROR("데이터 없음"));
    }

    @Override
    public Header<ItemApiResponse> update(Header<ItemApiRequest> request) {

        ItemApiRequest body = request.getData();

        return baseRepository.findById(body.getId())
                .map(itemEntity -> {
                    itemEntity
                            .setName(body.getName())
                            .setBrandName(body.getBrandName())
                            .setContent(body.getContent())
                            .setStatus(body.getStatus())
                            .setTitle(body.getTitle())
                            .setPrice(body.getPrice())
                            .setRegisteredAt(body.getRegisteredAt())
                            .setUnregisteredAt(body.getUnregisteredAt())
                            ;
                            return itemEntity;

                })
                .map(newEntity -> baseRepository.save(newEntity))
                .map(item -> response(item))
                .orElseGet(() -> Header.ERROR("데이터 없음"));


    }

    @Override
    public Header delete(Long id) {

        return baseRepository.findById(id)
                .map(item -> {
                    baseRepository.delete(item);
                    return Header.OK();
                })
                .orElseGet(()->Header.ERROR("데이터 없음"));
    }


    public Header<ItemApiResponse> response(Item item){

        ItemApiResponse body = ItemApiResponse.builder()
                .id(item.getId())
                .name(item.getName())
                .title(item.getTitle())
                .content(item.getContent())
                .price(item.getPrice())
                .brandName(item.getBrandName())
                .status(item.getStatus())
                .registeredAt(item.getRegisteredAt())
                .unregisteredAt(item.getUnregisteredAt())
                .partnerId(item.getPartner().getId())
                .build();

        return Header.OK(body);

    }

    @Override
    public Header<List<ItemApiResponse>> search(Pageable pageable) {
        return null;
    }
}
