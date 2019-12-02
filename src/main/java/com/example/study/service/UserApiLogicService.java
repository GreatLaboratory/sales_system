package com.example.study.service;

import com.example.study.model.entity.OrderGroup;
import com.example.study.model.entity.User;
import com.example.study.model.enumClass.UserStatus;
import com.example.study.model.network.Header;
import com.example.study.model.network.PageInfo;
import com.example.study.model.network.request.UserApiRequest;
import com.example.study.model.network.response.ItemApiResponse;
import com.example.study.model.network.response.OrderGroupApiResponse;
import com.example.study.model.network.response.UserApiResponse;
import com.example.study.model.network.response.UserOrderInfoApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserApiLogicService extends BaseService<UserApiRequest, UserApiResponse, User> {

    @Autowired
    private OrderGroupApiLogicService orderGroupApiLogicService;

    @Autowired
    private ItemApiLogicService itemApiLogicService;


    public Header<UserOrderInfoApiResponse> orderInfo(Long id) {

        // user
        User user = baseRepository.getOne(id);
        UserApiResponse userApiResponse = responseList(user);

        // orderGroup
        List<OrderGroup> orderGroupList = user.getOrderGroupList();
        List<OrderGroupApiResponse> orderGroupApiResponseList = orderGroupList.stream()
                .map(orderGroup -> {
                    OrderGroupApiResponse orderGroupApiResponse = orderGroupApiLogicService.response(orderGroup).getData();

                    // item
                    List<ItemApiResponse> itemApiResponseList = orderGroup.getOrderDetailList().stream()
                            .map(detail -> detail.getItem())
                            .map(item -> itemApiLogicService.response(item).getData())
                            .collect(Collectors.toList());

                    orderGroupApiResponse.setItemApiResponseList(itemApiResponseList);

                    return orderGroupApiResponse;

                })
                .collect(Collectors.toList());

        userApiResponse.setOrderGroupApiResponseList(orderGroupApiResponseList);

        UserOrderInfoApiResponse userOrderInfoApiResponse =  UserOrderInfoApiResponse.builder()
                .userApiResponse(userApiResponse)
                .build();

        return Header.OK(userOrderInfoApiResponse);
    }

    @Override
    public Header<List<UserApiResponse>> search(Pageable pageable) {
        Page<User> users = baseRepository.findAll(pageable);
        List<UserApiResponse> userApiResponseList = users.stream()
                .map(user -> responseList(user))
                .collect(Collectors.toList());

        PageInfo pageInfo = PageInfo.builder()
                .currentElements(users.getNumberOfElements())
                .currentPage(users.getNumber())
                .totalElements(users.getTotalElements())
                .totalPages(users.getTotalPages())
                .build();

        return Header.OK(userApiResponseList, pageInfo);
    }

    @Override
    public Header<UserApiResponse> create(Header<UserApiRequest> request) {

        // 1. request로 넘어온 전체 json 중에서 data에 해당하는 부분만 객체화해서 받아온다.
        UserApiRequest userApiRequest = request.getData();

        // 2. 받아온 객체의 각각의 필드값들을 user라는 모델에 넣는다.
        User user = User.builder()
                .account(userApiRequest.getAccount())
                .password(userApiRequest.getPassword())
                .email(userApiRequest.getEmail())
                .status(UserStatus.REGISTERED)
                .phoneNumber(userApiRequest.getPhoneNumber())
                .registeredAt(LocalDateTime.now())
                .build();

        // 3. 저장된 user를 jparepository를 통해 db에 저장한다.  -> 여기서 자동으로 createdAt이라든가 id라든가 등등이 db에 자동저장됨
        User newUser = baseRepository.save(user);

        // 4. newUser를 userApiResponse형식으로 바꿔서 응답한다. -> 형식변환은 새로운 메소드로 맨 아래에 새로 정의한다.
        return response(newUser);
    }

    @Override
    public Header<UserApiResponse> read(Long id) {
        Optional<User> user = baseRepository.findById(id);
        if (user.isPresent()) {
            return response(user.get());
        }
        return Header.ERROR("존재하지않는 사용자입니다.");
    }

    @Override
    public Header<UserApiResponse> update(Header<UserApiRequest> request) {

        // 1. request로 넘어온 전체 json 중에서 data에 해당하는 부분만 객체화해서 받아온다.
        UserApiRequest userApiRequest = request.getData();

        // 2. 받아온 UserApiResponse객체의 id값으로 db에서 user객체를 조회한다.
        Optional<User> user = baseRepository.findById(userApiRequest.getId());

        // 3. user객체가 존재할 경우 request로 받아온 값들을 새롭게 db에 저장한 후
        // 4. updatedUser를 userApiResponse형식으로 바꿔서 응답한다.
        if (user.isPresent()) {
            user.get()
                    .setAccount(userApiRequest.getAccount())
                    .setPassword(userApiRequest.getPassword())
                    .setEmail(userApiRequest.getEmail())
                    .setStatus(UserStatus.REGISTERED)
                    .setPhoneNumber(userApiRequest.getPhoneNumber());
            User updatedUser = baseRepository.save(user.get());
            return response(updatedUser);
        }
        return Header.ERROR("존재하지않는 사용자입니다.");
    }

    @Override
    public Header delete(Long id) {

        Optional<User> user = baseRepository.findById(id);

        if (user.isPresent()) {
            baseRepository.delete(user.get());
            return Header.OK();
        }
        return Header.ERROR("존재하지않는 사용자입니다.");
    }


    // 엔티티 User를 Header포함한 UserApiResponse으로 바꿔주는 메소드
    private Header<UserApiResponse> response(User user) {

        UserApiResponse userApiResponse = UserApiResponse.builder()
                .id(user.getId())
                .account(user.getAccount())
                .password(user.getPassword())         // 암호화 필요
                .email(user.getEmail())
                .status(user.getStatus())
                .phoneNumber(user.getPhoneNumber())
                .registeredAt(user.getRegisteredAt())
                .unregisteredAt(user.getUnregisteredAt())
                .build();

        // header + data 리턴
        return Header.OK(userApiResponse);
    }

    // 엔티티 User를 UserApiResponse으로 바꿔주는 메소드
    private UserApiResponse responseList(User user) {

        UserApiResponse userApiResponse = UserApiResponse.builder()
                .id(user.getId())
                .account(user.getAccount())
                .password(user.getPassword())         // 암호화 필요
                .email(user.getEmail())
                .status(user.getStatus())
                .phoneNumber(user.getPhoneNumber())
                .registeredAt(user.getRegisteredAt())
                .unregisteredAt(user.getUnregisteredAt())
                .build();

        return userApiResponse;
    }

}
