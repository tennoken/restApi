package com.example.example.service;

import com.example.example.ifs.CrudInterface;
import com.example.example.model.entity.Category;
import com.example.example.model.network.Header;
import com.example.example.model.network.request.CategoryApiRequest;
import com.example.example.model.network.response.CategoryApiResponse;
import com.example.example.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryApiLogicService implements CrudInterface<CategoryApiRequest, CategoryApiResponse> {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Header<CategoryApiResponse> create(Header<CategoryApiRequest> request) {

        CategoryApiRequest body = request.getData();

        Category category = Category.builder()
                .title(body.getTitle())
                .type(body.getType())
                .build()
                ;

        Category newCategory = categoryRepository.save(category);

        return response(newCategory);
    }

    @Override
    public Header<CategoryApiResponse> read(Long id) {

        return categoryRepository.findById(id)
                .map(this::response)
                .orElseGet(()->Header.ERROR("데이터 없음"));

    }

    @Override
    public Header<CategoryApiResponse> update(Header<CategoryApiRequest> request) {


        CategoryApiRequest body = request.getData();

        return categoryRepository.findById(body.getId())
                .map(category -> {
                    category
                            .setTitle(body.getTitle())
                            .setType(body.getType())
                            ;
                    return  category;
                })
                .map(changeCategory -> categoryRepository.save(changeCategory))
                .map(this::response)
                .orElseGet(()->Header.ERROR("데이터 없음"));

    }

    @Override
    public Header delete(Long id) {

        return categoryRepository.findById(id)
                .map(category -> {
                    categoryRepository.delete(category);
                    return Header.OK();
                })
                .orElseGet(()->Header.ERROR("데이터 없음"));
    }

    private Header<CategoryApiResponse> response(Category category){

        CategoryApiResponse body = CategoryApiResponse.builder()
                .id(category.getId())
                .title(category.getTitle())
                .type(category.getType())
                .build()
                ;
        return Header.OK(body);
    }
}
