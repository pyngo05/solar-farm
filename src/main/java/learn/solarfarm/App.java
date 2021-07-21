package learn.solarfarm;

import learn.solarfarm.data.SolarPanelFileRepository;
import learn.solarfarm.ui.ConsoleIO;
import learn.solarfarm.ui.View;
import learn.solarfarm.domain.SolarPanelService;
import learn.solarfarm.ui.ConsoleIO;
import learn.solarfarm.ui.Controller;

public class App {

    public static void main(String[] args) {
        SolarPanelFileRepository repository = new SolarPanelFileRepository("./data/solarpanels.csv");
        SolarPanelService service = new SolarPanelService(repository);

        ConsoleIO io = new ConsoleIO();
//        View view = new View(io);
        View view = new View();

        Controller controller = new Controller(service, view);

        controller.run();
    }

}
