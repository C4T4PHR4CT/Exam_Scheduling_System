package ScheduleModel;

/**This class describes a person (a Student) with the properties of his or her:<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;	• Name<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;	• VIAID (unique ID, can't be the same for multiple students)<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;	• the name of the Class that he or she attends<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;	• the project group which he or she is being part of*/
public class Student
{
  /**Student's Name*/
  private String Name;
  /**Student's VIAID (unique)*/
  private int VIAID;
  /**Student's Class' name*/
  private String Participating;
  /**Student's Group's number*/
  private int Group;

  /**The main constructor initializes all instance variables.*/
  public Student(String Name, int VIAID, String Participating, int Group)
  {
    this.Name = Name;
    this.VIAID = VIAID;
    this.Participating = Participating;
    this.Group = Group;
  }

  /**This constructor only gives a user defined value to the VIAID<br>
   * (this is used for testing and comparing purposes, since the VIAID is the unique identifier of a student)*/
  public Student(int VIAID)
  {
    this.Name = "NoName";
    this.VIAID = VIAID;
    this.Participating = "NoClass";
    this.Group = -1;
  }

  /**This method overrides the default equals method by returning true if the inputted Student in the argument has the same unique VIAID (Student.VIAID) as this' (since the VIAID is the unique identifier of a Student).*/
  public boolean equals(Object obj)
  {
    if (!(obj instanceof Student))
      return false;
    Student student = (Student) obj;
    return (student.VIAID == VIAID);
  }

  /**This getter returns the Name of this Student.*/
  public String getName()
  {
    return Name;
  }

  /**This getter returns the VIAID of this Student.*/
  public int getVIAID()
  {
    return VIAID;
  }

  /**This getter returns the Name of the Class which is attended by this Student.*/
  public String getParticipating()
  {
    return Participating;
  }

  /**This getter returns the Group number of this Student.*/
  public int getGroup()
  {
    return Group;
  }

  /**This setter changes the Name of this Student.*/
  public void setName(String Name)
  {
    this.Name = Name;
  }

  /**This setter changes the Participating Class' name (Student.Participating) of this Student.*/
  public void setParticipating(String Participating)
  {
    this.Participating = Participating;
  }
}