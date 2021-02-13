package view;

import ScheduleModel.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**This class controls the IO for the Examiners view.*/
public class ExaminersController
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
    UnaOrd.setText("Unavailabilities");
    OrdTyp = "Name";
    if (NamOrd.getText().substring(NamOrd.getText().length() - 2).equals("\\/"))
    {
      OrdRev = true;
      SchModMan.orderExaminers(OrdTyp, true);
      NamOrd.setText("Name /\\");
    }
    else
    {
      OrdRev = false;
      SchModMan.orderExaminers(OrdTyp, false);
      NamOrd.setText("Name \\/");
    }
    refreshList();
  }

  @FXML private Button UnaOrd;
  public void UnavailabilitiesOrder()
  {
    NamOrd.setText("Name");
    OrdTyp = "Unavailabilities";
    if (UnaOrd.getText().substring(UnaOrd.getText().length() - 2).equals("\\/"))
    {
      OrdRev = true;
      SchModMan.orderExaminers(OrdTyp, true);
      UnaOrd.setText("Unavailabilities /\\");
    }
    else
    {
      OrdRev = false;
      SchModMan.orderExaminers(OrdTyp, false);
      UnaOrd.setText("Unavailabilities \\/");
    }
    refreshList();
  }

  @FXML private TextField NamInp;
  public void Name()
  {
    Examiners.getSelectionModel().select(null);
    if (!NamInp.getText().equals(""))
    {
      CursorPos = NamInp.getCaretPosition();
      if (NamInp.getText().length() > 19)
        NamInp.setText(NamInp.getText().substring(0, 19));
      selectList();
      NamInp.positionCaret(CursorPos);
    }
  }

  public void AddExaminer()
  {
    if (!NamInp.getText().matches("^ *$"))
    {
      SchModMan.removeExaminer(NamInp.getText().trim());
      Date[] unaTmp;
      try
      {
        unaTmp = new Date[UnaInp.getText().split("(-)|(, )").length];
        for (int i = 0; i < unaTmp.length; i++)
        {
          unaTmp[i] = new Date(UnaInp.getText().split("(-)|(, )")[i]);
        }
      }
      catch (Exception e)
      {
        unaTmp = new Date[0];
      }
      SchModMan.newExaminer(NamInp.getText().trim(), unaTmp);
      SchModMan.orderExaminers(OrdTyp, OrdRev);
      refreshList();
      selectList();
    }
  }

  public void RemoveExaminer()
  {
    SchModMan.removeExaminer(NamInp.getText());
    SchModMan.orderExaminers(OrdTyp, OrdRev);
    refreshList();
  }

  @FXML private TextField UnaInp;
  @FXML private ListView<String> Examiners;
  void init(ViewHandler viewHandler, ScheduleModelManager SchModMan)
  {
    this.viewHandler = viewHandler;
    this.SchModMan = SchModMan;
    this.OrdTyp = "Name";
    this.OrdRev = false;
    SchModMan.orderExaminers(OrdTyp,false);
    Examiners.setStyle("-fx-font-family: Consolas;-fx-font-size:15");
    refreshList();
    Examiners.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
    {
      if (newValue != null)
        {
        Examiner currentExaminer;
        try
        {
          currentExaminer = SchModMan.getExaminer(newValue.substring(0, 21).trim());
        }
        catch (Exception e)
        {
          currentExaminer = SchModMan.getExaminer(newValue.trim());
        }
        NamInp.setText(currentExaminer.getName());
        String temp = "";
        for (int j = 0; j < currentExaminer.getUnavailabilities().length; j++)
          temp += currentExaminer.getUnavailabilities()[j][0] + "-" + currentExaminer.getUnavailabilities()[j][1] + ", ";
        if (currentExaminer.getUnavailabilities().length > 0)
          temp = temp.substring(0, temp.length() - 2);
        UnaInp.setText(temp);
      }
    });
  }

  /**Selects and scrolls to the current element inputted in the unique identifier' input TextField (if such element exists).*/
  private void selectList()
  {
    for (int i = 0; i < Examiners.getItems().size(); i++)
    {
      try
      {
        if (Examiners.getItems().get(i).substring(0, 21).trim().equals(NamInp.getText()))
        {
          Examiners.getSelectionModel().select(i);
          if (i >= 1)
            Examiners.scrollTo(i - 1);
          else
            Examiners.scrollTo(0);
        }
      }
      catch (Exception e)
      {
        if (Examiners.getItems().get(i).trim().equals(NamInp.getText()))
        {
          Examiners.getSelectionModel().select(i);
          if (i >= 1)
            Examiners.scrollTo(i - 1);
          else
            Examiners.scrollTo(0);
        }
      }
    }
  }

  /**Refreshes the ListView in this view.*/
  private void refreshList()
  {
    String line;
    int temp;
    Examiners.getItems().clear();
    for (int i = 0; i < SchModMan.getExaminers().length; i++)
    {
      line = "";
      line += SchModMan.getExaminers()[i].getName();
      temp = line.length();
      for (int j = 0; j < 21 - temp; j++)
        line += " ";
      for (int j = 0; j < SchModMan.getExaminers()[i].getUnavailabilities().length; j++)
        line += SchModMan.getExaminers()[i].getUnavailabilities()[j][0] + "-" + SchModMan.getExaminers()[i].getUnavailabilities()[j][1] + ", ";
      line = line.substring(0, line.length() - 2);
      Examiners.getItems().add(line);
    }
  }

  /**called by javaFX*/
  public ExaminersController(){}
}