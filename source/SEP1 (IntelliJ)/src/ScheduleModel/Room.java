package ScheduleModel;

import java.util.ArrayList;

/**This class describes a classroom with the properties of its:</br>
 * &nbsp;&nbsp;&nbsp;&nbsp;	• Name (unique Name, can't be the same for multiple rooms)</br>
 * &nbsp;&nbsp;&nbsp;&nbsp;	• Size</br>
 * &nbsp;&nbsp;&nbsp;&nbsp;	• Equipments*/
public class Room
{
  /**Room's Name (unique)*/
  private String Name;
  /**Rooms's capacity*/
  private int Size;
  /**ArrayList of Equipments present in this classroom*/
  private ArrayList<String> Equipments;

  /**The constructor checks for duplicates in the inputted Equipments in the argument and eliminates the duplicates, and then initializes all instance variables.*/
  public Room(String Name, int Size, String[] Equipments)
  {
    this.Equipments = new ArrayList<String>();
    this.Name = Name;
    this.Size = Size;
    for (int i = 0; i < Equipments.length; i++)
    {
      addEquipment(Equipments[i]);
    }
  }

  /**This constructor only gives a user defined value to the Name<br>
   * (this is used for testing and comparing purposes, since the Name is the unique identifier of a room)*/
  public Room(String Name)
  {
    this.Name = Name;
  }

  /**This method returns true if the Room is equipped with the inputted argument.*/
  public boolean hasEquipment(String equipment)
  {
    return (Equipments.contains(equipment));
  }

  /**This method overrides the default equals method by returning true if the inputted Room in the argument has the same unique Name (Room.Name) as this' (since the Name is the unique identifier of a Room).*/
  public boolean equals(Object obj)
  {
    if (!(obj instanceof Room))
      return false;
    Room room = (Room) obj;
    return (room.Name.equals(Name));
  }

  /**This getter returns the Name of this Room.*/
  public String getName()
  {
    return Name;
  }

  /**This getter returns the Size of this Room.*/
  public int getSize()
  {
    return Size;
  }

  /**This method adds an equipment to the Equipments of this exam, in an abc ordered fashion (if not already present).*/
  public void addEquipment(String equipment)
  {
    if (!this.Equipments.contains(equipment))
    {
      if (Equipments.size() == 0)
      {
        this.Equipments.add(equipment);
      }
      else
      {
        if (Equipments.get(Equipments.size() - 1).compareTo(equipment) < 0)
          this.Equipments.add(equipment);
        for (int i = 0; i < Equipments.size(); i++)
        {
          if (equipment.compareTo(Equipments.get(i)) < 0)
          {
            this.Equipments.add(i, equipment);
            break;
          }
        }
      }
    }
  }

  /**This getter returns all Equipments of this Room.*/
  public String[] getEquipments()
  {
    String[] temp = new String[Equipments.size()];
    temp = Equipments.toArray(temp);
    return temp;
  }

  /**This setter changes the Name of this Room.*/
  public void setName(String Name)
  {
    this.Name = Name;
  }
}