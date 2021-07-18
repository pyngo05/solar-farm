package learn.solarfarm.data;

import learn.solarfarm.models.SolarPanel;

import java.util.List;

public interface SolarPanelRepository {

    List<SolarPanel> findAll() throws XDataAccessException;

    boolean findBySection(String section) throws XDataAccessException;

//    List<SolarPanel> findIsTracking(boolean isTracking) throws XDataAccessException;
//
//    SolarPanel add(SolarPanel panel) throws XDataAccessException;
//
//    boolean update(SolarPanel panel) throws XDataAccessException;
//
//    boolean deleteById(int panelId) throws XDataAccessException;
}