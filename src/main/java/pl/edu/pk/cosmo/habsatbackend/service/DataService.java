package pl.edu.pk.cosmo.habsatbackend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import pl.edu.pk.cosmo.habsatbackend.entity.Data;
import pl.edu.pk.cosmo.habsatbackend.repository.DataRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class DataService {
    final DataRepository dataRepository;
    final SimpMessagingTemplate simpMessagingTemplate;
    final JdbcTemplate jdbcTemplate;
    final Logger logger = LoggerFactory.getLogger(DataService.class);

    public DataService(final DataRepository dataRepository, final SimpMessagingTemplate simpMessagingTemplate, final JdbcTemplate jdbcTemplate) {
        this.dataRepository = dataRepository;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(final Data data) {
        dataRepository.save(data);
    }

    @Transactional
    public void sendFrame(final Data data) {
        jdbcTemplate.update("INSERT INTO DATA(SPEED, ALTITUDE, LONGITUDE, LATITUDE,TEMPERATURE, TIME, RSSI) VALUES (?, ?, ?, ?, ?, ?, ?)",
                data.getSpeed(),
                data.getAltitude(),
                data.getLongitude(),
                data.getLatitude(),
                data.getTemperature(),
                data.getTime(),
                data.getRssi());
        simpMessagingTemplate.convertAndSend("/data/ws", data);
    }

    public List<Data> findAll() {
        return dataRepository.findAll();
    }

    public void deleteAll() {
        jdbcTemplate.execute("ALTER TABLE DATA ALTER COLUMN ID RESTART WITH 1");
        dataRepository.deleteAll();
    }

    // Only for previous purposes
//    private Data generateRandomData() {
//        final Random random = new Random();
//        final Data data = new Data();
//        String[] ns = {"N", "S"};
//        String[] ew = {"E", "W"};
//        data.setSpeed(random.nextDouble());
//        data.setAltitude(random.nextDouble());
//        data.setTemperature(random.nextDouble());
//        data.setLongitude(random.nextDouble()*100 + ew[random.nextInt(ew.length)]);
//        data.setLatitude(random.nextDouble()*100 + ns[random.nextInt(ns.length)]);
//        return data;
//    }
}
