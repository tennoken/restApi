package com.example.example.controller.api;


import com.example.example.controller.CrudController;
import com.example.example.ifs.CrudInterface;
import com.example.example.model.entity.User;
import com.example.example.model.network.Header;
import com.example.example.model.network.request.UserApiRequest;
import com.example.example.model.network.response.UserApiResponse;
import com.example.example.service.UserApiLogicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserApiController extends CrudController<UserApiRequest, UserApiResponse, User> {


}
