package view;

import ScheduleModel.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**This class controls the IO for the Classrooms view.*/
public class ClassroomsController
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
    SizOrd.setText("Size");
    EquOrd.setText("Equipments");
    OrdTyp = "Name";
    if (NamOrd.getText().substring(NamOrd.getText().length() - 2).equals("\\/"))
    {
      OrdRev = true;
      SchModMan.orderRooms(OrdTyp, true);
      NamOrd.setText("Name /\\");
    }
    else
    {
      OrdRev = false;
      SchModMan.orderRooms(OrdTyp, false);
      NamOrd.setText("Name \\/");
    }
    refreshList();
  }

  @FXML private Button SizOrd;
  public void SizeOrder()
  {
    NamOrd.setText("Name");
    EquOrd.setText("Equipments");
    OrdTyp = "Size";
    if (SizOrd.getText().substring(SizOrd.getText().length() - 2).equals("\\/"))
    {
      OrdRev = true;
      SchModMan.orderRooms(OrdTyp, true);
      SizOrd.setText("Size /\\");
    }
    else
    {
      OrdRev = false;
      SchModMan.orderRooms(OrdTyp, false);
      SizOrd.setText("Size \\/");
    }
    refreshList();
  }

  @FXML private Button EquOrd;
  public void EquipmentOrder()
  {
    NamOrd.setText("Name");
    SizOrd.setText("Size");
    OrdTyp = "Equipments";
    if (EquOrd.getText().substring(EquOrd.getText().length() - 2).equals("\\/"))
    {
      OrdRev = true;
      SchModMan.orderRooms(OrdTyp, true);
      EquOrd.setText("Equipments /\\");
    }
    else
    {
      OrdRev = false;
      SchModMan.orderRooms(OrdTyp, false);
      EquOrd.setText("Equipments \\/");
    }
    refreshList();
  }

  @FXML private TextField NamInp;
  public void Name()
  {
    Rooms.getSelectionModel().select(null);
    if (!NamInp.getText().equals(""))
    {
      CursorPos = NamInp.getCaretPosition();
      if (NamInp.getText().length() > 8)
        NamInp.setText(NamInp.getText().substring(0, 8));
      selectList();
      NamInp.positionCaret(CursorPos);
    }
  }

  @FXML private TextField SizInp;
  public void Size()
  {
    if (!SizInp.getText().equals(""))
    {
      CursorPos = SizInp.getCaretPosition();
      SizInp.setText(SizInp.getText().replaceAll("[^\\d]", ""));
      if (SizInp.getText().length() > 5)
         SizInp.setText(SizInp.getText().substring(0, 5));
      SizInp.positionCaret(CursorPos);
    }
  }

  public void AddRoom()
  {
    if (!NamInp.getText().matches("^ *$") && !SizInp.getText().equals(""))
    {
      SchModMan.removeRoom(NamInp.getText().trim());
      SchModMan.newRoom(NamInp.getText().trim(), Integer.parseInt(SizInp.getText()),
          EquInp.getText().split(", "));
      SchModMan.orderRooms(OrdTyp, OrdRev);
      refreshList();
      selectList();
    }
  }

  public void RemoveRoom()
  {
    if (!NamInp.getText().equals(""))
    {
      SchModMan.removeRoom(NamInp.getText());
      SchModMan.orderRooms(OrdTyp, OrdRev);
      refreshList();
    }
  }

  @FXML private TextField EquInp;
  @FXML private ListView<String> Rooms;
  void init(ViewHandler viewHandler, ScheduleModelManager SchModMan)
  {
    this.viewHandler = viewHandler;
    this.SchModMan = SchModMan;
    this.OrdTyp = "Name";
    this.OrdRev = false;
    SchModMan.orderRooms(OrdTyp,false);
    Rooms.setStyle("-fx-font-family: Consolas;-fx-font-size:16");
    refreshList();
    Rooms.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
    {
      if (newValue != null)
      {
        Room currentRoom = SchModMan.getRoom(newValue.substring(0,10).replaceAll(" {2,}",""));
        NamInp.setText(currentRoom.getName());
        SizInp.setText(String.valueOf(currentRoom.getSize()));
        String temp1 = "";
        for (int j = 0; j < currentRoom.getEquipments().length; j++)
          temp1 += currentRoom.getEquipments()[j] + ", ";
        temp1 = temp1.substring(0, temp1.length() - 2);
        EquInp.setText(temp1);
      }
    });
  }

  /**Selects and scrolls to the current element inputted in the unique identifier' input TextField (if such element exists).*/
  private void selectList()
  {
    for (int i = 0; i < Rooms.getItems().size(); i++)
    {
      if (Rooms.getItems().get(i).substring(0, 10).trim().equals(NamInp.getText()))
      {
        Rooms.getSelectionModel().select(i);
        if (i >= 1)
          Rooms.scrollTo(i - 1);
        else
          Rooms.scrollTo(0);
      }
    }
  }

  /**Refreshes the ListView in this view.*/
  private void refreshList()
  {
    String line;
    int temp;
    Rooms.getItems().clear();
    for (int i = 0; i < SchModMan.getRooms().length; i++)
    {
      line = "";
      line += SchModMan.getRooms()[i].getName();
      temp = line.length();
      for (int j = 0; j < 10 - temp; j++)
        line += " ";
      line += SchModMan.getRooms()[i].getSize();
      temp = line.length();
      for (int j = 0; j < 17 - temp; j++)
        line += " ";
      for (int j = 0; j < SchModMan.getRooms()[i].getEquipments().length; j++)
        line += SchModMan.getRooms()[i].getEquipments()[j] + ", ";
      line = line.substring(0, line.length() - 2);
      Rooms.getItems().add(line);
    }
  }

  /**called by javaFX*/
  public ClassroomsController(){}
}