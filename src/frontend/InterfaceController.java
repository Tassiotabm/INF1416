package frontend;

import backend.QueryController.IQueryController;
import frontend.Screens.FirstScreen;
import frontend.Screens.SecondScreen;

public abstract class InterfaceController {
	
	private static IQueryController controller;
	
	public static void init(IQueryController _controller) {
		controller = _controller;
		new FirstScreen(controller);
	}
	
	public static void startSecondScreen() {
		new SecondScreen(controller);
	}
	
}
