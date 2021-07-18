package learn.solarfarm.domain;

import learn.solarfarm.data.SolarPanelRepository;
import learn.solarfarm.data.XDataAccessException;
import learn.solarfarm.data.SolarPanelRepository;
import learn.solarfarm.models.SolarPanel;
import java.util.List;

// Rules for the SolarPanel model
public class SolarPanelService {

        private final SolarPanelRepository repository;

        public SolarPanelService(SolarPanelRepository repository) {
            this.repository = (SolarPanelRepository) repository;
        }

    public SolarPanelResult findBySection(String section) throws XDataAccessException {
        SolarPanelResult result = new SolarPanelResult();
        if (!repository.findBySection(section)) {
            String message = String.format("Section %s was not found.", section);
            result.addErrorMessage(message);
        }
        return result;
    }

//        public List<SolarPanel> findTracking() throws XDataAccessException {
//            return repository.isTracking(true);
//        }
//
//
//        public MemoryResult add(Memory memory) throws XDataAccessException {
//            MemoryResult result = validate(memory);
//
//            if (memory.getId() > 0) {
//                result.addErrorMessage("Memory `id` should not be set.");
//            }
//
//            if (result.isSuccess()) {
//                memory = repository.add(memory);
//                result.setMemory(memory);
//            }
//            return result;
//        }
//
//        public MemoryResult update(Memory memory) throws XDataAccessException {
//            MemoryResult result = validate(memory);
//
//            if (memory.getId() <= 0) {
//                result.addErrorMessage("Memory `id` is required.");
//            }
//
//            if (result.isSuccess()) {
//                if (repository.update(memory)) {
//                    result.setMemory(memory);
//                } else {
//                    String message = String.format("Memory id %s was not found.", memory.getId());
//                    result.addErrorMessage(message);
//                }
//            }
//            return result;
//        }
//
//        public MemoryResult deleteById(int memoryId) throws XDataAccessException {
//            MemoryResult result = new MemoryResult();
//            if (!repository.deleteById(memoryId)) {
//                String message = String.format("Memory id %s was not found.", memoryId);
//                result.addErrorMessage(message);
//            }
//            return result;
//        }
//
//        private MemoryResult validate(Memory memory) {
//            MemoryResult result = new MemoryResult();
//
//            if (memory == null) {
//                result.addErrorMessage("Memory cannot be null.");
//                return result;
//            }
//
//            if (memory.getFrom() == null || memory.getFrom().isBlank()) {
//                result.addErrorMessage("Memory `from` is required.");
//            }
//
//            if (memory.getContent() == null || memory.getContent().isBlank()) {
//                result.addErrorMessage("Memory `content` is required.");
//            }
//
//            return result;
//        }
}