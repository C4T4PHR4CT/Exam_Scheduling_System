package view;

import ScheduleModel.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**This class controls the IO for the Students view.*/
public class StudentsController
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

  @FXML private Button ViaOrd;
  public void ViaidOrder()
  {
    NamOrd.setText("Name");
    ClsOrd.setText("Class");
    GrpOrd.setText("Group");
    OrdTyp = "VIAID";
    if (ViaOrd.getText().substring(ViaOrd.getText().length() - 2).equals("\\/"))
    {
      OrdRev = true;
      SchModMan.orderStudents(OrdTyp, true);
      ViaOrd.setText("VIAID /\\");
    }
    else
    {
      OrdRev = false;
      SchModMan.orderStudents(OrdTyp, false);
      ViaOrd.setText("VIAID \\/");
    }
    refreshList();
  }

  @FXML private Button NamOrd;
  public void NameOrder()
  {
    ViaOrd.setText("VIAID");
    ClsOrd.setText("Class");
    GrpOrd.setText("Group");
    OrdTyp = "Name";
    if (NamOrd.getText().substring(NamOrd.getText().length() - 2).equals("\\/"))
    {
      OrdRev = true;
      SchModMan.orderStudents(OrdTyp, true);
      NamOrd.setText("Name /\\");
    }
    else
    {
      OrdRev = false;
      SchModMan.orderStudents(OrdTyp, false);
      NamOrd.setText("Name \\/");
    }
    refreshList();
  }

  @FXML private Button ClsOrd;
  public void ClassOrder()
  {
    ViaOrd.setText("VIAID");
    NamOrd.setText("Name");
    GrpOrd.setText("Group");
    OrdTyp = "Class";
    if (ClsOrd.getText().substring(ClsOrd.getText().length() - 2).equals("\\/"))
    {
      OrdRev = true;
      SchModMan.orderStudents(OrdTyp, true);
      ClsOrd.setText("Class /\\");
    }
    else
    {
      OrdRev = false;
      SchModMan.orderStudents(OrdTyp, false);
      ClsOrd.setText("Class \\/");
    }
    refreshList();
  }

  @FXML private Button GrpOrd;
  public void GroupOrder()
  {
    ViaOrd.setText("VIAID");
    NamOrd.setText("Name");
    ClsOrd.setText("Class");
    OrdTyp = "Group";
    if (GrpOrd.getText().substring(GrpOrd.getText().length() - 2).equals("\\/"))
    {
      OrdRev = true;
      SchModMan.orderStudents(OrdTyp, true);
      GrpOrd.setText("Group /\\");
    }
    else
    {
      OrdRev = false;
      SchModMan.orderStudents(OrdTyp, false);
      GrpOrd.setText("Group \\/");
    }
    refreshList();
  }

  @FXML private TextField ViaInp;
  public void Viaid()
  {
    Students.getSelectionModel().select(null);
    if (!ViaInp.getText().equals(""))
    {
      CursorPos = ViaInp.getCaretPosition();
      ViaInp.setText(ViaInp.getText().replaceAll("[^\\d]", ""));
      if (ViaInp.getText().length() > 6)
        ViaInp.setText(ViaInp.getText().substring(0, 6));
      selectList();
      ViaInp.positionCaret(CursorPos);
    }
  }

  @FXML private TextField NamInp;
  public void Name()
  {
    if (!NamInp.getText().equals(""))
    {
      CursorPos = NamInp.getCaretPosition();
      if (NamInp.getText().length() > 21)
        NamInp.setText(NamInp.getText().substring(0, 21));
      NamInp.positionCaret(CursorPos);
    }
  }

  @FXML private TextField GrpInp;
  public void Group()
  {
    if (!GrpInp.getText().equals(""))
    {
      CursorPos = GrpInp.getCaretPosition();
      GrpInp.setText(GrpInp.getText().replaceAll("[^\\d]", ""));
      if (GrpInp.getText().length() > 2)
        GrpInp.setText(GrpInp.getText().substring(0, 2));
      GrpInp.positionCaret(CursorPos);
    }
  }

  public void AddStudent()
  {
    if (!ViaInp.getText().equals("") && !NamInp.getText().matches("^ *$") & !GrpInp.getText().equals(""))
    {
      SchModMan.removeStudent(Integer.parseInt(ViaInp.getText()));
      SchModMan.newStudent(NamInp.getText().trim(),Integer.parseInt(ViaInp.getText()),ClsInp.getSelectionModel().getSelectedItem(),Integer.parseInt(GrpInp.getText()));
      SchModMan.orderStudents(OrdTyp, OrdRev);
      refreshList();
      selectList();
    }
  }

  public void RemoveStudent()
  {
    if (!ViaInp.getText().equals(""))
    {
      SchModMan.removeStudent(Integer.parseInt(ViaInp.getText()));
      SchModMan.orderStudents(OrdTyp, OrdRev);
      refreshList();
    }
  }

  @FXML private ChoiceBox<String> ClsInp;
  @FXML private ListView<String> Students;
  void init(ViewHandler viewHandler, ScheduleModelManager SchModMan)
  {
    this.viewHandler = viewHandler;
    this.SchModMan = SchModMan;
    this.OrdTyp = "VIAID";
    this.OrdRev = false;
    SchModMan.orderStudents(OrdTyp,false);
    Students.setStyle("-fx-font-family: Consolas;-fx-font-size:15");
    ClsInp.getItems().add("!Unassigned");
    for (int i = 0; i < SchModMan.getClasses().length; i++)
    {
      ClsInp.getItems().add(SchModMan.getClasses()[i].getName());
    }
    ClsInp.getSelectionModel().select("!Unassigned");
    refreshList();
    Students.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
    {
      if (newValue != null)
      {
        Student currentStudent = SchModMan.getStudent(Integer.parseInt(newValue.substring(0, 9).trim()));
        ViaInp.setText(String.valueOf(currentStudent.getVIAID()));
        NamInp.setText(currentStudent.getName());
        GrpInp.setText(String.valueOf(currentStudent.getGroup()));
        if (currentStudent.getParticipating().charAt(0) != '!')
          ClsInp.getSelectionModel().select(currentStudent.getParticipating());
      }
    });
  }

  /**Selects and scrolls to the current element inputted in the unique identifier' input TextField (if such element exists).*/
  private void selectList()
  {
    refreshList();
    for (int i = 0; i < Students.getItems().size(); i++)
    {
      if (Students.getItems().get(i).substring(0,9).trim().equals(ViaInp.getText()))
      {
        Students.getSelectionModel().select(i);
        if (i >= 1)
          Students.scrollTo(i - 1);
        else
          Students.scrollTo(0);
        break;
      }
    }
  }

  /**Refreshes the ListView in this view.*/
  private void refreshList()
  {
    String line;
    int temp;
    Students.getItems().clear();
    for (int i = 0; i < SchModMan.getStudents().length; i++)
    {
      line = "";
      line += SchModMan.getStudents()[i].getVIAID();
      temp = line.length();
      for (int j = 0; j < 9 - temp; j++)
        line += " ";
      line += SchModMan.getStudents()[i].getName();
      temp = line.length();
      for (int j = 0; j < 32 - temp; j++)
        line += " ";
      line += SchModMan.getStudents()[i].getParticipating();
      temp = line.length();
      for (int j = 0; j < 49 - temp; j++)
        line += " ";
      line += SchModMan.getStudents()[i].getGroup();
      Students.getItems().add(line);
    }
  }

  /**called by javaFX*/
  public StudentsController(){}
}