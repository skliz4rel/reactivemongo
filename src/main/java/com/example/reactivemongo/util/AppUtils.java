package com.example.reactivemongo.util;

import com.example.reactivemongo.dto.ProductDto;
import com.example.reactivemongo.entities.Product;
import org.springframework.beans.BeanUtils;

public class AppUtils {

    public static ProductDto entityToDto(Product product){

        ProductDto productDto = new ProductDto();
        BeanUtils.copyProperties(product, productDto);

        return productDto;
    }

    public static Product dtoToEntity(ProductDto dto){

        Product product = new Product();
        BeanUtils.copyProperties(dto, product);

        return product;
    }
}
