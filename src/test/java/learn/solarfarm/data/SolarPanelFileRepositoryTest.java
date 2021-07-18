package learn.solarfarm.data;

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

    static final String SEED_FILE_PATH = "./data/dataaccess-testdata.txt";
    static final String TEST_FILE_PATH = "./data/dataaccess-testdata-copy.txt";

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
        assertEquals(3, actual.size());
    }

    @Test
    void findBySection() throws XDataAccessException {
        List<SolarPanel> actual = repository.findBySection("Other Section");
        assertEquals(1, actual.size());

        actual = repository.findBySection("Non-existent Section");
        assertEquals(0, actual.size());
    }

//    @Test
//    void add() throws XDataAccessException {
//        SolarPanel panel = new SolarPanel();
//        panel.setFrom("A Friend");
//        panel.setContent("A special memory.");
//
//        SolarPanel actual = repository.add(actual);
//        assertEquals(4, actual.getId());
//
//        List<SolarPanel> all = repository.findAll();
//        assertEquals(4, all.size());
//
//        actual = all.get(3);                        // the newly-added memory
//        assertEquals(4, actual.getId());
//        assertEquals("A Friend", actual.getFrom()); // confirm all fields
//        assertEquals("A special memory.", actual.getContent());
//        assertFalse(actual.isShareable());
//    }


//    @Test
//    void findShareable() throws XDataAccessException {
//        List<Memory> actual = repository.findShareable(true);
//        assertEquals(2, actual.size()); // seed data has 2 shareable memories
//
//        actual = repository.findShareable(false);
//        assertEquals(1, actual.size()); // 1 non-shareable memory
//    }
//
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
//    @Test
//    void update() throws XDataAccessException {
//        Memory memory = repository.findById(2);
//        memory.setFrom("Sherwin");                    // was Uncle Sherwin
//        memory.setShareable(false);                   // was true
//        assertTrue(repository.update(memory));
//
//        memory = repository.findById(2);
//        assertNotNull(memory);                        // confirm the memory exists
//        assertEquals("Sherwin", memory.getFrom());    // confirm the memory was updated
//        assertFalse(memory.isShareable());
//
//        Memory doesNotExist = new Memory();
//        doesNotExist.setId(1024);
//        assertFalse(repository.update(doesNotExist)); // can't update a memory that doesn't exist
//    }
//
//    @Test
//    void deleteById() throws XDataAccessException {
//        int count = repository.findAll().size();
//        assertTrue(repository.deleteById(1));
//        assertFalse(repository.deleteById(1024));
//        //  assertEqual
//    }

}

