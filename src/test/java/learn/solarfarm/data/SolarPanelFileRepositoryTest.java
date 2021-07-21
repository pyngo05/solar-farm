package learn.solarfarm.data;

import learn.solarfarm.models.Material;
import learn.solarfarm.models.SolarPanel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SolarPanelFileRepositoryTest {

    static final String SEED_FILE_PATH = "./data/dataaccess-testdata.csv";
    static final String TEST_FILE_PATH = "./data/dataaccess-testdata-copy.csv";

    SolarPanelFileRepository repository;

    @BeforeEach
    void setupTest() throws IOException {
        Path seedPath = Paths.get(SEED_FILE_PATH);
        Path testPath = Paths.get(TEST_FILE_PATH);

        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);

        repository = new SolarPanelFileRepository(TEST_FILE_PATH);
    }
    // Delete the test file that was generated from the seed file.
    @AfterEach
    void cleanupTest() throws IOException {
        Path testPath = Paths.get(TEST_FILE_PATH);

        Files.delete(testPath);
    }

    @Test
    void findAll() throws XDataAccessException {
        List<SolarPanel> actual = repository.findAll();

        assertNotNull(actual);
        assertEquals(3, actual.size());
    }

    @Test
    void findBySection() throws XDataAccessException {
        List<SolarPanel> actual = repository.findBySection("Other Section");
        assertNotNull(actual);
        assertEquals(1, actual.size());

        actual = repository.findBySection("Non-existent Section");
        assertEquals(0, actual.size());
    }

    @Test
    void add() throws XDataAccessException {
        SolarPanel panel = new SolarPanel(20, "Farm", 5,
                7, 2017, Material.CIGS, true);

        SolarPanel actual = repository.add(panel);
        assertNotNull(actual);
        assertEquals("Farm", actual.getSection());
    }

    @Test
    void update() throws XDataAccessException {
        SolarPanel panel = new SolarPanel(2, "Farm", 100, 105, 2020, Material.CdTe, false);

        boolean success = repository.update(panel);
        assertTrue(success);

        SolarPanel actual = repository.findById(2);
        assertNotNull(actual);
        assertEquals(2020, actual.getYear());
        assertEquals(100, actual.getRow());

        SolarPanel doesNotExist = new SolarPanel(99, "Farm", 100, 105, 2020, Material.CdTe, false);
        success = repository.update(doesNotExist);
        assertFalse(success);
    }

    @Test
    void findById() throws XDataAccessException {
        SolarPanel panel = repository.findById(3);
        assertNotNull(panel);
        assertEquals("Other Section", panel.getSection());
        assertTrue(panel.isTracking());

        panel = repository.findById(1024);
        assertNull(panel); // id 1024 does not exist, expect null
    }

    @Test
    void deleteById() throws XDataAccessException {
        boolean success = repository.deleteById(3);
        assertTrue(success);

        SolarPanel panel = repository.findById(3);
        assertNull(panel);

        success = repository.deleteById(5);
        assertFalse(success);
    }

}

