package com.example.reactivemongo.services;

import com.example.reactivemongo.dto.ProductDto;
import com.example.reactivemongo.repository.ProductRepository;
import com.example.reactivemongo.util.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public Flux<ProductDto> getProducts(){

        return repository.findAll()
                .map(AppUtils::entityToDto);
    }

    public Mono<ProductDto> getProduct(String id){
        return repository.findById(id)
                .map(AppUtils::entityToDto);
    }

    public Flux<ProductDto> getProductinRange(double min , double max){

        return repository.findByPriceBetween(Range.closed(min, max))
                .map(AppUtils::entityToDto); //since it a one to one mapping.
    }

    public Mono<ProductDto> saveProduct(Mono<ProductDto> productDtoMono){
        return productDtoMono.map(AppUtils::dtoToEntity)
                .flatMap(repository::insert) // One to Many u use flatmap
                .map(AppUtils::entityToDto); //for since mapping u use map.  one to one mapping.
    }

    public Mono<ProductDto> updateProduct(String id, Mono<ProductDto> productDtoMono){
        return repository.findById(id)
                .flatMap(p-> productDtoMono.map(AppUtils::dtoToEntity)) //We use flatMap cos it would have a mono of mono argument   one to many mapping
                .doOnNext(e->e.setId(id)) //You set the id so the update operations happen
                .flatMap(repository::save)
                .map(AppUtils::entityToDto);  //We now pass it to Dto.

    }

    public Mono<Void> deleteProduct(String id){
        return repository.deleteById(id);
    }
}
