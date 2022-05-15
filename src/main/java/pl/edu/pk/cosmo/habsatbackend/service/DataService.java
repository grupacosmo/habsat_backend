package pl.edu.pk.cosmo.habsatbackend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import pl.edu.pk.cosmo.habsatbackend.entity.Data;
import pl.edu.pk.cosmo.habsatbackend.exception.NoDataException;
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

    public void saveAll(List<Data> list) {
        dataRepository.saveAll(list);
    }

    public void changeData(Data newData, Long id) throws NoDataException {
        if(!dataRepository.existsById(id))
            throw new NoDataException("There is no data with given id: " + id);

        Data data = dataRepository.findById(id).get();
        data.setTemperature(newData.getTemperature() != null ? newData.getTemperature() : data.getTemperature());
        data.setAltitude(newData.getAltitude() != null ? newData.getAltitude() : data.getAltitude());
        data.setLongitude(newData.getLongitude() != null ? newData.getLongitude() : data.getLongitude());
        data.setLatitude(newData.getLatitude() != null ? newData.getLatitude() : data.getLatitude());
        data.setRssi(newData.getRssi() != null ? newData.getRssi() : data.getRssi());
        data.setSpeed(newData.getSpeed() != null ? newData.getSpeed() : data.getSpeed());
        data.setTime(newData.getTime() != null ? newData.getTime() : data.getTime());

        dataRepository.save(data);
    }

    public Data findById(Long id) throws NoDataException {
        if(!dataRepository.existsById(id))
            throw new NoDataException("There is no data with given id: " + id);

        return dataRepository.findById(id).get();
    }

    public void deleteById(Long id) throws NoDataException {
        if(!dataRepository.existsById(id))
            throw new NoDataException("There is no data with given id: " + id);

        dataRepository.deleteById(id);
    }
}
