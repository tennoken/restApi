package com.example.example.controller.api;

import com.example.example.controller.CrudController;
import com.example.example.ifs.CrudInterface;
import com.example.example.model.entity.OrderGroup;
import com.example.example.model.network.Header;
import com.example.example.model.network.request.OrderGroupApiRequest;
import com.example.example.model.network.response.OrderGroupApiResponse;
import com.example.example.service.OrderGroupApiLogicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("/api/orderGroup")
public class OrderGroupApiController extends CrudController<OrderGroupApiRequest, OrderGroupApiResponse, OrderGroup> {

}
