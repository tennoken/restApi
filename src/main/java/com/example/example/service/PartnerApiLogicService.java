package com.example.example.service;

import com.example.example.ifs.CrudInterface;
import com.example.example.model.entity.Partner;
import com.example.example.model.network.Header;
import com.example.example.model.network.request.PartnerApiRequest;
import com.example.example.model.network.response.PartnerApiResponse;
import com.example.example.repository.CategoryRepository;
import com.example.example.repository.PartnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PartnerApiLogicService implements CrudInterface<PartnerApiRequest, PartnerApiResponse> {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PartnerRepository partnerRepository;

    @Override
    public Header<PartnerApiResponse> create(Header<PartnerApiRequest> request) {

        PartnerApiRequest body = request.getData();

        Partner partner = Partner.builder()
                .name(body.getName())
                .address(body.getAddress())
                .status(body.getStatus())
                .partnerNumber(body.getPartnerNumber())
                .businessNumber(body.getBusinessNumber())
                .callCenter(body.getCallCenter())
                .ceoName(body.getCeoName())
                .registeredAt(LocalDateTime.now())
                .category(categoryRepository.getOne(body.getCategoryId()))
                .build()
                ;

        Partner newPartner = partnerRepository.save(partner);

        return response(newPartner);
    }

    @Override
    public Header<PartnerApiResponse> read(Long id) {

        return partnerRepository.findById(id)
                .map(this::response)
                .orElseGet(()->Header.ERROR("데이터 없음"));

    }

    @Override
    public Header<PartnerApiResponse> update(Header<PartnerApiRequest> request) {

        PartnerApiRequest body = request.getData();

        return partnerRepository.findById(body.getId())
                .map(partner -> {
                    partner
                            .setName(body.getName())
                            .setAddress(body.getAddress())
                            .setBusinessNumber(body.getBusinessNumber())
                            .setCallCenter(body.getCallCenter())
                            .setCeoName(body.getCeoName())
                            .setPartnerNumber(body.getPartnerNumber())
                            .setStatus(body.getStatus())
                            ;
                    return partner;
                })
                .map(changePartner -> partnerRepository.save(changePartner))
                .map(this::response)
                .orElseGet(()->Header.ERROR("데이터 없음"));
    }

    @Override
    public Header delete(Long id) {

        return partnerRepository.findById(id)
                .map(partner -> {
                    partnerRepository.delete(partner);
                    return Header.OK();
                })
                .orElseGet(()->Header.ERROR("데이터 없음"));

    }


    private Header<PartnerApiResponse> response(Partner partner){

        PartnerApiResponse body = PartnerApiResponse.builder()
                .id(partner.getId())
                .name(partner.getName())
                .address(partner.getAddress())
                .businessNumber(partner.getBusinessNumber())
                .callCenter(partner.getCallCenter())
                .ceoName(partner.getCeoName())
                .partnerNumber(partner.getPartnerNumber())
                .status(partner.getStatus())
                .registeredAt(partner.getRegisteredAt())
                .categoryId(partner.getCategory().getId())
                .build()
                ;

        return Header.OK(body);
    }
}
