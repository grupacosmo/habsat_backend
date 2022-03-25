package pl.edu.pk.cosmo.habsatbackend.controller;

import org.springframework.web.bind.annotation.*;
import pl.edu.pk.cosmo.habsatbackend.entity.Data;
import pl.edu.pk.cosmo.habsatbackend.service.DataService;

import java.util.List;

@RestController
@RequestMapping("/data-frame`")
public class DataController {

    final DataService dataService;

    public DataController(final DataService dataService) {
        this.dataService = dataService;
    }

    @PostMapping
    public void save(@RequestBody final Data data) {
        dataService.save(data);
    }

    @GetMapping("/all")
    public List<Data> findAll() {
        return dataService.findAll();
    }

    @DeleteMapping("/~deleteAll")
    public void delete() {
        dataService.deleteAll();
    }

}
