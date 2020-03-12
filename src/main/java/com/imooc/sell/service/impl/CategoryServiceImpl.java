package com.imooc.sell.service.impl;

import com.imooc.sell.entity.ProductCategory;
import com.imooc.sell.repository.ProductCategoryRepository;
import com.imooc.sell.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * CategoryServiceImpl class
 *
 * @author hujun
 * @date 2020/02/05
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Override
    public ProductCategory findOne(Integer id) {
        Optional<ProductCategory> category = productCategoryRepository.findById(id);
        return category.orElse(null);
    }

    @Override
    public List<ProductCategory> findAll() {
        return productCategoryRepository.findAll();
    }

    @Override
    public Page<ProductCategory> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<ProductCategory> findByTypeIn(List<Integer> typeList) {
        return productCategoryRepository.findByTypeIn(typeList);
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return productCategoryRepository.save(productCategory);
    }
}
