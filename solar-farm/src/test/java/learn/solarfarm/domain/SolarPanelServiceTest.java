package learn.solarfarm.domain;

import learn.solarfarm.data.SolarPanelDoubleRepository;
import learn.solarfarm.data.SolarPanelRepository;
import learn.solarfarm.data.XDataAccessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class SolarPanelServiceTest {

    SolarPanelService service;

    @BeforeEach
    void setupTest() throws IOException {
        SolarPanelRepository repository = new SolarPanelDoubleRepository();
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
}