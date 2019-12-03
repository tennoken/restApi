package com.example.example.service;

import com.example.example.ifs.CrudInterface;
import com.example.example.model.network.Header;
import com.example.example.model.network.response.UserOrderInfoApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public abstract class BaseService<Req, Res, Entity> implements CrudInterface<Req, Res> {

    @Autowired(required = false)
    protected JpaRepository<Entity, Long> baseRepository;

    public abstract Header<List<Res>> search(Pageable pageable);

    public Header<UserOrderInfoApiResponse> orderInfo(Long id){
        return null;
    }
}
