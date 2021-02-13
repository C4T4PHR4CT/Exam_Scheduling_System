package ScheduleModel;

import java.util.ArrayList;

/**This class describes a person (an Examiner) with the properties of his or her:</br>
 * &nbsp;&nbsp;&nbsp;&nbsp;	• Name (unique Name, can't be the same for multiple examiners)</br>
 * &nbsp;&nbsp;&nbsp;&nbsp;	• time intervals when he or she is unavailable*/
public class Examiner
{
  /**Examiner's Name (unique)*/
  private String Name;
  /**ArrayList of elements consisting 2 dates each, where one pair describes one interval of the Examiner's unavailabilities*/
  private ArrayList<Date[]> Unavailabilities;

  /**The constructor initializes all instance variables.*/
  public Examiner(String Name, Date[] Unavailabilities)
  {
    this.Unavailabilities = new ArrayList<Date[]>();
    this.Name = Name;
    for (int i = 0; i < Unavailabilities.length / 2; i++)
    {
      Date[] temp = new Date[2];
      temp[0] = Unavailabilities[((i + 1) * 2) - 2];
      temp[1] = Unavailabilities[((i + 1) * 2) - 1];
      if (temp[0].getHour() == 0 && temp[0].getMinute() == 0 && temp[1].getHour() == 0 && temp[1].getMinute() == 0)
        temp[1] = temp[1].mergeDate(new Date(23,59));
      if (temp[1].getYear() == 1 && temp[1].getMonth() == 1 && temp[1].getDay() == 1)
        temp[1] = temp[0].mergeDate(temp[1]);
      this.Unavailabilities.add(temp);
    }
  }

  /**This constructor only gives a user defined value to the Name<br>
   * (this is used for testing and comparing purposes, since the Name is the unique identifier of an examiner)*/
  public Examiner(String Name)
  {
    this.Name = Name;
  }

  /**This method returns true if the inputted Date in the argument is in one of the unavailable time period(s) of this Examiner.*/
  public boolean isUnavailable(Date date)
  {
    boolean temp = false;
    for (int i = 0; i < Unavailabilities.size(); i++)
    {   //Unavailability.Start < Date & Date < Unavailability.End | Date = Unavailability.Start | Date = Unavailability.End
      if (Unavailabilities.get(i)[0].isBefore(date) && date.isBefore(Unavailabilities.get(i)[1]) || Unavailabilities.get(i)[0].equals(date) || Unavailabilities.get(i)[1].equals(date))
      {
        temp = true;
        break;
      }
    }
    return temp;
  }

  /**This method returns true if the inputted interval in the argument overlaps with one of the unavailable time period(s) of this Examiner.*/
  public boolean isUnavailable(Date startDate, Date endDate)
  {
    boolean temp = false;
    for (int i = 0; i < Unavailabilities.size(); i++)
    {   //Unavailability.Start < startDate & startDate < Unavailability.End | startDate = Unavailability.Start | startDate = Unavailability.End
      if (Unavailabilities.get(i)[0].isBefore(startDate) && startDate.isBefore(Unavailabilities.get(i)[1]) || Unavailabilities.get(i)[0].equals(startDate) || Unavailabilities.get(i)[1].equals(startDate))
      {
        temp = true;
        break;
      } //Unavailability.Start < endDate & endDate < Unavailability.End | endDate = Unavailability.Start | endDate = Unavailability.End
      if (Unavailabilities.get(i)[0].isBefore(endDate) && endDate.isBefore(Unavailabilities.get(i)[1]) || Unavailabilities.get(i)[0].equals(endDate) || Unavailabilities.get(i)[1].equals(endDate))
      {
        temp = true;
        break;
      } //startDate < Unavailability.Start & Unavailability.Start < endDate | startDate = Unavailability.Start | endDate = Unavailability.Start
      if (startDate.isBefore(Unavailabilities.get(i)[0]) && Unavailabilities.get(i)[0].isBefore(endDate) || Unavailabilities.get(i)[0].equals(startDate) || Unavailabilities.get(i)[0].equals(endDate))
      {
        temp = true;
        break;
      } //startDate < Unavailability.End & Unavailability.End < endDate | startDate = Unavailability.End | endDate = Unavailability.End
      if (startDate.isBefore(Unavailabilities.get(i)[1]) && Unavailabilities.get(i)[1].isBefore(endDate) || Unavailabilities.get(i)[1].equals(startDate) || Unavailabilities.get(i)[1].equals(endDate))
      {
        temp = true;
        break;
      }
    }
    return temp;
  }

  /**This method overrides the default equals method by returning true if the inputted Examiner in the argument has the same unique Name (Examiner.Name) as this' (since the Name is the unique identifier of an Examiner).*/
  public boolean equals(Object obj)
  {
    if (!(obj instanceof Examiner))
      return false;
    Examiner examiner = (Examiner) obj;
    return (examiner.Name.equals(Name));
  }

  /**This getter returns the Name of this Examiner.*/
  public String getName()
  {
    return Name;
  }

  /**This getter returns of the Unavailabilities of this Examiner, in ordered pairs where the index 0 and 1 defines the beginning and the end, described on the second dimension of the array.*/
  public Date[][] getUnavailabilities()
  {
    Date[][] temp = new Date[Unavailabilities.size()][2];
    for (int i = 0; i < Unavailabilities.size(); i++)
    {
      temp[i][0] = Unavailabilities.get(i)[0];
      temp[i][1] = Unavailabilities.get(i)[1];
    }
    return temp;
  }

  /**This setter changes the Name of this Examiner.*/
  public void setName(String Name)
  {
    this.Name = Name;
  }
}