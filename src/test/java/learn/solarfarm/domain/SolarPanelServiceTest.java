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
    void shouldAddPanel() throws XDataAccessException {
        SolarPanelResult result = service.add(new SolarPanel(5, "Section1", 2, 3, 2018,
                Material.aSi, true));
        assertTrue(result.isSuccess());

    }

    @Test
    void shouldNotAddNullPanel() throws XDataAccessException {
        SolarPanelResult result = service.add(new SolarPanel(20, "", 251, 250, 2022,
                Material.CdTe, false));
        assertFalse(result.isSuccess());

    }

    @Test
    void shouldUpdate() throws XDataAccessException {
        SolarPanelResult result = service.add(new SolarPanel(1, "Section1", 3, 3, 2018,
                Material.aSi, true));
        assertTrue(result.isSuccess());
    }

    @Test
    void shouldNotUpdate() throws XDataAccessException {
        SolarPanelResult result = service.add(new SolarPanel(1, "Section1", 255, 3, 2018,
                Material.aSi, true));
        assertFalse(result.isSuccess());
    }


}