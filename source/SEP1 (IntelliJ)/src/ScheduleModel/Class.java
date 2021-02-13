package ScheduleModel;

import java.util.ArrayList;

/**This class describes a Class (a group of students) with the properties of its:</br>
 * &nbsp;&nbsp;&nbsp;&nbsp;	• Name (unique Name, can't be the same for multiple classes)</br>
 * &nbsp;&nbsp;&nbsp;&nbsp;	• attending students*/
public class Class
{
  /**Class' Name (unique)*/
  private String Name;
  /**ArrayList of pointers of students who attend this CLass*/
  private ArrayList<Student> Students;

  /**The constructor initializes the Name instance variable, as the students can be added or removed with other methods later on.*/
  public Class(String Name)
  {
    Students = new ArrayList<Student>();
    this.Name = Name;
  }

  /**This method adds a Student to the Class, unless the Student is already a part of the Class.*/
  public void addStudent(Student student)
  {
    if(!Students.contains(student))
    {
      Students.add(student);
    }
  }

  /**This method removes a Student from the Class if the student is the part of the class.*/
  public void removeStudent(Student student)
  {
    for (int i = 0; i < Students.size(); i++)
    {
      if(Students.get(i).getVIAID() == student.getVIAID())
      {
        Students.remove(i);
      }
    }
  }

  /**This method returns the number of students attending this Class.*/
  public int getNumberOfStudents()
  {
    return Students.size();
  }

  /**This method returns the number of groups in this Class.*/
  public int getNumberOfGroups()
  {
    ArrayList<Integer> groups = new ArrayList<Integer>();
    for(int i = 0; i < getNumberOfStudents(); i++)
    {
      if(!(groups.contains(Students.get(i).getGroup())))
        groups.add(Students.get(i).getGroup());
    }
    return groups.size();
  }

  /**This method overrides the default equals method by returning true if the inputted Class in the argument has the same unique Name (Class.Name) as this' (since the Name is the unique identifier of a Class).*/
  public boolean equals(Object obj)
  {
    if (!(obj instanceof Class))
      return false;
    Class classOther = (Class) obj;
    return (classOther.Name.equals(Name));
  }

  /**This getter returns the Name of this CLass.*/
  public String getName()
  {
    return Name;
  }

  /**This getter returns the students of this Class.*/
  public Student[] getStudents()
  {
    Student[] temp = new Student[Students.size()];
    temp = Students.toArray(temp);
    return temp;
  }

  public Student[] getGroup(int groupNumber)
  {
    ArrayList<Student> grpStudents = new ArrayList<>();
    for(int i = 0; i < getNumberOfStudents(); i++)
    {
      if(Students.get(i).getGroup() == groupNumber)
        grpStudents.add(Students.get(i));
    }
    Student[] temp = new Student[grpStudents.size()];
    temp = grpStudents.toArray(temp);
    return temp;
  }

  /**This setter changes the Name of this Class.*/
  public void setName(String Name)
  {
    this.Name = Name;
    for(int i = 0; i < Students.size(); i++)
    {
      Students.get(i).setParticipating(Name);
    }
  }
}