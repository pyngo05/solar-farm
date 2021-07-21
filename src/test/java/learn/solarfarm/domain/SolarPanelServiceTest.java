package learn.solarfarm.domain;

import learn.solarfarm.data.SolarPanelRepositoryDouble;
import learn.solarfarm.data.SolarPanelRepository;
import learn.solarfarm.data.XDataAccessException;
import learn.solarfarm.models.Material;
import learn.solarfarm.models.SolarPanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class SolarPanelServiceTest {

    SolarPanelService service;

    @BeforeEach
    void setupTest() throws IOException {
        SolarPanelRepository repository = new SolarPanelRepositoryDouble();
        service = new SolarPanelService(repository);
    }

    @Test
    void findBySection() throws XDataAccessException {
        // test that we find panels of a section
        SolarPanelResult result = service.findBySection("Section2");
        assertTrue(result.isSuccess());
        assertEquals(1, result.getPanels().size());

        // test that error occurs if empty section name given
        result = service.findBySection("");
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotAddDuplicatePanel() throws XDataAccessException {
        SolarPanelResult result = service.add(new SolarPanel(5, "Section1", 2, 3, 2018,
                Material.aSi, true));
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldAddPanel() throws XDataAccessException {
        SolarPanelResult result = service.add(new SolarPanel(8, "Section1", 20, 30, 2018,
                Material.aSi, true));
        assertTrue(result.isSuccess());
    }

    @Test
    void shouldNotAddInvalidPanel() throws XDataAccessException {
        SolarPanelResult result = service.add(new SolarPanel(20, "", 251, 250, 2022,
                Material.CdTe, false));
        assertFalse(result.isSuccess());

    }

    @Test
    void shouldUpdateExisting() throws XDataAccessException {
        SolarPanel updatedPanel = new SolarPanel(5, "Farmland", 250, 250, 2020,
                Material.CdTe, false);
        SolarPanelResult result = service.update(5, updatedPanel);
        result.setPanel(updatedPanel);
        assertTrue(result.isSuccess());
    }

    @Test
    void shouldNotUpdateMissing() throws XDataAccessException {
        SolarPanel updatedPanel = new SolarPanel(100, "Farmland", 250, 250, 2020,
                Material.CdTe, false);
        SolarPanelResult result = service.update(100, updatedPanel);
        result.setPanel(updatedPanel);
        assertFalse(result.isSuccess());
    }


    @Test
    void shouldDeleteById() throws XDataAccessException {
        SolarPanelResult result = service.deleteById(5);
        assertTrue(result.isSuccess());
    }

    @Test
    void shouldNotDeleteByMissingId() throws XDataAccessException {
        SolarPanelResult result = service.deleteById(10000000);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldFindPanelBySectionRowColumn() throws XDataAccessException {
        SolarPanelResult result = service.findPanelBySectionRowColumn("Section1", 3, 3);
        assertTrue(result.isSuccess());
    }

    @Test
    void shouldNotFindMissingPanelBySectionRowColumn() throws XDataAccessException {
        SolarPanelResult result = service.findPanelBySectionRowColumn("Section 12", 3, 3);
        assertFalse(result.isSuccess());
    }
}