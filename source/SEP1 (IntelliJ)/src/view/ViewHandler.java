package view;

import ScheduleModel.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

/**This class is used to handle the Scenes, Stages, Controllers, and initialize the ModelManager for the project.*/
public class ViewHandler
{
  private Scene currentScene;
  private Stage primaryStage;
  private ScheduleModelManager SchModMan;

  /**The constructor initializes the currentScene by creating a new Scene.*/
  public ViewHandler()
  {
    currentScene = new Scene(new Region());
  }

  /**This method initializes the primaryStage, creates a ScheduleModelManager, and switches to the MainMenu Stage.*/
  public void start(Stage primaryStage)
  {
    this.primaryStage = primaryStage;
    SchModMan = new ScheduleModelManager();
    switchStage("MainMenu", false);
  }

  /**This method calls the extended switchStage method with default values for the extra arguments.*/
  void switchStage(String stage)
  {
    switchStage(stage, true);
  }

  /**This switches to a different stage defined in the argument, and also has the option to keep the window size from before the switch.*/
  void switchStage(String stage, boolean keepSize)
  {
    try
    {
      double Width = primaryStage.getWidth();
      double Height = primaryStage.getHeight();
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource(stage + ".fxml"));
      Region root = loader.load();
      switch (stage)
      {
        case "MainMenu":
          MainMenuController controller1 = loader.getController();
          controller1.init(this,SchModMan);
          break;
        case "ExamPeriod":
          ExamPeriodController controller2 = loader.getController();
          controller2.init(this,SchModMan);
          break;
        case "Classrooms":
          ClassroomsController controller3 = loader.getController();
          controller3.init(this,SchModMan);
          break;
        case "Examiners":
          ExaminersController controller4 = loader.getController();
          controller4.init(this,SchModMan);
          break;
        case "Students":
          StudentsController controller5 = loader.getController();
          controller5.init(this,SchModMan);
          break;
        case "Classes":
          ClassesController controller6 = loader.getController();
          controller6.init(this,SchModMan);
          break;
        case "Exams":
          ExamsController controller7 = loader.getController();
          controller7.init(this,SchModMan);
          break;
      }
      currentScene.setRoot(root);
      String title = "";
      if (root.getUserData() != null)
      {
        title += root.getUserData();
      }
      primaryStage.setTitle(title);
      primaryStage.setScene(currentScene);
      primaryStage.setWidth(root.getPrefWidth());
      primaryStage.setHeight(root.getPrefHeight());
      primaryStage.show();
      primaryStage.setMinWidth(root.minWidth(-1));
      primaryStage.setMinHeight(root.minHeight(-1));
      if (keepSize)
      {
        if (primaryStage.getMinWidth() <= Width)
          primaryStage.setWidth(Width);
        if (primaryStage.getMinHeight() <= Height)
          primaryStage.setHeight(Height);
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  /**This method closes the primaryStage.*/
  void closeView()
  {
    primaryStage.close();
  }
}
