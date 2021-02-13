package view;

import ScheduleModel.*;
import javafx.stage.FileChooser;
import java.io.File;

/**This class controls the IO for the MainMenu view.*/
public class MainMenuController
{
  private ViewHandler viewHandler;
  private ScheduleModelManager SchModMan;
  private FileChooser fileChooser;
  
  public void Exams()
  {
    viewHandler.switchStage("Exams");
  }

  public void Classes()
  {
    viewHandler.switchStage("Classes");
  }

  public void Students()
  {
    viewHandler.switchStage("Students");
  }

  public void Examiners()
  {
    viewHandler.switchStage("Examiners");
  }
  
  public void Classrooms()
  {
    viewHandler.switchStage("Classrooms");
  }

  public void ExamPeriod()
  {
    viewHandler.switchStage("ExamPeriod");
  }

  public void ClearAll()
  {
    SchModMan.clearAll();
  }

  public void LoadSch()
  {
    fileChooser.setTitle("open saved schedule");
    File selectedFile = fileChooser.showOpenDialog(null);
    if(selectedFile != null)
    {
      SchModMan.unZipSCH(selectedFile.getPath());
      SchModMan.loadXMLs();
    }
  }

  public void SaveSch()
  {
    fileChooser.setTitle("save current schedule");
    File selectedFile = fileChooser.showSaveDialog(null);
    if(selectedFile != null)
    {
      SchModMan.saveXMLs();
      SchModMan.zipXMLs(selectedFile.getPath());
    }
  }

  public void UpdateWeb()
  {
    SchModMan.updateWebXML();
  }

  public void Exit()
  {
    viewHandler.closeView();
  }

  void init(ViewHandler viewHandler, ScheduleModelManager SchModMan)
  {
    this.viewHandler = viewHandler;
    this.SchModMan = SchModMan;
    fileChooser = new FileChooser();
    fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "\\Documents"));
    FileChooser.ExtensionFilter schFilter = new FileChooser.ExtensionFilter("sch archive", "*.sch");
    FileChooser.ExtensionFilter zipFilter = new FileChooser.ExtensionFilter("zip archive", "*.zip");
    fileChooser.getExtensionFilters().add(schFilter);
    fileChooser.getExtensionFilters().add(zipFilter);
    fileChooser.setSelectedExtensionFilter(schFilter);
    fileChooser.setInitialFileName("newSchedule");
  }

  /**called by javaFX*/
  public MainMenuController(){}
}