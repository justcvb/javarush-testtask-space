package com.space.service;

import com.space.exception.IdException;
import com.space.exception.NotFoundException;
import com.space.model.Ship;
import com.space.repository.ShipRepository;
import com.space.specification.ShipSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class ShipService {

    private ShipRepository repository;

    @Autowired
    public ShipService(ShipRepository repository) {
        this.repository = repository;
    }

    public long count(Map<String, String> params) {
        return repository.count(new ShipSpecification(params));
    }

    public void deleteById(Long id) {
        if (id <= 0) {
            throw new IdException();
        }
        repository.delete(repository.findById(id).orElseThrow(NotFoundException::new));
    }

    public List<Ship> findAll(Map<String, String> params) {
        ShipSpecification specification = new ShipSpecification(params);
        PageRequest pageRequest = PageRequest.of(
                Integer.parseInt(params.get("pageNumber")),
                Integer.parseInt(params.get("pageSize")),
                Sort.by(params.get("order").toLowerCase()));
        return repository.findAll(specification, pageRequest).getContent();
    }

    public Ship findById(Long id) {
        if (id <= 0) {
            throw new IdException();
        }
        return repository.findById(id).orElseThrow(NotFoundException::new);
    }

    public Ship save(Ship ship) {
        ship.validate();
        ship.calcRating();
        return repository.save(ship);
    }

    public Ship update(Long id, Ship data) {
        if (id <= 0) {
            throw new IdException();
        }
        Ship ship = repository.findById(id).orElseThrow(NotFoundException::new);
        ship.merge(data);
        return save(ship);
    }

}
