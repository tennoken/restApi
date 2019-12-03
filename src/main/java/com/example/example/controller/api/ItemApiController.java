package com.example.example.controller.api;

import com.example.example.controller.CrudController;
import com.example.example.ifs.CrudInterface;
import com.example.example.model.entity.Item;
import com.example.example.model.network.Header;
import com.example.example.model.network.request.ItemApiRequest;
import com.example.example.model.network.response.ItemApiResponse;

import com.example.example.service.ItemApiLogicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/item")
public class ItemApiController extends CrudController<ItemApiRequest, ItemApiResponse, Item> {

}
