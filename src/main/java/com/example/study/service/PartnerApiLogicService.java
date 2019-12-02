package com.example.study.service;

import com.example.study.model.entity.Partner;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.PartnerApiRequest;
import com.example.study.model.network.response.PartnerApiResponse;
import com.example.study.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PartnerApiLogicService extends BaseService<PartnerApiRequest, PartnerApiResponse, Partner> {


    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Header<List<PartnerApiResponse>> search(Pageable pageable) {
        Page<Partner> partners = baseRepository.findAll(pageable);

        List<PartnerApiResponse> userApiResponseList = partners.stream()
                .map(partnerList -> responseList(partnerList))
                .collect(Collectors.toList());
        return Header.OK(userApiResponseList);
    }

    @Override
    public Header<PartnerApiResponse> create(Header<PartnerApiRequest> request) {

        PartnerApiRequest partnerApiRequest = request.getData();

        Partner partner = Partner.builder()
                .name(partnerApiRequest.getName())
                .status(partnerApiRequest.getStatus())
                .address(partnerApiRequest.getAddress())
                .category(categoryRepository.getOne(partnerApiRequest.getCategoryId()))
                .callCenter(partnerApiRequest.getCallCenter())
                .partnerNumber(partnerApiRequest.getPartnerNumber())
                .businessNumber(partnerApiRequest.getBusinessNumber())
                .ceoName(partnerApiRequest.getCeoName())
                .registeredAt(LocalDateTime.now())
                .unregisteredAt(partnerApiRequest.getUnregisteredAt())
                .build();

        Partner newPartner = baseRepository.save(partner);

        return response(newPartner);
    }

    @Override
    public Header<PartnerApiResponse> read(Long id) {

        Optional<Partner> partner = baseRepository.findById(id);
        if (partner.isPresent()) {
            return response(partner.get());
        }

        return Header.ERROR("존재하지 않는 파트너입니다.");
    }

    @Override
    public Header<PartnerApiResponse> update(Header<PartnerApiRequest> request) {

        PartnerApiRequest partnerApiRequest = request.getData();

        Optional<Partner> partner = baseRepository.findById(partnerApiRequest.getId());

        if (partner.isPresent()) {
            partner.get()
                    .setName(partnerApiRequest.getName())
                    .setStatus(partnerApiRequest.getStatus())
                    .setAddress(partnerApiRequest.getAddress())
                    .setCallCenter(partnerApiRequest.getCallCenter())
                    .setBusinessNumber(partnerApiRequest.getBusinessNumber())
                    .setPartnerNumber(partnerApiRequest.getPartnerNumber())
                    .setCeoName(partnerApiRequest.getCeoName());

            Partner updatedPartner = baseRepository.save(partner.get());
            return response(updatedPartner);
        }
        return Header.ERROR("존재하지 않는 파트너입니다.");
    }

    @Override
    public Header delete(Long id) {

        Optional<Partner> partner = baseRepository.findById(id);
        if (partner.isPresent()) {
            baseRepository.delete(partner.get());
            return Header.OK();
        }
        return Header.ERROR("존재하지 않는 파트너입니다.");
    }

    private Header<PartnerApiResponse> response(Partner partner) {
        PartnerApiResponse partnerApiResponse = PartnerApiResponse.builder()
                .id(partner.getId())
                .status(partner.getStatus())
                .name(partner.getName())
                .address(partner.getAddress())
                .callCenter(partner.getCallCenter())
                .partnerNumber(partner.getPartnerNumber())
                .businessNumber(partner.getBusinessNumber())
                .ceoName(partner.getCeoName())
                .registeredAt(partner.getRegisteredAt())
                .unregisteredAt(partner.getUnregisteredAt())
                .categoryId(partner.getCategory().getId())
                .build();
        return Header.OK(partnerApiResponse);
    }

    private PartnerApiResponse responseList(Partner partner) {
        PartnerApiResponse partnerApiResponse = PartnerApiResponse.builder()
                .id(partner.getId())
                .status(partner.getStatus())
                .name(partner.getName())
                .address(partner.getAddress())
                .callCenter(partner.getCallCenter())
                .partnerNumber(partner.getPartnerNumber())
                .businessNumber(partner.getBusinessNumber())
                .ceoName(partner.getCeoName())
                .registeredAt(partner.getRegisteredAt())
                .unregisteredAt(partner.getUnregisteredAt())
                .categoryId(partner.getCategory().getId())
                .build();
        return partnerApiResponse;
    }
}
