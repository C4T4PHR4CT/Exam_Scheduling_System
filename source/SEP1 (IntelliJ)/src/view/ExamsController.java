package view;

import ScheduleModel.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**This class controls the IO for the Exams view.*/
public class ExamsController
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
  private boolean ExpDet;

  @FXML private Button TypOrd;
  public void TypeOrder()
  {
    NamOrd.setText("Name");
    PlcOrd.setText("Place");
    ClsOrd.setText("Class");
    Exa1Ord.setText("Examiner 1");
    Exa2Ord.setText("Examiner 2");
    StrDatOrd.setText("Start date");
    StrTimOrd.setText("Start time");
    DurOrd.setText("Dur");
    EndOrd.setText("End");
    OrdTyp = "Type";
    if (TypOrd.getText().substring(TypOrd.getText().length() - 2).equals("\\/"))
    {
      OrdRev = true;
      SchModMan.orderExams(OrdTyp, true);
      TypOrd.setText("Type /\\");
    }
    else
    {
      OrdRev = false;
      SchModMan.orderExams(OrdTyp, false);
      TypOrd.setText("Type \\/");
    }
    refreshList();
  }

  @FXML private Button NamOrd;
  public void NameOrder()
  {
    TypOrd.setText("Type");
    PlcOrd.setText("Place");
    ClsOrd.setText("Class");
    Exa1Ord.setText("Examiner 1");
    Exa2Ord.setText("Examiner 2");
    StrDatOrd.setText("Start date");
    StrTimOrd.setText("Start time");
    DurOrd.setText("Dur");
    EndOrd.setText("End");
    OrdTyp = "Name";
    if (NamOrd.getText().substring(NamOrd.getText().length() - 2).equals("\\/"))
    {
      OrdRev = true;
      SchModMan.orderExams(OrdTyp, true);
      NamOrd.setText("Name /\\");
    }
    else
    {
      OrdRev = false;
      SchModMan.orderExams(OrdTyp, false);
      NamOrd.setText("Name \\/");
    }
    refreshList();
  }

  @FXML private Button PlcOrd;
  public void PlaceOrder()
  {
    TypOrd.setText("Type");
    NamOrd.setText("Name");
    ClsOrd.setText("Class");
    Exa1Ord.setText("Examiner 1");
    Exa2Ord.setText("Examiner 2");
    StrDatOrd.setText("Start date");
    StrTimOrd.setText("Start time");
    DurOrd.setText("Dur");
    EndOrd.setText("End");
    OrdTyp = "Place";
    if (PlcOrd.getText().substring(PlcOrd.getText().length() - 2).equals("\\/"))
    {
      OrdRev = true;
      SchModMan.orderExams(OrdTyp, true);
      PlcOrd.setText("Place /\\");
    }
    else
    {
      OrdRev = false;
      SchModMan.orderExams(OrdTyp, false);
      PlcOrd.setText("Place \\/");
    }
    refreshList();
  }

  @FXML private Button ClsOrd;
  public void ClassOrder()
  {
    TypOrd.setText("Type");
    NamOrd.setText("Name");
    PlcOrd.setText("Place");
    Exa1Ord.setText("Examiner 1");
    Exa2Ord.setText("Examiner 2");
    StrDatOrd.setText("Start date");
    StrTimOrd.setText("Start time");
    DurOrd.setText("Dur");
    EndOrd.setText("End");
    OrdTyp = "Class";
    if (ClsOrd.getText().substring(ClsOrd.getText().length() - 2).equals("\\/"))
    {
      OrdRev = true;
      SchModMan.orderExams(OrdTyp, true);
      ClsOrd.setText("Class /\\");
    }
    else
    {
      OrdRev = false;
      SchModMan.orderExams(OrdTyp, false);
      ClsOrd.setText("Class \\/");
    }
    refreshList();
  }

  @FXML private Button Exa1Ord;
  public void Examiner1Order()
  {
    TypOrd.setText("Type");
    NamOrd.setText("Name");
    PlcOrd.setText("Place");
    ClsOrd.setText("Class");
    Exa2Ord.setText("Examiner 2");
    StrDatOrd.setText("Start date");
    StrTimOrd.setText("Start time");
    DurOrd.setText("Dur");
    EndOrd.setText("End");
    OrdTyp = "Examiner1";
    if (Exa1Ord.getText().substring(Exa1Ord.getText().length() - 2).equals("\\/"))
    {
      OrdRev = true;
      SchModMan.orderExams(OrdTyp, true);
      Exa1Ord.setText("Examiner 1 /\\");
    }
    else
    {
      OrdRev = false;
      SchModMan.orderExams(OrdTyp, false);
      Exa1Ord.setText("Examiner 1 \\/");
    }
    refreshList();
  }

  @FXML private Button Exa2Ord;
  public void Examiner2Order()
  {
    TypOrd.setText("Type");
    NamOrd.setText("Name");
    PlcOrd.setText("Place");
    ClsOrd.setText("Class");
    Exa1Ord.setText("Examiner 1");
    StrDatOrd.setText("Start date");
    StrTimOrd.setText("Start time");
    DurOrd.setText("Dur");
    EndOrd.setText("End");
    OrdTyp = "Examiner2";
    if (Exa2Ord.getText().substring(Exa2Ord.getText().length() - 2).equals("\\/"))
    {
      OrdRev = true;
      SchModMan.orderExams(OrdTyp, true);
      Exa2Ord.setText("Examiner 2 /\\");
    }
    else
    {
      OrdRev = false;
      SchModMan.orderExams(OrdTyp, false);
      Exa2Ord.setText("Examiner 2 \\/");
    }
    refreshList();
  }

  @FXML private Button StrDatOrd;
  public void StartDateOrder()
  {
    TypOrd.setText("Type");
    NamOrd.setText("Name");
    PlcOrd.setText("Place");
    ClsOrd.setText("Class");
    Exa1Ord.setText("Examiner 1");
    Exa2Ord.setText("Examiner 2");
    StrTimOrd.setText("Start time");
    DurOrd.setText("Dur");
    EndOrd.setText("End");
    OrdTyp = "Start";
    if (StrDatOrd.getText().substring(StrDatOrd.getText().length() - 2).equals("\\/"))
    {
      OrdRev = true;
      SchModMan.orderExams(OrdTyp, true);
      StrDatOrd.setText("Start date /\\");
    }
    else
    {
      OrdRev = false;
      SchModMan.orderExams(OrdTyp, false);
      StrDatOrd.setText("Start date \\/");
    }
    refreshList();
  }

  @FXML private Button StrTimOrd;
  public void StartTimeOrder()
  {
    TypOrd.setText("Type");
    NamOrd.setText("Name");
    PlcOrd.setText("Place");
    ClsOrd.setText("Class");
    Exa1Ord.setText("Examiner 1");
    Exa2Ord.setText("Examiner 2");
    StrDatOrd.setText("Start date");
    DurOrd.setText("Dur");
    EndOrd.setText("End");
    OrdTyp = "StartTime";
    if (StrTimOrd.getText().substring(StrTimOrd.getText().length() - 2).equals("\\/"))
    {
      OrdRev = true;
      SchModMan.orderExams(OrdTyp, true);
      StrTimOrd.setText("Start time /\\");
    }
    else
    {
      OrdRev = false;
      SchModMan.orderExams(OrdTyp, false);
      StrTimOrd.setText("Start time \\/");
    }
    refreshList();
  }

  @FXML private Button DurOrd;
  public void DurationOrder()
  {
    TypOrd.setText("Type");
    NamOrd.setText("Name");
    PlcOrd.setText("Place");
    ClsOrd.setText("Class");
    Exa1Ord.setText("Examiner 1");
    Exa2Ord.setText("Examiner 2");
    StrDatOrd.setText("Start date");
    StrTimOrd.setText("Start time");
    EndOrd.setText("End");
    OrdTyp = "Duration";
    if (DurOrd.getText().substring(DurOrd.getText().length() - 2).equals("\\/"))
    {
      OrdRev = true;
      SchModMan.orderExams(OrdTyp, true);
      DurOrd.setText("Dur /\\");
    }
    else
    {
      OrdRev = false;
      SchModMan.orderExams(OrdTyp, false);
      DurOrd.setText("Dur \\/");
    }
    refreshList();
  }

  @FXML private Button EndOrd;
  public void EndOrder()
  {
    TypOrd.setText("Type");
    NamOrd.setText("Name");
    PlcOrd.setText("Place");
    ClsOrd.setText("Class");
    Exa1Ord.setText("Examiner 1");
    Exa2Ord.setText("Examiner 2");
    StrDatOrd.setText("Start date");
    StrTimOrd.setText("Start time");
    DurOrd.setText("Dur");
    OrdTyp = "End";
    if (EndOrd.getText().substring(EndOrd.getText().length() - 2).equals("\\/"))
    {
      OrdRev = true;
      SchModMan.orderExams(OrdTyp, true);
      EndOrd.setText("End /\\");
    }
    else
    {
      OrdRev = false;
      SchModMan.orderExams(OrdTyp, false);
      EndOrd.setText("End \\/");
    }
    refreshList();
  }

  @FXML private TextField NamInp;
  public void Name()
  {
    Exams.getSelectionModel().select(null);
    if (!NamInp.getText().equals(""))
    {
      CursorPos = NamInp.getCaretPosition();
      if (NamInp.getText().length() > 14)
        NamInp.setText(NamInp.getText().substring(0, 14));
      selectList();
      NamInp.positionCaret(CursorPos);
    }
  }

  @FXML private TextField StrTimH;
  public void StartTimeHour()
  {
    if (!StrTimH.getText().equals(""))
    {
      CursorPos = StrTimH.getCaretPosition();
      StrTimH.setText(StrTimH.getText().replaceAll("[^\\d]", ""));
      if (StrTimH.getText().length() > 2)
        StrTimH.setText(StrTimH.getText().substring(0, 2));
      if (Integer.parseInt(StrTimH.getText()) > 23)
        StrTimH.setText(StrTimH.getText().substring(0, 1));
      StrTimH.positionCaret(CursorPos);
    }
  }

  @FXML private TextField StrTimM;
  public void StartTimeMinute()
  {
    if (!StrTimM.getText().equals(""))
    {
      CursorPos = StrTimM.getCaretPosition();
      StrTimM.setText(StrTimM.getText().replaceAll("[^\\d]", ""));
      if (StrTimM.getText().length() > 2)
        StrTimM.setText(StrTimM.getText().substring(0, 2));
      if (Integer.parseInt(StrTimM.getText()) > 59)
        StrTimM.setText(StrTimM.getText().substring(0, 1));
      StrTimM.positionCaret(CursorPos);
    }
  }

  @FXML private TextField DurInp;
  public void Duration()
  {
    if (!DurInp.getText().equals(""))
    {
      CursorPos = DurInp.getCaretPosition();
      DurInp.setText(DurInp.getText().replaceAll("[^\\d]", ""));
      if (DurInp.getText().length() > 5)
        DurInp.setText(DurInp.getText().substring(0, 5));
      DurInp.positionCaret(CursorPos);
    }
  }

  @FXML private TextField BrkDur;
  public void BreakDuration()
  {
    if (!BrkDur.getText().equals(""))
    {
      CursorPos = BrkDur.getCaretPosition();
      BrkDur.setText(BrkDur.getText().replaceAll("[^\\d]", ""));
      if (BrkDur.getText().length() > 3)
        BrkDur.setText(BrkDur.getText().substring(0, 3));
      BrkDur.positionCaret(CursorPos);
    }
  }

  @FXML private TextField DurPerStd;
  public void DurationPerStudent()
  {
    if (!DurPerStd.getText().equals(""))
    {
      CursorPos = DurPerStd.getCaretPosition();
      DurPerStd.setText(DurPerStd.getText().replaceAll("[^\\d]", ""));
      if (DurPerStd.getText().length() > 3)
        DurPerStd.setText(DurPerStd.getText().substring(0, 3));
      DurPerStd.positionCaret(CursorPos);
    }
  }

  public void AddExam()
  {
    if (!NamInp.getText().matches("^ *$") && StrDatInp.getValue() != null && !StrTimH.getText().equals("") && !StrTimM.getText().equals(""))
    {
      SchModMan.removeExam(NamInp.getText().trim());
      SchModMan.newExam(TypInp.getValue(),NamInp.getText().trim(),PlcInp.getValue(),ClsInp.getValue(),Exa1Inp.getValue(),Exa2Inp.getValue(),ReqInp.getText().split(", "),new Date(StrDatInp.getValue()).mergeDate(new Date(Integer.parseInt(StrTimH.getText()),Integer.parseInt(StrTimM.getText()))),DurInp.getText(),BrkDur.getText(),DurPerStd.getText());
      refreshList();
      selectList(NamInp.getText().trim());
    }
  }

  public void RemoveExam()
  {
    if (!NamInp.getText().equals(""))
    {
      SchModMan.removeExam(NamInp.getText());
      refreshList();
    }
  }

  @FXML private CheckBox Expand;
  public void ExpandDetails()
  {
    if (Expand.isSelected())
    {
      ExpDet = true;
    }
    else
    {
      ExpDet = false;
    }
    if (Exams.getSelectionModel().getSelectedItem() != null)
    {
      String selected = Exams.getSelectionModel().getSelectedItem().substring(10, 31).trim();
      refreshList();
      selectList(selected);
    }
    else
    {
      refreshList();
    }
  }

  public void CheckConflicts()
  {
    SchModMan.generateConflicts();
    refreshList();
  }

  @FXML private ChoiceBox<String> TypInp;
  @FXML private ChoiceBox<String> PlcInp;
  @FXML private ChoiceBox<String> ClsInp;
  @FXML private ChoiceBox<String> Exa1Inp;
  @FXML private ChoiceBox<String> Exa2Inp;
  @FXML private DatePicker StrDatInp;
  @FXML private TextField ReqInp;
  @FXML private ListView<String> Exams;
  void init(ViewHandler viewHandler, ScheduleModelManager SchModMan)
  {
    this.viewHandler = viewHandler;
    this.SchModMan = SchModMan;
    this.OrdTyp = "Type";
    this.OrdRev = false;
    SchModMan.orderExams(OrdTyp,OrdRev);
    this.ExpDet = false;
    Exams.setStyle("-fx-font-family: 'Consolas';-fx-font-size:16");
    TypInp.getItems().add("Written");
    TypInp.getItems().add("Oral");
    TypInp.getItems().add("Project");
    TypInp.getSelectionModel().select("Written");
    PlcInp.getItems().add("!Unassigned");
    for (int i = 0; i < SchModMan.getRooms().length; i++)
    {
      PlcInp.getItems().add(SchModMan.getRooms()[i].getName());
    }
    PlcInp.getSelectionModel().select("!Unassigned");
    ClsInp.getItems().add("!Unassigned");
    for (int i = 0; i < SchModMan.getClasses().length; i++)
    {
      ClsInp.getItems().add(SchModMan.getClasses()[i].getName());
    }
    ClsInp.getSelectionModel().select("!Unassigned");
    Exa1Inp.getItems().add("!Unassigned");
    Exa2Inp.getItems().add("!Unassigned");
    for (int i = 0; i < SchModMan.getExaminers().length; i++)
    {
      Exa1Inp.getItems().add(SchModMan.getExaminers()[i].getName());
      Exa2Inp.getItems().add(SchModMan.getExaminers()[i].getName());
    }
    Exa1Inp.getSelectionModel().select("!Unassigned");
    Exa2Inp.getSelectionModel().select("!Unassigned");
    refreshList();
    Exams.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
    {
      if (newValue != null)
      {
        if(newValue.substring(0,15).equals(" ├── Conflicts:"))
          Exams.getSelectionModel().select(Exams.getSelectionModel().getSelectedIndex() - 1);
        else if(newValue.substring(0,18).equals(" ├── Requirements:"))
          Exams.getSelectionModel().select(Exams.getSelectionModel().getSelectedIndex() - 2);
        else if(newValue.substring(0,16).equals(" └── Equipments:"))
          Exams.getSelectionModel().select(Exams.getSelectionModel().getSelectedIndex() - 3);
        else
        {
          Exam currentExam = SchModMan.getExam(Exams.getSelectionModel().getSelectedItem().substring(10, 31).trim());
          TypInp.getSelectionModel().select(currentExam.getClass().getName().substring(14, currentExam.getClass().getName().length() - 4));
          NamInp.setText(currentExam.getName());
          PlcInp.getSelectionModel().select(currentExam.getPlace().getName());
          ClsInp.getSelectionModel().select(currentExam.getParticipatingClass().getName());
          Exa1Inp.getSelectionModel().select(currentExam.getExaminer().getName());
          StrDatInp.setValue(currentExam.getStartDate().toLocalDate());
          StrTimH.setText(String.valueOf(currentExam.getStartDate().getHour()));
          StrTimM.setText(String.valueOf(currentExam.getStartDate().getMinute()));
          DurInp.setText(String.valueOf(currentExam.getDuration()));
          String temp = "";
          for (int i = 0; i < currentExam.getRequirements().length; i++)
          {
            temp += currentExam.getRequirements()[i] + ", ";
          }
          if (currentExam.getRequirements().length > 0)
            temp = temp.substring(0, temp.length() - 2);
          ReqInp.setText(temp);
          switch (currentExam.getClass().getName().substring(14, currentExam.getClass().getName().length() - 4))
          {
            case "Written":
              BrkDur.setText("");
              DurPerStd.setText("");
              break;
            case "Oral":
              Exa2Inp.getSelectionModel().select(((OralExam) currentExam).getExaminer2().getName());
              BrkDur.setText(String.valueOf(((OralExam) currentExam).getBreakDurationPerStudent()));
              DurPerStd.setText(String.valueOf(((OralExam) currentExam).getDurationPerStudent()));
              break;
            case "Project":
              Exa2Inp.getSelectionModel().select(((ProjectExam) currentExam).getExaminer2().getName());
              BrkDur.setText(String.valueOf(((ProjectExam) currentExam).getBreakDurationPerGroup()));
              DurPerStd.setText(String.valueOf(((ProjectExam) currentExam).getDurationPerStudentPerGroup()));
              break;
          }
        }
      }
    });
  }

  /**Selects and scrolls to the current element inputted in the unique identifier' input TextField (if such element exists).*/
  private void selectList(String Name)
  {
    for (int i = 0; i < Exams.getItems().size(); i++)
    {
      try
      {
        if (Exams.getItems().get(i).substring(10, 31).trim().equals(Name))
        {
          Exams.getSelectionModel().select(i);
          if (i >= 1)
            Exams.scrollTo(i - 1);
          else
            Exams.scrollTo(0);
        }
      }catch(Exception e){}
    }
  }

  /**Refreshes the ListView in this view.*/
  private void refreshList()
  {
    String line;
    int temp;
    Exams.getItems().clear();
    SchModMan.orderExams(OrdTyp,OrdRev);
    for (int i = 0; i < SchModMan.getExams().length; i++)
    {
      line = "";
      line += SchModMan.getExams()[i].getClass().getName().substring(14, SchModMan.getExams()[i].getClass().getName().length() - 4);
      temp = line.length();
      for (int j = 0; j < 10 - temp; j++)
        line += " ";
      line += SchModMan.getExams()[i].getName();
      temp = line.length();
      for (int j = 0; j < 31 - temp; j++)
        line += " ";
      line += SchModMan.getExams()[i].getPlace().getName();
      temp = line.length();
      for (int j = 0; j < 43 - temp; j++)
        line += " ";
      line += SchModMan.getExams()[i].getParticipatingClass().getName();
      temp = line.length();
      for (int j = 0; j < 59 - temp; j++)
        line += " ";
      line += SchModMan.getExams()[i].getExaminer().getName();
      temp = line.length();
      for (int j = 0; j < 79 - temp; j++)
        line += " ";
      switch (SchModMan.getExams()[i].getClass().getName().substring(14, SchModMan.getExams()[i].getClass().getName().length() - 4))
      {
        case "Oral":
          line += ((OralExam) SchModMan.getExams()[i]).getExaminer2().getName();
          break;
        case "Project":
          line += ((ProjectExam) SchModMan.getExams()[i]).getExaminer2().getName();
          break;
      }
      temp = line.length();
      for (int j = 0; j < 100 - temp; j++)
        line += " ";
      line += SchModMan.getExams()[i].getStartDate().toDateOnly();
      temp = line.length();
      for (int j = 0; j < 115 - temp; j++)
        line += " ";
      line += SchModMan.getExams()[i].getStartDate().toTimeOnly();
      temp = line.length();
      for (int j = 0; j < 127 - temp; j++)
        line += " ";
      line += SchModMan.getExams()[i].getDuration();
      temp = line.length();
      for (int j = 0; j < 134 - temp; j++)
        line += " ";
      line += SchModMan.getExams()[i].getEnd().toString();
      temp = line.length();
      for (int j = 0; j < 153 - temp; j++)
        line += " ";
      switch (SchModMan.getExams()[i].getClass().getName().substring(14, SchModMan.getExams()[i].getClass().getName().length() - 4))
      {
        case "Oral":
          line += ((OralExam) SchModMan.getExams()[i]).getBreakDurationPerStudent();
          break;
        case "Project":
          line += ((ProjectExam) SchModMan.getExams()[i]).getBreakDurationPerGroup();
          break;
      }
      temp = line.length();
      for (int j = 0; j < 159 - temp; j++)
        line += " ";
      switch (SchModMan.getExams()[i].getClass().getName().substring(14, SchModMan.getExams()[i].getClass().getName().length() - 4))
      {
        case "Oral":
          line += ((OralExam) SchModMan.getExams()[i]).getDurationPerStudent();
          break;
        case "Project":
          line += ((ProjectExam) SchModMan.getExams()[i]).getDurationPerStudentPerGroup();
          break;
      }
      temp = line.length();
      for (int j = 0; j < 165 - temp; j++)
        line += " ";
      line += SchModMan.getExams()[i].getParticipatingClass().getNumberOfStudents();
      temp = line.length();
      for (int j = 0; j < 171 - temp; j++)
        line += " ";
      line += SchModMan.getExams()[i].getParticipatingClass().getNumberOfGroups();
      Exams.getItems().add(line);
      if (ExpDet)
      {
        line = "";
        line += " ├── Conflicts: " + SchModMan.getExams()[i].getConflicts();
        Exams.getItems().add(line);
        line ="";
        line +=" ├── Requirements: ";
        for(int j = 0; j < SchModMan.getExams()[i].getRequirements().length; j++)
            line +=SchModMan.getExams()[i].getRequirements()[j]+", ";
        if(SchModMan.getExams()[i].getRequirements().length >0)
            line =line.substring(0,line.length()-2);
        Exams.getItems().add(line);
        line ="";
        line +=" └── Equipments:   ";
        for(int j = 0; j < SchModMan.getExams()[i].getPlace().getEquipments().length; j++)
            line +=SchModMan.getExams()[i].getPlace().getEquipments()[j]+", ";
        if(SchModMan.getExams()[i].getPlace().getEquipments().length > 0)
            line =line.substring(0,line.length()-2);
        Exams.getItems().add(line);
      }
    }
  }

  private void selectList()
  {
    for (int i = 0; i < Exams.getItems().size(); i++)
    {
      if (Exams.getItems().get(i).substring(10, 31).trim().equals(NamInp.getText()))
      {
        Exams.getSelectionModel().select(i);
        if (i >= 1)
          Exams.scrollTo(i - 1);
        else
          Exams.scrollTo(0);
      }
    }
  }

  /**called by javaFX*/
  public ExamsController(){}
}