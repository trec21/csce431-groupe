/**
 * @(#)Connect_Four.java
 *
 *
 * @author
 * @version 1.00 2012/3/18
 */




public class Connect_Four {

    public static void main(String[] args){
    	Game_Window game = new Game_Window(10,10,512);
    	Game_Window_Gui gui = new Game_Window_Gui(game);
    	game.AddGUI(gui);
    	game.Init_All();
    	gui.Init();
    	MouseMethods m = new MouseMethods(game,gui);
    	game.Run();

    }


}