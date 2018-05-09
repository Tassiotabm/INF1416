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
       
    }

    public static void startSecondScreen() {
        new SecondScreen(controller);
    }
    
    public static void startThirdScreen() {
        new ThirdScreen(controller);
    }
    public static void startForthScreen() {
        new ForthScreen(controller);
    }

}
