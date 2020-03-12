package com.imooc.sell.service.impl;

import com.imooc.sell.entity.ProductCategory;
import com.imooc.sell.enums.ErrorCode;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.repository.ProductCategoryRepository;
import com.imooc.sell.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * CategoryServiceImpl class
 *
 * @author hujun
 * @date 2020/02/05
 */
@Service
@Slf4j
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
        return productCategoryRepository.findAll(pageable);
    }

    @Override
    public List<ProductCategory> findByTypeIn(List<Integer> typeList) {
        return productCategoryRepository.findByTypeIn(typeList);
    }

    @Override
    @Transactional
    public ProductCategory save(ProductCategory productCategory) {
        ProductCategory category = new ProductCategory();
        if (productCategory.getId() != null){
            category = findOne(productCategory.getId());
            if (category == null){
                log.error("【修改类目】类目不存在");
                throw new SellException(ErrorCode.CATEGORY_NOT_EXISTS);
            }
            productCategory.setCreateTime(category.getCreateTime());
            productCategory.setUpdateTime(category.getUpdateTime());
        }
        BeanUtils.copyProperties(productCategory, category);
        return productCategoryRepository.save(category);
    }
}
