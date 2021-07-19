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

    SolarPanelFileRepository repository = new SolarPanelFileRepository(TEST_FILE_PATH);

    @BeforeEach
    void setupTest() throws IOException {
        Path seedPath = Paths.get(SEED_FILE_PATH);
        Path testPath = Paths.get(TEST_FILE_PATH);

        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
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
        SolarPanel panel = new SolarPanel("Farm", 5,
                7, 2017, Material.CIGS, true);

        SolarPanel actual = repository.add(panel);
        assertNotNull(actual);
        assertEquals("Farm", actual.getSection());
    }

    @Test
    void update() throws XDataAccessException {
//        SolarPanel panel = repository.findBySection("Farm");
//        panel.setRow(1);                    // was Uncle Sherwin
//        panel.setShareable(false);                   // was true
//        assertTrue(repository.update(panel));
//
//        panel = repository.findById(2);
//        assertNotNull(panel);                        // confirm the memory exists
//        assertEquals("Sherwin", panel.getFrom());    // confirm the memory was updated
//        assertFalse(panel.isShareable());

        SolarPanel doesNotExist = new SolarPanel();
        doesNotExist.setYear(1024);
        boolean actual = repository.update(doesNotExist);
//        assertFalse(repository.update(doesNotExist));
        assertFalse(actual);
    }

//    @Test
//    void findById() throws XDataAccessException {
//        Memory memory = repository.findById(2);
//        assertNotNull(memory);
//        assertEquals("Uncle Sherwin", memory.getFrom());
//        assertTrue(memory.isShareable());
//
//        memory = repository.findById(1024);
//        assertNull(memory); // id 1024 does not exist, expect null
//    }
//
//
//
//    @Test
//    void deleteById() throws XDataAccessException {
//        int count = repository.findAll().size();
//        assertTrue(repository.deleteById(1));
//        assertFalse(repository.deleteById(1024));
//        //  assertEqual
//    }

}

