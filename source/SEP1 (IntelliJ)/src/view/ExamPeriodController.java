package view;

import ScheduleModel.*;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**This class controls the IO for the ExamPeriod view.*/
public class ExamPeriodController
{
  private ViewHandler viewHandler;
  private ScheduleModelManager SchModMan;

  public void Back()
  {
    viewHandler.switchStage("MainMenu");
  }

  private int CursorPos;
  
  @FXML private TextField StrTimH;
  @FXML private TextField StrTimM;
  public void StartTime()
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
    if (!StrTimH.getText().equals("") && !StrTimM.getText().equals("") && !new Date(Integer.parseInt(StrTimH.getText()), Integer.parseInt(StrTimM.getText())).equals(SchModMan.getExamPeriod().getStartTime()))
      SchModMan.getExamPeriod().setStartTime(new Date(Integer.parseInt(StrTimH.getText()), Integer.parseInt(StrTimM.getText())));
  }

  @FXML private TextField EndTimH;
  @FXML private TextField EndTimM;
  public void EndTime()
  {
    if (!EndTimH.getText().equals(""))
    {
      CursorPos = EndTimH.getCaretPosition();
      EndTimH.setText(EndTimH.getText().replaceAll("[^\\d]", ""));
      if (EndTimH.getText().length() > 2)
        EndTimH.setText(EndTimH.getText().substring(0, 2));
      if (Integer.parseInt(EndTimH.getText()) > 23)
        EndTimH.setText(EndTimH.getText().substring(0, 1));
      EndTimH.positionCaret(CursorPos);
    }
    if (!EndTimM.getText().equals(""))
    {
      CursorPos = EndTimM.getCaretPosition();
      EndTimM.setText(EndTimM.getText().replaceAll("[^\\d]", ""));
      if (EndTimM.getText().length() > 2)
        EndTimM.setText(EndTimM.getText().substring(0, 2));
      if (Integer.parseInt(EndTimM.getText()) > 59)
        EndTimM.setText(EndTimM.getText().substring(0, 1));
      EndTimM.positionCaret(CursorPos);
    }
    if (!EndTimH.getText().equals("") && !EndTimM.getText().equals("") && !new Date(Integer.parseInt(EndTimH.getText()), Integer.parseInt(EndTimM.getText())).equals(SchModMan.getExamPeriod().getEndTime()))
      SchModMan.getExamPeriod().setEndTime(new Date(Integer.parseInt(EndTimH.getText()), Integer.parseInt(EndTimM.getText())));
  }

  @FXML private TextField BrkStrTimH;
  @FXML private TextField BrkStrTimM;
  public void BreakStartTime()
  {
    if (!BrkStrTimH.getText().equals(""))
    {
      CursorPos = BrkStrTimH.getCaretPosition();
      BrkStrTimH.setText(BrkStrTimH.getText().replaceAll("[^\\d]", ""));
      if (BrkStrTimH.getText().length() > 2)
        BrkStrTimH.setText(BrkStrTimH.getText().substring(0, 2));
      if (Integer.parseInt(BrkStrTimH.getText()) > 23)
        BrkStrTimH.setText(BrkStrTimH.getText().substring(0, 1));
      BrkStrTimH.positionCaret(CursorPos);
    }
    if (!BrkStrTimM.getText().equals(""))
    {
      CursorPos = BrkStrTimM.getCaretPosition();
      BrkStrTimM.setText(BrkStrTimM.getText().replaceAll("[^\\d]", ""));
      if (BrkStrTimM.getText().length() > 2)
        BrkStrTimM.setText(BrkStrTimM.getText().substring(0, 2));
      if (Integer.parseInt(BrkStrTimM.getText()) > 59)
        BrkStrTimM.setText(BrkStrTimM.getText().substring(0, 1));
      BrkStrTimM.positionCaret(CursorPos);
    }
    if (!BrkStrTimH.getText().equals("") && !BrkStrTimM.getText().equals("") && !new Date(Integer.parseInt(BrkStrTimH.getText()), Integer.parseInt(BrkStrTimM.getText())).equals(SchModMan.getExamPeriod().getBreakStartTime()))
      SchModMan.getExamPeriod().setBreakStartTime(new Date(Integer.parseInt(BrkStrTimH.getText()), Integer.parseInt(BrkStrTimM.getText())));
  }

  @FXML private TextField BrkEndTimH;
  @FXML private TextField BrkEndTimM;
  public void BreakEndTime()
  {
    if (!BrkEndTimH.getText().equals(""))
    {
      CursorPos = BrkEndTimH.getCaretPosition();
      BrkEndTimH.setText(BrkEndTimH.getText().replaceAll("[^\\d]", ""));
      if (BrkEndTimH.getText().length() > 2)
        BrkEndTimH.setText(BrkEndTimH.getText().substring(0, 2));
      if (Integer.parseInt(BrkEndTimH.getText()) > 23)
        BrkEndTimH.setText(BrkEndTimH.getText().substring(0, 1));
      BrkEndTimH.positionCaret(CursorPos);
    }
    if (!BrkEndTimM.getText().equals(""))
    {
      CursorPos = BrkEndTimM.getCaretPosition();
      BrkEndTimM.setText(BrkEndTimM.getText().replaceAll("[^\\d]", ""));
      if (BrkEndTimM.getText().length() > 2)
        BrkEndTimM.setText(BrkEndTimM.getText().substring(0, 2));
      if (Integer.parseInt(BrkEndTimM.getText()) > 59)
        BrkEndTimM.setText(BrkEndTimM.getText().substring(0, 1));
      BrkEndTimM.positionCaret(CursorPos);
    }
    if (!BrkEndTimH.getText().equals("") && !BrkEndTimM.getText().equals("") && !new Date(Integer.parseInt(BrkEndTimH.getText()), Integer.parseInt(BrkEndTimM.getText())).equals(SchModMan.getExamPeriod().getBreakEndTime()))
      SchModMan.getExamPeriod().setBreakEndTime(new Date(Integer.parseInt(BrkEndTimH.getText()), Integer.parseInt(BrkEndTimM.getText())));
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
    if (!BrkDur.getText().equals("") && Integer.parseInt(BrkDur.getText()) != SchModMan.getExamPeriod().getBreakDuration())
      SchModMan.getExamPeriod().setBreakDuration(Integer.parseInt(BrkDur.getText()));
  }

  @FXML private TextField DefDurPerStd;
  public void DefaultDurationPerStudent()
  {
    if (!DefDurPerStd.getText().equals(""))
    {
      CursorPos = DefDurPerStd.getCaretPosition();
      DefDurPerStd.setText(DefDurPerStd.getText().replaceAll("[^\\d]", ""));
      if (DefDurPerStd.getText().length() > 3)
        DefDurPerStd.setText(DefDurPerStd.getText().substring(0, 3));
      DefDurPerStd.positionCaret(CursorPos);
    }
    if (!DefDurPerStd.getText().equals("") && Integer.parseInt(DefDurPerStd.getText()) != SchModMan.getExamPeriod().getDefaultDurationPerStudent())
      SchModMan.getExamPeriod().setDefaultDurationPerStudent(Integer.parseInt(DefDurPerStd.getText()));
  }

  @FXML private TextField DefDurPerStdPerGrp;
  public void DefaultDurationPerStudentPerGroup()
  {
    if (!DefDurPerStdPerGrp.getText().equals(""))
    {
      CursorPos = DefDurPerStdPerGrp.getCaretPosition();
      DefDurPerStdPerGrp.setText(DefDurPerStdPerGrp.getText().replaceAll("[^\\d]", ""));
      if (DefDurPerStdPerGrp.getText().length() > 3)
        DefDurPerStdPerGrp.setText(DefDurPerStdPerGrp.getText().substring(0, 3));
      DefDurPerStdPerGrp.positionCaret(CursorPos);
    }
    if (!DefDurPerStdPerGrp.getText().equals("") && Integer.parseInt(DefDurPerStdPerGrp.getText()) != SchModMan.getExamPeriod().getDefaultDurationPerStudentPerGroup())
      SchModMan.getExamPeriod().setDefaultDurationPerStudentPerGroup(Integer.parseInt(DefDurPerStdPerGrp.getText()));
  }

  @FXML private TextField DefBrkDurPerStd;
  public void DefaultBreakDurationPerStudent()
  {
    if (!DefBrkDurPerStd.getText().equals(""))
    {
      CursorPos = DefBrkDurPerStd.getCaretPosition();
      DefBrkDurPerStd.setText(DefBrkDurPerStd.getText().replaceAll("[^\\d]", ""));
      if (DefBrkDurPerStd.getText().length() > 3)
        DefBrkDurPerStd.setText(DefBrkDurPerStd.getText().substring(0, 3));
      DefBrkDurPerStd.positionCaret(CursorPos);
    }
    if (!DefBrkDurPerStd.getText().equals("") && Integer.parseInt(DefBrkDurPerStd.getText()) != SchModMan.getExamPeriod().getDefaultBreakDurationPerStudent())
      SchModMan.getExamPeriod().setDefaultBreakDurationPerStudent(Integer.parseInt(DefBrkDurPerStd.getText()));
  }
  
  @FXML private TextField DefBrkDurPerGrp;
  public void DefaultBreakDurationPerGroup()
  {
    if (!DefBrkDurPerGrp.getText().equals(""))
    {
      CursorPos = DefBrkDurPerGrp.getCaretPosition();
      DefBrkDurPerGrp.setText(DefBrkDurPerGrp.getText().replaceAll("[^\\d]", ""));
      if (DefBrkDurPerGrp.getText().length() > 3)
        DefBrkDurPerGrp.setText(DefBrkDurPerGrp.getText().substring(0, 3));
      DefBrkDurPerGrp.positionCaret(CursorPos);
    }
    if (!DefBrkDurPerGrp.getText().equals("") && Integer.parseInt(DefBrkDurPerGrp.getText()) != SchModMan.getExamPeriod().getDefaultBreakDurationPerGroup())
      SchModMan.getExamPeriod().setDefaultBreakDurationPerGroup(Integer.parseInt(DefBrkDurPerGrp.getText()));
  }

  @FXML private ListView<String> ValDates;
  @FXML private DatePicker NewDate;
  public void AddNewDate()
  {
    if (NewDate.getValue() != null)
    {
      SchModMan.getExamPeriod().addValidDate(new Date(NewDate.getValue()));
      ValDates.getItems().clear();
      for (int i = 0; i < SchModMan.getExamPeriod().getValidDates().length; i++)
      {
        ValDates.getItems().add(SchModMan.getExamPeriod().getValidDates()[i].toDateOnly());
      }
      for (int i = 0; i < ValDates.getItems().size(); i++)
      {
        if (ValDates.getItems().get(i).equals(new Date(NewDate.getValue()).toDateOnly()))
        {
          ValDates.getSelectionModel().select(i);
          if (i >= 1)
            ValDates.scrollTo(i - 1);
          else
            ValDates.scrollTo(0);
        }
      }
    }
    else
    {
      NewDate.show();
    }
  }

  public void RemoveDate()
  {
    if (NewDate.getValue() != null)
      {
      SchModMan.getExamPeriod().removeValidDate(new Date(NewDate.getValue()));
      ValDates.getItems().clear();
      for (int i = 0; i < SchModMan.getExamPeriod().getValidDates().length; i++)
      {
        ValDates.getItems().add(SchModMan.getExamPeriod().getValidDates()[i].toDateOnly());
      }
    }
    else
    {
      NewDate.show();
    }
  }

  void init(ViewHandler viewHandler, ScheduleModelManager SchModMan)
  {
    this.viewHandler = viewHandler;
    this.SchModMan = SchModMan;
    ValDates.setStyle("-fx-font-family: Consolas;-fx-font-size:15");
    StrTimH.setText(String.valueOf(SchModMan.getExamPeriod().getStartTime().getHour()));
    StrTimM.setText(String.valueOf(SchModMan.getExamPeriod().getStartTime().getMinute()));
    EndTimH.setText(String.valueOf(SchModMan.getExamPeriod().getEndTime().getHour()));
    EndTimM.setText(String.valueOf(SchModMan.getExamPeriod().getEndTime().getMinute()));
    BrkStrTimH.setText(String.valueOf(SchModMan.getExamPeriod().getBreakStartTime().getHour()));
    BrkStrTimM.setText(String.valueOf(SchModMan.getExamPeriod().getBreakStartTime().getMinute()));
    BrkEndTimH.setText(String.valueOf(SchModMan.getExamPeriod().getBreakEndTime().getHour()));
    BrkEndTimM.setText(String.valueOf(SchModMan.getExamPeriod().getBreakEndTime().getMinute()));
    BrkDur.setText(String.valueOf(SchModMan.getExamPeriod().getBreakDuration()));
    DefDurPerStd.setText(String.valueOf(SchModMan.getExamPeriod().getDefaultDurationPerStudent()));
    DefDurPerStdPerGrp.setText(String.valueOf(SchModMan.getExamPeriod().getDefaultDurationPerStudentPerGroup()));
    DefBrkDurPerStd.setText(String.valueOf(SchModMan.getExamPeriod().getDefaultBreakDurationPerStudent()));
    DefBrkDurPerGrp.setText(String.valueOf(SchModMan.getExamPeriod().getDefaultBreakDurationPerGroup()));
    for (int i = 0; i < SchModMan.getExamPeriod().getValidDates().length; i++)
    {
      ValDates.getItems().add(SchModMan.getExamPeriod().getValidDates()[i].toDateOnly());
    }
    ValDates.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
    {
      if (newValue != null)
        NewDate.setValue(new Date(newValue).toLocalDate());
    });
  }

  /**called by javaFX*/
  public ExamPeriodController(){}
}