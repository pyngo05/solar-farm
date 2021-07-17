package learn.solarfarm.data;

import learn.solarfarm.models.SolarPanel;

import java.util.List;

public interface SolarPanelRepository {

    List<SolarPanel> findAll() throws DataAccessException;

    SolarPanel findById(int panelId) throws DataAccessException;

    List<SolarPanel> findIsTracking(boolean isTracking) throws DataAccessException;

    SolarPanel add(SolarPanel panel) throws DataAccessException;

    boolean update(SolarPanel panel) throws DataAccessException;

    boolean deleteById(int panelId) throws DataAccessException;
}