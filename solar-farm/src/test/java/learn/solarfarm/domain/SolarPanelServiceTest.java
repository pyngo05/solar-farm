package learn.solarfarm.domain;

import learn.solarfarm.data.SolarPanelFileRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static org.junit.jupiter.api.Assertions.*;

class SolarPanelServiceTest {

        static final String SEED_FILE_PATH = "./data/solarpanel-seed.txt";
        static final String TEST_FILE_PATH = "./data/solarpanel-test.txt";

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
    void findBySection() {
    }
}