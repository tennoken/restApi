package com.example.example.controller.api;


import com.example.example.controller.CrudController;
import com.example.example.ifs.CrudInterface;
import com.example.example.model.entity.User;
import com.example.example.model.network.Header;
import com.example.example.model.network.request.UserApiRequest;
import com.example.example.model.network.response.UserApiResponse;
import com.example.example.model.network.response.UserOrderInfoApiResponse;
import com.example.example.service.UserApiLogicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserApiController extends CrudController<UserApiRequest, UserApiResponse, User> {

    @GetMapping("")
    public Header<List<UserApiResponse>> search(@PageableDefault(sort = "id", direction = Sort.Direction.ASC, size = 15) Pageable pageable){
        log.info("{}", pageable);
        return baseService.search(pageable);
    }

    @GetMapping("/{id}/orderInfo")
    public Header<UserOrderInfoApiResponse> orderInfo(@PathVariable Long id){
        return baseService.orderInfo(id);
    }

}
