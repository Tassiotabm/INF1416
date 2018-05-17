package frontend;

import backend.QueryController.IQueryController;
import frontend.Screens.FirstScreen;
import frontend.Screens.ForthScreen;
import frontend.Screens.SecondScreen;
import frontend.Screens.ThirdScreen;

public abstract class InterfaceController {

    private static IQueryController controller;

    public static void init(IQueryController _controller) {
        controller = _controller;
        new FirstScreen(controller);
        //new SecondScreen(controller);
        //new ThirdScreen(controller);
        //new ForthScreen(controller, "Teste", "Administrator", "Teste", 1,2,3);
    }

    public static void startSecondScreen() {
        new SecondScreen(controller);
    }

    public static void startThirdScreen() {
        new ThirdScreen(controller);
    }

    public static void startForthScreen(String Login, String Grupo, String Name) {
        new ForthScreen(controller, Login, Grupo , Name, 1,2,3);
    }
}
