package view;

import ScheduleModel.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**This class controls the IO for the Classes view.*/
public class ClassesController
{
  private ViewHandler viewHandler;
  private ScheduleModelManager SchModMan;

  public void Back()
  {
    viewHandler.switchStage("MainMenu");
  }

  private int CursorPos;

  private String OrdTyp;
  private boolean OrdRev;

  @FXML private Button NamOrd;
  public void NameOrder()
  {
    SizOrd.setText("Participants");
    GrpOrd.setText("Groups");
    OrdTyp = "Name";
    if (NamOrd.getText().substring(NamOrd.getText().length() - 2).equals("\\/"))
    {
      OrdRev = true;
      SchModMan.orderClasses(OrdTyp, true);
      NamOrd.setText("Name /\\");
    }
    else
    {
      OrdRev = false;
      SchModMan.orderClasses(OrdTyp, false);
      NamOrd.setText("Name \\/");
    }
    refreshList();
  }

  @FXML private Button SizOrd;
  public void SizeOrder()
  {
    NamOrd.setText("Name");
    GrpOrd.setText("Groups");
    OrdTyp = "Size";
    if (SizOrd.getText().substring(SizOrd.getText().length() - 2).equals("\\/"))
    {
      OrdRev = true;
      SchModMan.orderClasses(OrdTyp, true);
      SizOrd.setText("Participants /\\");
    }
    else
    {
      OrdRev = false;
      SchModMan.orderClasses(OrdTyp, false);
      SizOrd.setText("Participants \\/");
    }
    refreshList();
  }

  @FXML private Button GrpOrd;
  public void GroupOrder()
  {
    SizOrd.setText("Participants");
    NamOrd.setText("Name");
    OrdTyp = "Group";
    if (GrpOrd.getText().substring(GrpOrd.getText().length() - 2).equals("\\/"))
    {
      OrdRev = true;
      SchModMan.orderClasses(OrdTyp, true);
      GrpOrd.setText("Groups /\\");
    }
    else
    {
      OrdRev = false;
      SchModMan.orderClasses(OrdTyp, false);
      GrpOrd.setText("Groups \\/");
    }
    refreshList();
  }

  @FXML private TextField NamInp;
  public void Name()
  {
    Classes.getSelectionModel().select(null);
    if (!NamInp.getText().equals(""))
    {
      CursorPos = NamInp.getCaretPosition();
      if (NamInp.getText().length() > 14)
        NamInp.setText(NamInp.getText().substring(0, 14));
      selectList();
      NamInp.positionCaret(CursorPos);
    }
  }

  public void AddClass()
  {
    if (!NamInp.getText().matches("^ *$"))
    {
      SchModMan.newClass(NamInp.getText().trim());
      SchModMan.orderClasses(OrdTyp, OrdRev);
      refreshList();
      selectList();
    }
  }

  public void RemoveClass()
  {
    SchModMan.removeClass(NamInp.getText(), false);
    SchModMan.orderClasses(OrdTyp, OrdRev);
    refreshList();
  }

  public void RemoveClassStud()
  {
    SchModMan.removeClass(NamInp.getText(), true);
    SchModMan.orderClasses(OrdTyp, OrdRev);
    Classes.getItems().clear();
    refreshList();
  }

  public void RenameClass()
  {
    if (!ReNamInp.getText().matches("^ *$"))
    {
      SchModMan.reNameClass(NamInp.getText(), ReNamInp.getText().trim());
      SchModMan.orderClasses(OrdTyp, OrdRev);
      refreshList();
    }
  }

  @FXML private TextField ReNamInp;
  public void ReName()
  {
    if (!ReNamInp.getText().equals(""))
    {
      CursorPos = ReNamInp.getCaretPosition();
      if (ReNamInp.getText().length() > 14)
        ReNamInp.setText(ReNamInp.getText().substring(0, 14));
      ReNamInp.positionCaret(CursorPos);
    }
  }

  @FXML private ListView<String> Classes;
  void init(ViewHandler viewHandler, ScheduleModelManager SchModMan)
  {
    this.viewHandler = viewHandler;
    this.SchModMan = SchModMan;
    this.OrdTyp = "Name";
    this.OrdRev = false;
    SchModMan.orderClasses(OrdTyp, false);
    Classes.setStyle("-fx-font-family: Consolas;-fx-font-size:16");
    refreshList();
    Classes.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
    {
      if (newValue != null)
      {
        NamInp.setText(newValue.substring(0, 22).trim());
      }
    });
  }

  private void selectList()
  {
    for (int i = 0; i < Classes.getItems().size(); i++)
    {
      if (Classes.getItems().get(i).substring(0, 22).trim().equals(NamInp.getText()))
      {
        Classes.getSelectionModel().select(i);
        if (i >= 1)
          Classes.scrollTo(i - 1);
        else
          Classes.scrollTo(0);
      }
    }
  }

  /**Refreshes the ListView in this view.*/
  private void refreshList()
  {
    String line;
    int temp;
    Classes.getItems().clear();
    for (int i = 0; i < SchModMan.getClasses().length; i++)
    {
      line = "";
      line += SchModMan.getClasses()[i].getName();
      temp = line.length();
      for (int j = 0; j < 22 - temp; j++)
        line += " ";
      line += SchModMan.getClasses()[i].getNumberOfStudents();
      temp = line.length();
      for (int j = 0; j < 36 - temp; j++)
        line += " ";
      line += SchModMan.getClasses()[i].getNumberOfGroups();
      Classes.getItems().add(line);
    }
  }

  /**called by javaFX*/
  public ClassesController(){}
}