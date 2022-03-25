package pl.edu.pk.cosmo.habsatbackend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.edu.pk.cosmo.habsatbackend.entity.Data;
import pl.edu.pk.cosmo.habsatbackend.repository.DataRepository;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

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
    @Scheduled(fixedDelay = 1000 * 10/* Seconds */)
    public void sendTestFrame() {
        final Data data = generateRandomData();
        jdbcTemplate.update("INSERT INTO DATA(TIME, SYSTEM_TIME, FIX, QUALITY_LOCATION, SPEED, ALTITUDE, SATELLITES, TEMPERATURE, PRESSURE, ACCELERATIONX, ACCELERATIONY, ACCELERATIONZ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",

                Timestamp.valueOf(LocalDateTime.now()),
                Timestamp.valueOf(LocalDateTime.now()),
                data.getFix(),
                data.getQualityLocation(),
                data.getSpeed(),
                data.getAltitude(),
                data.getSatellites(),
                data.getTemperature(),
                data.getPressure(),
                data.getAccelerationX(),
                data.getAccelerationY(),
                data.getAccelerationZ());
        simpMessagingTemplate.convertAndSend("/data/ws", data);
    }

    public List<Data> findAll() {
        return dataRepository.findAll();
    }

    private Data generateRandomData() {
        final Random random = new Random();
        final Data data = new Data();
        data.setTime(LocalDateTime.now());
        data.setSystemTime(LocalDateTime.now());
        data.setFix(random.nextDouble());
        data.setQualityLocation(random.nextDouble());
        data.setSpeed(random.nextDouble());
        data.setPressure(random.nextDouble());
        data.setAltitude(random.nextDouble());
        data.setSatellites(random.nextInt());
        data.setTemperature(random.nextDouble());
        data.setAccelerationX(random.nextDouble());
        data.setAccelerationY(random.nextDouble());
        data.setAccelerationZ(random.nextDouble());
        return data;
    }
}
