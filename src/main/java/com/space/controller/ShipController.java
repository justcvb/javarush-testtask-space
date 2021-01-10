package com.space.controller;

import com.space.model.Ship;
import com.space.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/rest/ships")
public class ShipController {

    private ShipService service;

    @Autowired
    public ShipController(ShipService service) {
        this.service = service;
    }

    @RequestMapping(path = "/count", method = RequestMethod.GET)
    public ResponseEntity<Long> count(@RequestParam Map<String, String> params) {
        return new ResponseEntity<>(service.count(params), HttpStatus.OK);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public ResponseEntity<List<Ship>> findAll(@RequestParam Map<String, String> params) {
        params.putIfAbsent("order", ShipOrder.ID.getFieldName());
        params.putIfAbsent("pageNumber", "0");
        params.putIfAbsent("pageSize", "3");
        return new ResponseEntity<>(service.findAll(params), HttpStatus.OK);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Ship> findById(@PathVariable Long id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<Ship> save(@RequestBody Ship ship) {
        return new ResponseEntity<>(service.save(ship), HttpStatus.OK);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.POST)
    public ResponseEntity<Ship> update(@PathVariable Long id, @RequestBody Ship ship) {
        return new ResponseEntity<>(service.update(id, ship), HttpStatus.OK);
    }

}
