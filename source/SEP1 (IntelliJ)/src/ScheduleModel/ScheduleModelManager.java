package ScheduleModel;

import xmlHandler.node;
import xmlHandler.xmlReader;
import xmlHandler.xmlWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**This class manages other a classes' instances which are needed to to create the exam schedule.*/
public class ScheduleModelManager implements ScheduleModel
{
  private static final String ANSI_RESET = "\u001B[0m";    //default
  private static final String ANSI_RED = "\u001B[31m";     //Error
  private static final String ANSI_GREEN = "\u001B[32m";   //Added
  private static final String ANSI_YELLOW = "\u001B[33m";  //Modified or Removed
  private static final String ANSI_CYAN = "\u001B[36m";    //File operations

  /**ArrayList of all existing Classes*/
  private ArrayList<Class> Classes;
  /**ArrayList of all existing Students*/
  private ArrayList<Student> Students;
  /**ArrayList of all existing Examiners*/
  private ArrayList<Examiner> Examiners;
  /**ArrayList of all existing Classrooms*/
  private ArrayList<Room> Rooms;
  /**The ExamPeriod which stores the attributes that define this examination period*/
  private ExamPeriod ValidExamPeriod;
  /**ArrayList of all existing Exams*/
  private ArrayList<Exam> Exams;

  /**The constructor initializes instance variables, mostly empty ArrayLists, which going to store the required information for the schedule.*/
  public ScheduleModelManager()
  {
    Classes = new ArrayList<Class>();
    Students = new ArrayList<Student>();
    Examiners = new ArrayList<Examiner>();
    Rooms = new ArrayList<Room>();
    Exams = new ArrayList<Exam>();
    ValidExamPeriod = new ExamPeriod(new Date[0],new Date("00:00"),new Date("00:00"),new Date("1/1/1 00:00"),new Date("1/1/1 00:00"),0,0,0,0,0);
  }

  /**This method:</br>
   * &nbsp;&nbsp;&nbsp;&nbsp;	• constructs a new Class class and stores it in the Classes ArrayList, unless it already contains an other class with the same name</br>
   * &nbsp;&nbsp;&nbsp;&nbsp; • logs the method's successfullness to the console*/
  public void newClass(String Name)
  {
    Class new_class = new Class(Name);
    if(Classes.contains(new_class))
    {
      System.out.println("ScheduleModelManager:    " + ANSI_RED + "Error registering: class " + Name + " already exists" + ANSI_RESET);
    }
    else
    {
      Classes.add(new_class);
      System.out.println("ScheduleModelManager:    " + ANSI_GREEN + "New class " + Name + " successfully registered" + ANSI_RESET);
    }
  }

  /**This method:</br>
   * 	&nbsp;&nbsp;&nbsp;&nbsp; • removes the Class from the Classes ArrayList with the name given in the argument, unless such Class does not exists</br>
   * 	&nbsp;&nbsp;&nbsp;&nbsp; • renames the Class to "!Class_Removed"</br>
   * 	&nbsp;&nbsp;&nbsp;&nbsp; • if the second argument states, it removes all students attending the Class from the Students ArrayList</br>
   * 	&nbsp;&nbsp;&nbsp;&nbsp; • logs the method's successfullness to the console*/
  public void removeClass(String Name, boolean RemoveStudents)
  {
    if (Classes.contains(new Class(Name)))
    {
      for (int i = 0; i < Classes.size(); i++)
      {
        if (Classes.get(i).getName().equals(Name))
        {
          Classes.get(i).setName("!Class_Removed");
          Classes.remove(i);
          if (RemoveStudents)
          {
            for (int j = 0; j < Students.size(); j++)
            {
              if (Students.get(j).getParticipating().equals(Name))
              {
                Students.remove(j);
              }
            }
            System.out.println("ScheduleModelManager:    " + ANSI_YELLOW + "Class " + Name + " successfully removed, including all students" + ANSI_RESET);
          }
          else
          {
            System.out.println("ScheduleModelManager:    " + ANSI_YELLOW + "Class " + Name + " successfully removed" + ANSI_RESET);
          }
        }
      }
    }
    else
    {
      System.out.println("ScheduleModelManager:    " + ANSI_RED + "Error removing: class " + Name + " not found" + ANSI_RESET);
    }
  }

  /**This method:</br>
   * 	&nbsp;&nbsp;&nbsp;&nbsp; • renames the Class from the Classes ArrayList with the Name given in the first argument to the Name given in the second argument, to unless such Class does not exists</br>
   * 	&nbsp;&nbsp;&nbsp;&nbsp; • renames all Student.Participating attributes of the Students attending this Class</br>
   * 	&nbsp;&nbsp;&nbsp;&nbsp; • logs the method's successfullness to the console*/
  public void reNameClass(String Name, String newName)
  {
    if (Classes.contains(new Class(Name)))
    {
      for (int i = 0; i < Classes.size(); i++)
      {
        if (Classes.get(i).getName().equals(Name))
        {
          Classes.get(i).setName(newName);
          System.out.println("ScheduleModelManager:    " + ANSI_YELLOW + "Class " + Name + " successfully renamed to " + newName + ANSI_RESET);
        }
      }
    }
    else
    {
      System.out.println("ScheduleModelManager:    " + ANSI_RED + "Error renaming: class " + Name + " not found" + ANSI_RESET);
    }
  }

  /**This getter:</br>
   * 	&nbsp;&nbsp;&nbsp;&nbsp; • returns the Class with the same Name inputted in the argument (if it exists)</br>
   * 	&nbsp;&nbsp;&nbsp;&nbsp; • logs the possible errors of the method to the console*/
  public Class getClass(String Name)
  {
    if(Classes.contains(new Class(Name)))
    {
      for (int i = 0; i < Classes.size(); i++)
      {
        if (Classes.get(i).getName().equals(Name))
        {
          return Classes.get(i);
        }
      }
    }
    else if(Name.equals("!Unassigned"))
      return new Class("!Unassigned");
    System.out.println("ScheduleModelManager:    " + ANSI_RED + "Error getting: class " + Name + " not found" + ANSI_RESET);
    return null;
  }

  /**This getter returns all of the Classes.*/
  public Class[] getClasses()
  {
    Class[] temp = new Class[Classes.size()];
    temp = Classes.toArray(temp);
    return temp;
  }
  /**This method orders the Classes by a certain quality of them given by the first argument and the order also can be reversed with the use of the second argument.*/
  public void orderClasses(String by, boolean reverse)
  {
    boolean swapped = true;
    switch (by)
    {
      case "Name":
        while (swapped)
        {
          swapped = false;
          for(int i = 0; i < Classes.size() - 1; i++)
          {
            if ((Classes.get(i).getName().compareTo(Classes.get(i + 1).getName()) > 0 && !reverse) || (Classes.get(i).getName().compareTo(Classes.get(i + 1).getName()) < 0 && reverse))
            {
              Classes.add(i, Classes.get(i + 1));
              Classes.remove(i + 2);
              swapped = true;
            }
          }
        }
        break;
      case "Size":
        while (swapped)
        {
          swapped = false;
          for(int i = 0; i < Classes.size() - 1; i++)
          {
            if ((Classes.get(i).getNumberOfStudents() > Classes.get(i + 1).getNumberOfStudents() && !reverse) || (Classes.get(i).getNumberOfStudents() < Classes.get(i + 1).getNumberOfStudents() && reverse))
            {
              Classes.add(i, Classes.get(i + 1));
              Classes.remove(i + 2);
              swapped = true;
            }
          }
        }
        break;
      case "Group":
        while (swapped)
        {
          swapped = false;
          for(int i = 0; i < Classes.size() - 1; i++)
          {
            if ((Classes.get(i).getNumberOfGroups() > Classes.get(i + 1).getNumberOfGroups() && !reverse) || (Classes.get(i).getNumberOfGroups() < Classes.get(i + 1).getNumberOfGroups() && reverse))
            {
              Classes.add(i, Classes.get(i + 1));
              Classes.remove(i + 2);
              swapped = true;
            }
          }
        }
        break;
    }
  }

  /**This method:</br>
   * &nbsp;&nbsp;&nbsp;&nbsp; • constructs a new Student class and stores it in the Students ArrayList, unless:</br>
   * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ○ it already contains an other Student with the same VIAID</br>
   * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ○ there is no Class existing which the Student attends</br>
   * &nbsp;&nbsp;&nbsp;&nbsp; • stores a pointer of the Student in the corresponding Class.Students ArrayList</br>
   * &nbsp;&nbsp;&nbsp;&nbsp; • logs the method's successfullness to the console*/
  public void newStudent(String Name, int VIAID, String Participating, int Group)
  {
    Student new_student = new Student(Name, VIAID, Participating, Group);
    if(Students.contains(new_student))
    {
      System.out.println("ScheduleModelManager:    " + ANSI_RED + "Error registering: student " + VIAID + " already exists" + ANSI_RESET);
    }
    else if (Classes.contains(new Class(Participating)))
    {
      for (int i = 0; i < Classes.size(); i++)
      {
        if (Classes.get(i).getName().equals(Participating))
        {
          Classes.get(i).addStudent(new_student);
          Students.add(new_student);
          System.out.println("ScheduleModelManager:    " + ANSI_GREEN + "New student " + VIAID + " successfully registered" + ANSI_RESET);
        }
      }
    }
    else if(Participating.charAt(0) == '!')
    {
      Students.add(new_student);
      System.out.println("ScheduleModelManager:    " + ANSI_GREEN + "New student " + VIAID + " successfully registered with " + Participating + " tag" + ANSI_RESET);
    }
    else
    {
      System.out.println("ScheduleModelManager:    " + ANSI_RED + "Error registering: student " + VIAID + ", participating class " + Participating + " not found" + ANSI_RESET);
    }
  }

  /**This method:</br>
   * &nbsp;&nbsp;&nbsp;&nbsp; • removes the Student from the Students ArrayList with the VIAID given in the argument</br>
   * &nbsp;&nbsp;&nbsp;&nbsp; • removes the Student from the corresponding Class.Students ArrayList which he or she attends, unless, such Student does not exist</br>
   * &nbsp;&nbsp;&nbsp;&nbsp; • logs the method's successfullness to the console*/
  public void removeStudent(int VIAID)
  {
    if(Students.contains(new Student(VIAID)))
    {
      for (int i = 0; i < Students.size(); i++)
      {
        if (Students.get(i).getVIAID() == VIAID)
        {
          for (int j = 0; j < Classes.size(); j++)
          {
            if (Classes.get(j).getName().equals(Students.get(i).getParticipating()))
            {
              Classes.get(j).removeStudent(new Student(VIAID));
            }
          }
          Students.get(i).setName("!Student_Removed");
          Students.remove(i);
          System.out.println("ScheduleModelManager:    " + ANSI_YELLOW + "Student " + VIAID + " successfully removed" + ANSI_RESET);
        }
      }
    }
    else
    {
      System.out.println("ScheduleModelManager:    " + ANSI_RED + "Error removing: student " + VIAID + " not found" + ANSI_RESET);
    }
  }

  /**This getter:</br>
   * 	&nbsp;&nbsp;&nbsp;&nbsp; • returns the Student with the same VIAID inputted in the argument (if it exists)</br>
   * 	&nbsp;&nbsp;&nbsp;&nbsp; • logs the possible errors of the method to the console*/
  public Student getStudent(int VIAID)
  {
    if(Students.contains(new Student(VIAID)))
    {
      for (int i = 0; i < Students.size(); i++)
      {
        if (Students.get(i).getVIAID() == VIAID)
        {
          return  Students.get(i);
        }
      }
    }
    System.out.println("ScheduleModelManager:    " + ANSI_RED + "Error getting: student " + VIAID + " not found" + ANSI_RESET);
    return null;
  }

  /**This getter returns all of the Students.*/
  public Student[] getStudents()
  {
    Student[] temp = new Student[Students.size()];
    temp = Students.toArray(temp);
    return temp;
  }

  /**This method orders the Students by a certain quality of them given by the first argument and the order also can be reversed with the use of the second argument.*/
  public void orderStudents(String by, boolean reverse)
  {
    boolean swapped = true;
    switch (by)
    {
      case "VIAID":
        while (swapped)
        {
          swapped = false;
          for(int i = 0; i < Students.size() - 1; i++)
          {
            if ((Students.get(i).getVIAID() > Students.get(i + 1).getVIAID() && !reverse) || (Students.get(i).getVIAID() < Students.get(i + 1).getVIAID() && reverse))
            {
              Students.add(i, Students.get(i + 1));
              Students.remove(i + 2);
              swapped = true;
            }
          }
        }
        break;
      case "Name":
        while (swapped)
        {
          swapped = false;
          for (int i = 0; i < Students.size() - 1; i++)
          {
            if ((Students.get(i).getName().compareTo(Students.get(i + 1).getName()) > 0 && !reverse) || (Students.get(i).getName().compareTo(Students.get(i + 1).getName()) < 0 && reverse))
            {
              Students.add(i, Students.get(i + 1));
              Students.remove(i + 2);
              swapped = true;
            }
          }
        }
        break;
      case "Class":
        while (swapped)
        {
          swapped = false;
          for (int i = 0; i < Students.size() - 1; i++)
          {
            if ((Students.get(i).getParticipating().compareTo(Students.get(i + 1).getParticipating()) > 0 && !reverse) || (Students.get(i).getParticipating().compareTo(Students.get(i + 1).getParticipating()) < 0 && reverse))
            {
              Students.add(i, Students.get(i + 1));
              Students.remove(i + 2);
              swapped = true;
            }
          }
        }
        break;
      case "Group":
        while (swapped)
        {
          swapped = false;
          for(int i = 0; i < Students.size() - 1; i++)
          {
            if ((Students.get(i).getGroup() > Students.get(i + 1).getGroup() && !reverse) || (Students.get(i).getGroup() < Students.get(i + 1).getGroup() && reverse))
            {
              Students.add(i, Students.get(i + 1));
              Students.remove(i + 2);
              swapped = true;
            }
          }
        }
        break;
    }
  }

  /**This method:</br>
   * &nbsp;&nbsp;&nbsp;&nbsp; • constructs a new Examiner class and stores it in the Examiners ArrayList, unless it already contains an other Examiner with the same name</br>
   * &nbsp;&nbsp;&nbsp;&nbsp; • logs the method's successfullness to the console*/
  public void newExaminer(String Name, Date[] Unavailabilities)
  {
    Examiner new_examiner = new Examiner(Name, Unavailabilities);
    if(Examiners.contains(new_examiner))
    {
      System.out.println("ScheduleModelManager:    " + ANSI_RED + "Error registering: examiner " + Name + " already exists" + ANSI_RESET);
    }
    else
    {
      Examiners.add(new_examiner);
      System.out.println("ScheduleModelManager:    " + ANSI_GREEN + "New examiner " + Name + " successfully registered" + ANSI_RESET);
    }
  }

  /**This method:</br>
   * &nbsp;&nbsp;&nbsp;&nbsp; • removes the Examiner from the Examiners with the name given in the argument, unless such Examiner does not exist</br>
   * &nbsp;&nbsp;&nbsp;&nbsp; • renames the Examiner to "!Examiner_Removed"</br>
   * &nbsp;&nbsp;&nbsp;&nbsp; • logs the method's successfullness to the console*/
  public void removeExaminer(String Name)
  {
    if(Examiners.contains(new Examiner(Name)))
    {
      for (int i = 0; i < Examiners.size(); i++)
      {
        if (Examiners.get(i).getName().equals(Name))
        {
          Examiners.get(i).setName("!Examiner_Removed");
          Examiners.remove(i);
          System.out.println("ScheduleModelManager:    " + ANSI_YELLOW + "Examiner " + Name + " successfully removed" + ANSI_RESET);
        }
      }
    }
    else
    {
      System.out.println("ScheduleModelManager:    " + ANSI_RED + "Error removing: examiner " + Name + " not found" + ANSI_RESET);
    }
  }

  /**This getter:</br>
   * 	&nbsp;&nbsp;&nbsp;&nbsp; • returns the Examiner with the same Name inputted in the argument (if it exists)</br>
   * 	&nbsp;&nbsp;&nbsp;&nbsp; • logs the possible errors of the method to the console*/
  public Examiner getExaminer(String Name)
  {
    if(Examiners.contains(new Examiner(Name)))
    {
      for (int i = 0; i < Examiners.size(); i++)
      {
        if (Examiners.get(i).getName().equals(Name))
        {
          return Examiners.get(i);
        }
      }
    }
    else if(Name.equals("!Unassigned"))
      return new Examiner("!Unassigned",new Date[0]);
    System.out.println("ScheduleModelManager:    " + ANSI_RED + "Error getting: examiner " + Name + " not found" + ANSI_RESET);
    return null;
  }

  /**This getter returns all of the Examiners.*/
  public Examiner[] getExaminers()
  {
    Examiner[] temp = new Examiner[Examiners.size()];
    temp = Examiners.toArray(temp);
    return temp;
  }

  /**This method orders the Examiners by a certain quality of them given by the first argument and the order also can be reversed with the use of the second argument.*/
  public void orderExaminers(String by, boolean reverse)
  {
    boolean swapped = true;
    switch (by)
    {
      case "Name":
        while (swapped)
        {
          swapped = false;
          for(int i = 0; i < Examiners.size() - 1; i++)
          {
            if ((Examiners.get(i).getName().compareTo(Examiners.get(i + 1).getName()) > 0 && !reverse) || (Examiners.get(i).getName().compareTo(Examiners.get(i + 1).getName()) < 0 && reverse))
            {
              Examiners.add(i, Examiners.get(i + 1));
              Examiners.remove(i + 2);
              swapped = true;
            }
          }
        }
        break;
      case "Unavailabilities":
        while (swapped)
        {
          swapped = false;
          for(int i = 0; i < Examiners.size() - 1; i++)
          {
            if ((Examiners.get(i).getUnavailabilities().length > Examiners.get(i + 1).getUnavailabilities().length && !reverse) || (Examiners.get(i).getUnavailabilities().length < Examiners.get(i + 1).getUnavailabilities().length && reverse))
            {
              Examiners.add(i, Examiners.get(i + 1));
              Examiners.remove(i + 2);
              swapped = true;
            }
          }
        }
        break;
    }
  }

  /**This method:</br>
   * &nbsp;&nbsp;&nbsp;&nbsp; • constructs a new Room class and stores it in the Rooms ArrayList, unless it already contains an other Room with the same name</br>
   * &nbsp;&nbsp;&nbsp;&nbsp; • logs the method's successfullness to the console*/
  public void newRoom(String Name, int Size, String[] Equipments)
  {
    Room new_room = new Room(Name, Size, Equipments);
    if(Rooms.contains(new_room))
    {
      System.out.println("ScheduleModelManager:    " + ANSI_RED + "Error registering: room " + Name + " already exists" + ANSI_RESET);
    }
    else
    {
      Rooms.add(new_room);
      System.out.println("ScheduleModelManager:    " + ANSI_GREEN + "New room " + Name + " successfully registered" + ANSI_RESET);
    }
  }

  /**This method:</br>
   * &nbsp;&nbsp;&nbsp;&nbsp; • removes the Room from the Rooms with the name given in the argument, unless such Room does not exist</br>
   * &nbsp;&nbsp;&nbsp;&nbsp; • renames the Room to "!Room_Removed"</br>
   * &nbsp;&nbsp;&nbsp;&nbsp; • logs the method's successfullness to the console*/
  public void removeRoom(String Name)
  {
    if(Rooms.contains(new Room(Name)))
    {
      for (int i = 0; i < Rooms.size(); i++)
      {
        if (Rooms.get(i).getName().equals(Name))
        {
          Rooms.get(i).setName("!Room_Removed");
          Rooms.remove(i);
          System.out.println("ScheduleModelManager:    " + ANSI_YELLOW + "Room " + Name + " successfully removed" + ANSI_RESET);
        }
      }
    }
    else
    {
      System.out.println("ScheduleModelManager:    " + ANSI_RED + "Error removing: room " + Name + " not found" + ANSI_RESET);
    }
  }

  /**This getter:</br>
   * 	&nbsp;&nbsp;&nbsp;&nbsp; • returns the Room with the same Name inputted in the argument (if it exists)</br>
   * 	&nbsp;&nbsp;&nbsp;&nbsp; • logs the possible errors of the method to the console*/
  public Room getRoom(String Name)
  {
    if(Rooms.contains(new Room(Name)))
    {
      for (int i = 0; i < Rooms.size(); i++)
      {
        if (Rooms.get(i).getName().equals(Name))
        {
          return  Rooms.get(i);
        }
      }
    }
    else if(Name.equals("!Unassigned"))
      return new Room("!Unassigned",0,new String[0]);
    System.out.println("ScheduleModelManager:    " + ANSI_RED + "Error getting: room " + Name + " not found" + ANSI_RESET);
    return null;
  }

  /**This getter returns all of the Rooms.*/
  public Room[] getRooms()
  {
    Room[] temp = new Room[Rooms.size()];
    temp = Rooms.toArray(temp);
    return temp;
  }

  /**This method orders the Rooms by a certain quality of them given by the first argument and the order also can be reversed with the use of the second argument.*/
  public void orderRooms(String by, boolean reverse)
  {
    boolean swapped = true;
    switch (by)
    {
      case "Name":
        while (swapped)
        {
          swapped = false;
          for(int i = 0; i < Rooms.size() - 1; i++)
          {
            if ((Rooms.get(i).getName().compareTo(Rooms.get(i + 1).getName()) > 0 && !reverse) || (Rooms.get(i).getName().compareTo(Rooms.get(i + 1).getName()) < 0 && reverse))
            {
              Rooms.add(i, Rooms.get(i + 1));
              Rooms.remove(i + 2);
              swapped = true;
            }
          }
        }
        break;
      case "Size":
        while (swapped)
        {
          swapped = false;
          for(int i = 0; i < Rooms.size() - 1; i++)
          {
            if ((Rooms.get(i).getSize() > Rooms.get(i + 1).getSize() && !reverse) || (Rooms.get(i).getSize() < Rooms.get(i + 1).getSize() && reverse))
            {
              Rooms.add(i, Rooms.get(i + 1));
              Rooms.remove(i + 2);
              swapped = true;
            }
          }
        }
        break;
      case "Equipments":
        while (swapped)
        {
          swapped = false;
          for(int i = 0; i < Rooms.size() - 1; i++)
          {
            if ((Rooms.get(i).getEquipments().length > Rooms.get(i + 1).getEquipments().length && !reverse) || (Rooms.get(i).getEquipments().length < Rooms.get(i + 1).getEquipments().length && reverse))
            {
              Rooms.add(i, Rooms.get(i + 1));
              Rooms.remove(i + 2);
              swapped = true;
            }
          }
        }
        break;
    }
  }

  /**This method:</br>
   * &nbsp;&nbsp;&nbsp;&nbsp; • constructs an ExamPeriod, which stores the attributes that define this examination period</br>
   * &nbsp;&nbsp;&nbsp;&nbsp; • logs the method's successfullness to the console*/
  public void initExamPeriod(Date[] ValidDates, Date StartTime, Date EndTime, Date BreakStartTime, Date BreakEndTime, int BreakDuration, int DefDurPerStudent, int DefDurPerStudentPerGroup, int DefBreakDurPerStudent, int DefBreakDurPerGroup)
  {
    this.ValidExamPeriod = new ExamPeriod(ValidDates, StartTime, EndTime, BreakStartTime, BreakEndTime, BreakDuration, DefDurPerStudent, DefDurPerStudentPerGroup, DefBreakDurPerStudent, DefBreakDurPerGroup);
    System.out.println("ScheduleModelManager:    " + ANSI_GREEN + "ExamPeriod successfully registered" + ANSI_RESET);
  }

  /**This getter returns the ExamPeriod.*/
  public ExamPeriod getExamPeriod()
  {
    return ValidExamPeriod;
  }

  /**This method:</br>
   * &nbsp;&nbsp;&nbsp;&nbsp; • constructs a new Exam class and stores it in the Exams ArrayList, unless it already contains an other Exam with the same name</br>
   * &nbsp;&nbsp;&nbsp;&nbsp; • logs the method's successfullness to the console*/
  public void newExam(String type, String Name, String Place, String Class, String Examiner1, String Examiner2, String[] Requirements, Date StartDate, String Duration, String BreakDuartion, String DurationPerStudent)
  {
    int duration, breakDuartion, durationPerStudent;
    try
    {
      duration = Integer.parseInt(Duration);
    }
    catch (Exception e)
    {
      duration = 0;
    }
    try
    {
      breakDuartion = Integer.parseInt(BreakDuartion);
    }
    catch (Exception e)
    {
      breakDuartion = 0;
    }
    try
    {
      durationPerStudent = Integer.parseInt(DurationPerStudent);
    }
    catch (Exception e)
    {
      durationPerStudent = 0;
    }
    switch (type)
    {
      case "Written":
          if (Exams.contains(new Exam(Name)))
          {
            System.out.println("ScheduleModelManager:    " + ANSI_RED + "Error registering: written exam " + Name + " already exists" + ANSI_RESET);
            break;
          }
          else if (!Rooms.contains(new Room(Place)) && !Place.equals("!Unassigned"))
          {
            System.out.println("ScheduleModelManager:    " + ANSI_RED + "Error registering: written exam " + Name + ", room " + Place + " not found" + ANSI_RESET);
            break;
          }
          else if (!Classes.contains(new Class(Class)) && !Class.equals("!Unassigned"))
          {
            System.out.println("ScheduleModelManager:    " + ANSI_RED + "Error registering: written exam " + Name + ", class " + Class + " not found" + ANSI_RESET);
            break;
          }
          else if (!Examiners.contains(new Examiner(Examiner1)) && !Examiner1.equals("!Unassigned"))
          {
            System.out.println("ScheduleModelManager:    " + ANSI_RED + "Error registering: written exam " + Name + ", examiner " + Examiner1 + " not found" + ANSI_RESET);
            break;
          }
          else
          {
            System.out.println("ScheduleModelManager:    " + ANSI_GREEN + "New written exam " + Name + " successfully registered" + ANSI_RESET);
            Exams.add(new WrittenExam(Name,getRoom(Place),getClass(Class),getExaminer(Examiner1),Requirements,StartDate,ValidExamPeriod,duration));
          }
        break;
      case "Oral":
        if (Exams.contains(new Exam(Name)))
        {
          System.out.println("ScheduleModelManager:    " + ANSI_RED + "Error registering: oral exam " + Name + " already exists" + ANSI_RESET);
          break;
        }
        else if (!Rooms.contains(new Room(Place)) && !Place.equals("!Unassigned"))
        {
          System.out.println("ScheduleModelManager:    " + ANSI_RED + "Error registering: oral exam " + Name + ", room " + Place + " not found" + ANSI_RESET);
          break;
        }
        else if (!Classes.contains(new Class(Class)) && !Class.equals("!Unassigned"))
        {
          System.out.println("ScheduleModelManager:    " + ANSI_RED + "Error registering: oral exam " + Name + ", class " + Class + " not found" + ANSI_RESET);
          break;
        }
        else if (!Examiners.contains(new Examiner(Examiner1)) && !Examiner1.equals("!Unassigned"))
        {
          System.out.println("ScheduleModelManager:    " + ANSI_RED + "Error registering: oral exam " + Name + ", examiner " + Examiner1 + " not found" + ANSI_RESET);
          break;
        }
        else if (!Examiners.contains(new Examiner(Examiner2)) && !Examiner2.equals("!Unassigned"))
        {
          System.out.println("ScheduleModelManager:    " + ANSI_RED + "Error registering: oral exam " + Name + ", examiner " + Examiner2 + " not found" + ANSI_RESET);
          break;
        }
        else
        {
          System.out.println("ScheduleModelManager:    " + ANSI_GREEN + "New oral exam " + Name + " successfully registered" + ANSI_RESET);
          Exams.add(new OralExam(Name,getRoom(Place),getClass(Class),getExaminer(Examiner1),getExaminer(Examiner2),Requirements,StartDate,ValidExamPeriod,duration,durationPerStudent,breakDuartion));
        }
        break;
      case "Project":
        if (Exams.contains(new Exam(Name)))
        {
          System.out.println("ScheduleModelManager:    " + ANSI_RED + "Error registering: project exam " + Name + " already exists" + ANSI_RESET);
          break;
        }
        else if (!Rooms.contains(new Room(Place)) && !Place.equals("!Unassigned"))
        {
          System.out.println("ScheduleModelManager:    " + ANSI_RED + "Error registering: project exam " + Name + ", room " + Place + " not found" + ANSI_RESET);
          break;
        }
        else if (!Classes.contains(new Class(Class)) && !Class.equals("!Unassigned"))
        {
          System.out.println("ScheduleModelManager:    " + ANSI_RED + "Error registering: project exam " + Name + ", class " + Class + " not found" + ANSI_RESET);
          break;
        }
        else if (!Examiners.contains(new Examiner(Examiner1)) && !Examiner1.equals("!Unassigned"))
        {
          System.out.println("ScheduleModelManager:    " + ANSI_RED + "Error registering: project exam " + Name + ", examiner " + Examiner1 + " not found" + ANSI_RESET);
          break;
        }
        else if (!Examiners.contains(new Examiner(Examiner2)) && !Examiner2.equals("!Unassigned"))
        {
          System.out.println("ScheduleModelManager:    " + ANSI_RED + "Error registering: project exam " + Name + ", examiner " + Examiner2 + " not found" + ANSI_RESET);
          break;
        }
        else
        {
          System.out.println("ScheduleModelManager:    " + ANSI_GREEN + "New project exam " + Name + " successfully registered" + ANSI_RESET);
          Exams.add(new ProjectExam(Name,getRoom(Place),getClass(Class),getExaminer(Examiner1),getExaminer(Examiner2),Requirements,StartDate,ValidExamPeriod,duration,durationPerStudent,breakDuartion));
        }
        break;
    }
  }

  /**This method:</br>
   * &nbsp;&nbsp;&nbsp;&nbsp; • removes the Exam from the Exams with the name given in the argument, unless such Exam does not exist</br>
   * &nbsp;&nbsp;&nbsp;&nbsp; • logs the method's successfullness to the console*/
  public void removeExam(String Name)
  {
    if(Exams.contains(new Exam(Name)))
    {
      for (int i = 0; i < Exams.size(); i++)
      {
        if (Exams.get(i).getName().equals(Name))
        {
          Exams.remove(i);
          System.out.println("ScheduleModelManager:    " + ANSI_YELLOW + "Exam " + Name + " successfully removed" + ANSI_RESET);
        }
      }
    }
    else
    {
      System.out.println("ScheduleModelManager:    " + ANSI_RED + "Error removing: exam " + Name + " not found" + ANSI_RESET);
    }
  }

  /**This getter:</br>
   * 	&nbsp;&nbsp;&nbsp;&nbsp; • returns the Exam with the same Name inputted in the argument (if it exists)</br>
   * 	&nbsp;&nbsp;&nbsp;&nbsp; • logs the possible errors of the method to the console*/
  public Exam getExam(String Name)
  {
    if(Exams.contains(new Exam(Name)))
    {
      for (int i = 0; i < Exams.size(); i++)
      {
        if (Exams.get(i).getName().equals(Name))
        {
          return  Exams.get(i);
        }
      }
    }
    System.out.println("ScheduleModelManager:    " + ANSI_RED + "Error getting: exam " + Name + " not found" + ANSI_RESET);
    return null;
  }

  /**This getter returns all of the Exams.*/
  public Exam[] getExams()
  {
    Exam[] temp = new Exam[Exams.size()];
    temp = Exams.toArray(temp);
    return temp;
  }

  /**This method orders the Exams by a certain quality of them given by the first argument and the order also can be reversed with the use of the second argument.*/
  public void orderExams(String by, boolean reverse)
  {
    boolean swapped = true;
    switch (by)
    {
      case "Type":
        while (swapped)
        {
          swapped = false;
          for(int i = 0; i < Exams.size() - 1; i++)
          {
            if ((Exams.get(i).getClass().getName().compareTo(Exams.get(i + 1).getClass().getName()) > 0 && !reverse) || (Exams.get(i).getClass().getName().compareTo(Exams.get(i + 1).getClass().getName()) < 0 && reverse))
            {
              Exams.add(i, Exams.get(i + 1));
              Exams.remove(i + 2);
              swapped = true;
            }
          }
        }
        break;
      case "Name":
        while (swapped)
        {
          swapped = false;
          for(int i = 0; i < Exams.size() - 1; i++)
          {
            if ((Exams.get(i).getName().compareTo(Exams.get(i + 1).getName()) > 0 && !reverse) || (Exams.get(i).getName().compareTo(Exams.get(i + 1).getName()) < 0 && reverse))
            {
              Exams.add(i, Exams.get(i + 1));
              Exams.remove(i + 2);
              swapped = true;
            }
          }
        }
        break;
      case "Place":
        while (swapped)
        {
          swapped = false;
          for(int i = 0; i < Exams.size() - 1; i++)
          {
            if ((Exams.get(i).getPlace().getName().compareTo(Exams.get(i + 1).getPlace().getName()) > 0 && !reverse) || (Exams.get(i).getPlace().getName().compareTo(Exams.get(i + 1).getPlace().getName()) < 0 && reverse))
            {
              Exams.add(i, Exams.get(i + 1));
              Exams.remove(i + 2);
              swapped = true;
            }
          }
        }
        break;
      case "Class":
        while (swapped)
        {
          swapped = false;
          for(int i = 0; i < Exams.size() - 1; i++)
          {
            if ((Exams.get(i).getParticipatingClass().getName().compareTo(Exams.get(i + 1).getParticipatingClass().getName()) > 0 && !reverse) || (Exams.get(i).getParticipatingClass().getName().compareTo(Exams.get(i + 1).getParticipatingClass().getName()) < 0 && reverse))
            {
              Exams.add(i, Exams.get(i + 1));
              Exams.remove(i + 2);
              swapped = true;
            }
          }
        }
        break;
      case "Examiner1":
        while (swapped)
        {
          swapped = false;
          for(int i = 0; i < Exams.size() - 1; i++)
          {
            if ((Exams.get(i).getExaminer().getName().compareTo(Exams.get(i + 1).getExaminer().getName()) > 0 && !reverse) || (Exams.get(i).getExaminer().getName().compareTo(Exams.get(i + 1).getExaminer().getName()) < 0 && reverse))
            {
              Exams.add(i, Exams.get(i + 1));
              Exams.remove(i + 2);
              swapped = true;
            }
          }
        }
        break;
      case "Examiner2":
        while (swapped)
        {
          swapped = false;
          for(int i = 0; i < Exams.size() - 1; i++)
          {
            if (Exams.get(i) instanceof OralExam && Exams.get(i + 1) instanceof OralExam)
            {
              if ((((OralExam) Exams.get(i)).getExaminer2().getName().compareTo(((OralExam) Exams.get(i + 1)).getExaminer2().getName()) > 0 && !reverse) || (((OralExam) Exams.get(i)).getExaminer2().getName().compareTo(((OralExam) Exams.get(i + 1)).getExaminer2().getName()) < 0 && reverse))
              {
                Exams.add(i, Exams.get(i + 1));
                Exams.remove(i + 2);
                swapped = true;
              }
            }
            if (Exams.get(i) instanceof ProjectExam && Exams.get(i + 1) instanceof ProjectExam)
            {
              if ((((ProjectExam) Exams.get(i)).getExaminer2().getName().compareTo(((ProjectExam) Exams.get(i + 1)).getExaminer2().getName()) > 0 && !reverse) || (((ProjectExam) Exams.get(i)).getExaminer2().getName().compareTo(((ProjectExam) Exams.get(i + 1)).getExaminer2().getName()) < 0 && reverse))
              {
                Exams.add(i, Exams.get(i + 1));
                Exams.remove(i + 2);
                swapped = true;
              }
            }
            if (Exams.get(i) instanceof ProjectExam && Exams.get(i + 1) instanceof OralExam)
            {
              if ((((ProjectExam) Exams.get(i)).getExaminer2().getName().compareTo(((OralExam) Exams.get(i + 1)).getExaminer2().getName()) > 0 && !reverse) || (((ProjectExam) Exams.get(i)).getExaminer2().getName().compareTo(((OralExam) Exams.get(i + 1)).getExaminer2().getName()) < 0 && reverse))
              {
                Exams.add(i, Exams.get(i + 1));
                Exams.remove(i + 2);
                swapped = true;
              }
            }
            if (Exams.get(i) instanceof OralExam && Exams.get(i + 1) instanceof ProjectExam)
            {
              if ((((OralExam) Exams.get(i)).getExaminer2().getName().compareTo(((ProjectExam) Exams.get(i + 1)).getExaminer2().getName()) > 0 && !reverse) || (((OralExam) Exams.get(i)).getExaminer2().getName().compareTo(((ProjectExam) Exams.get(i + 1)).getExaminer2().getName()) < 0 && reverse))
              {
                Exams.add(i, Exams.get(i + 1));
                Exams.remove(i + 2);
                swapped = true;
              }
            }
            if (Exams.get(i) instanceof OralExam && Exams.get(i + 1) instanceof WrittenExam)
            {
              if ((((OralExam) Exams.get(i)).getExaminer2().getName().compareTo("") > 0 && !reverse) || (((OralExam) Exams.get(i)).getExaminer2().getName().compareTo("") < 0 && reverse))
              {
                Exams.add(i, Exams.get(i + 1));
                Exams.remove(i + 2);
                swapped = true;
              }
            }
            if (Exams.get(i) instanceof ProjectExam && Exams.get(i + 1) instanceof WrittenExam)
            {
              if ((((ProjectExam) Exams.get(i)).getExaminer2().getName().compareTo("") > 0 && !reverse) || (((ProjectExam) Exams.get(i)).getExaminer2().getName().compareTo("") < 0 && reverse))
              {
                Exams.add(i, Exams.get(i + 1));
                Exams.remove(i + 2);
                swapped = true;
              }
            }
            if (Exams.get(i) instanceof WrittenExam && Exams.get(i + 1) instanceof OralExam)
            {
              if (("".compareTo(((OralExam) Exams.get(i + 1)).getExaminer2().getName()) > 0 && !reverse) || ("".compareTo(((OralExam) Exams.get(i + 1)).getExaminer2().getName()) < 0 && reverse))
              {
                Exams.add(i, Exams.get(i + 1));
                Exams.remove(i + 2);
                swapped = true;
              }
            }
            if (Exams.get(i) instanceof WrittenExam && Exams.get(i + 1) instanceof ProjectExam)
            {
              if (("".compareTo(((ProjectExam) Exams.get(i + 1)).getExaminer2().getName()) > 0 && !reverse) || ("".compareTo(((ProjectExam) Exams.get(i + 1)).getExaminer2().getName()) < 0 && reverse))
              {
                Exams.add(i, Exams.get(i + 1));
                Exams.remove(i + 2);
                swapped = true;
              }
            }
          }
        }
        break;
      case "Start":
        while (swapped)
        {
          swapped = false;
          for(int i = 0; i < Exams.size() - 1; i++)
          {
            if ((Exams.get(i + 1).getStartDate().isBefore(Exams.get(i).getStartDate()) && !reverse) || (Exams.get(i).getStartDate().isBefore(Exams.get(i + 1).getStartDate()) && reverse))
            {
              Exams.add(i, Exams.get(i + 1));
              Exams.remove(i + 2);
              swapped = true;
            }
          }
        }
        break;
      case "StartTime":
        while (swapped)
        {
          swapped = false;
          for(int i = 0; i < Exams.size() - 1; i++)
          {
            if ((Exams.get(i + 1).getStartDate().chopYMD().isBefore(Exams.get(i).getStartDate().chopYMD()) && !reverse) || (Exams.get(i).getStartDate().chopYMD().isBefore(Exams.get(i + 1).getStartDate().chopYMD()) && reverse))
            {
              Exams.add(i, Exams.get(i + 1));
              Exams.remove(i + 2);
              swapped = true;
            }
          }
        }
        break;
      case "Duration":
        while (swapped)
        {
          swapped = false;
          for(int i = 0; i < Exams.size() - 1; i++)
          {
            if ((Exams.get(i).getDuration() > Exams.get(i + 1).getDuration() && !reverse) || (Exams.get(i).getDuration() < Exams.get(i + 1).getDuration() && reverse))
            {
              Exams.add(i, Exams.get(i + 1));
              Exams.remove(i + 2);
              swapped = true;
            }
          }
        }
        break;
      case "End":
        while (swapped)
        {
          swapped = false;
          for(int i = 0; i < Exams.size() - 1; i++)
          {
            if ((Exams.get(i + 1).getEnd().isBefore(Exams.get(i).getEnd()) && !reverse) || (Exams.get(i).getEnd().isBefore(Exams.get(i + 1).getEnd()) && reverse))
            {
              Exams.add(i, Exams.get(i + 1));
              Exams.remove(i + 2);
              swapped = true;
            }
          }
        }
        break;
    }
  }

  public void generateConflicts()
  {
    for (int i = 0; i < Exams.size(); i++)
    {
      Exams.get(i).generateConflicts(getExams());
    }
  }

  //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


  /**This method clears all ArrayLists.*/
  public void clearAll()
  {
    System.out.println("ScheduleModelManager:    " + ANSI_YELLOW + "All Classes has been removed" + ANSI_RESET);
    Classes.clear();
    System.out.println("ScheduleModelManager:    " + ANSI_YELLOW + "All Students has been removed" + ANSI_RESET);
    Students.clear();
    System.out.println("ScheduleModelManager:    " + ANSI_YELLOW + "All Examiners has been removed" + ANSI_RESET);
    Examiners.clear();
    System.out.println("ScheduleModelManager:    " + ANSI_YELLOW + "All Rooms has been removed" + ANSI_RESET);
    Rooms.clear();
    System.out.println("ScheduleModelManager:    " + ANSI_YELLOW + "ValidExamPeriod has been removed" + ANSI_RESET);
    ValidExamPeriod = new ExamPeriod(new Date[0],new Date("00:00"),new Date("00:00"),new Date("1/1/1 00:00"),new Date("1/1/1 00:00"),0,0,0,0,0);
    System.out.println("ScheduleModelManager:    " + ANSI_YELLOW + "All Exams has been removed" + ANSI_RESET);
    Exams.clear();
  }

  /**This method clears all ArrayLists and then loads all elements from the xml files from USER.HOME\.scheduler\data\.*/
  public void loadXMLs()
  {
    clearAll();

    String Name, Participating, Place, Examiner1, Examiner2, Duration;
    int VIAID, Group, Size;
    System.out.println("ScheduleModelManager:    " + ANSI_CYAN + "Processing " + System.getProperty("user.home") + "\\.scheduler\\data\\Classes.xml" + ANSI_RESET);
    xmlReader Classes = new xmlReader(System.getProperty("user.home") + "\\.scheduler\\data\\Classes.xml");

    for (int i = 0; i < Classes.getRootNode().getChildren().length; i++)
    {
      newClass(Classes.getRootNode().getChildren()[i].getText());
    }
    System.out.println("ScheduleModelManager:    " + ANSI_CYAN + "Processing " + System.getProperty("user.home") + "\\.scheduler\\data\\Students.xml" + ANSI_RESET);
    xmlReader Students = new xmlReader(System.getProperty("user.home") + "\\.scheduler\\data\\Students.xml");
    for (int i = 0; i < Students.getRootNode().getChildren().length; i++)
    {
      Name = Students.getRootNode().getChildren()[i].getChildrenWithName("Name")[0].getText();
      VIAID = Integer.parseInt(Students.getRootNode().getChildren()[i].getChildrenWithName("VIAID")[0].getText());
      Participating = Students.getRootNode().getChildren()[i].getChildrenWithName("Participating")[0].getText();
      Group = Integer.parseInt(Students.getRootNode().getChildren()[i].getChildrenWithName("Group")[0].getText());
      newStudent(Name, VIAID, Participating, Group);
    }
    System.out.println("ScheduleModelManager:    " + ANSI_CYAN + "Processing " + System.getProperty("user.home") + "\\.scheduler\\data\\Examiners.xml" + ANSI_RESET);
    xmlReader Examiners = new xmlReader(System.getProperty("user.home") + "\\.scheduler\\data\\Examiners.xml");
    for (int i = 0; i < Examiners.getRootNode().getChildren().length; i++)
    {
      Name = Examiners.getRootNode().getChildren()[i].getChildrenWithName("Name")[0].getText();
      Date[] Unavailabilities = new Date[Examiners.getRootNode().getChildren()[i].getChildrenWithName("Unavailability").length * 2];
      for (int j = 0; j < Examiners.getRootNode().getChildren()[i].getChildrenWithName("Unavailability").length; j++)
      {
        Unavailabilities[j * 2] = new Date(Examiners.getRootNode().getChildren()[i].getChildrenWithName("Unavailability")[j].getChildrenWithName("Start")[0].getText());
        Unavailabilities[j * 2 + 1] = new Date(Examiners.getRootNode().getChildren()[i].getChildrenWithName("Unavailability")[j].getChildrenWithName("End")[0].getText());
      }
      newExaminer(Name, Unavailabilities);
    }
    System.out.println("ScheduleModelManager:    " + ANSI_CYAN + "Processing " + System.getProperty("user.home") + "\\.scheduler\\data\\Rooms.xml" + ANSI_RESET);
    xmlReader Rooms = new xmlReader(System.getProperty("user.home") + "\\.scheduler\\data\\Rooms.xml");
    for (int i = 0; i < Rooms.getRootNode().getChildren().length; i++)
    {
      Name = Rooms.getRootNode().getChildren()[i].getChildrenWithName("Name")[0].getText();
      Size = Integer.parseInt(Rooms.getRootNode().getChildren()[i].getChildrenWithName("Size")[0].getText());
      String[] Equipments = new String[Rooms.getRootNode().getChildren()[i].getChildrenWithName("Equipments")[0].getChildrenWithName("Equipment").length];
      for (int j = 0; j < Rooms.getRootNode().getChildren()[i].getChildrenWithName("Equipments")[0].getChildrenWithName("Equipment").length; j++)
      {
        Equipments[j] = Rooms.getRootNode().getChildren()[i].getChildrenWithName("Equipments")[0].getChildrenWithName("Equipment")[j].getText();
      }
      newRoom(Name, Size, Equipments);
    }
    System.out.println("ScheduleModelManager:    " + ANSI_CYAN + "Processing " + System.getProperty("user.home") + "\\.scheduler\\data\\ValidExamPeriod.xml" + ANSI_RESET);
    xmlReader ValidExamPeriod = new xmlReader(System.getProperty("user.home") + "\\.scheduler\\data\\ValidExamPeriod.xml");
    Date StartTime = new Date(ValidExamPeriod.getRootNode().getChildrenWithName("StartTime")[0].getText());
    Date EndTime = new Date(ValidExamPeriod.getRootNode().getChildrenWithName("EndTime")[0].getText());
    Date BreakStartTime = new Date(ValidExamPeriod.getRootNode().getChildrenWithName("BreakStartTime")[0].getText());
    Date BreakEndTime = new Date(ValidExamPeriod.getRootNode().getChildrenWithName("BreakEndTime")[0].getText());
    int BreakDuration = Integer.parseInt(ValidExamPeriod.getRootNode().getChildrenWithName("BreakDuration")[0].getText());
    int DefDurPerStudent = Integer.parseInt(ValidExamPeriod.getRootNode().getChildrenWithName("DefaultDurationPerStudent")[0].getText());
    int DefDurPerStudentPerGroup = Integer.parseInt(ValidExamPeriod.getRootNode().getChildrenWithName("DefaultDurationPerStudentPerGroup")[0].getText());
    int DefBreakDurPerStudent = Integer.parseInt(ValidExamPeriod.getRootNode().getChildrenWithName("DefaultBreakDurationPerStudent")[0].getText());
    int DefBreakDurPerGroup = Integer.parseInt(ValidExamPeriod.getRootNode().getChildrenWithName("DefaultBreakDurationPerGroup")[0].getText());
    Date[] ValidDates = new Date[ValidExamPeriod.getRootNode().getChildrenWithName("ValidDates")[0].getChildrenWithName("Date").length];
    for (int i = 0; i < ValidExamPeriod.getRootNode().getChildrenWithName("ValidDates")[0].getChildrenWithName("Date").length; i++)
    {
      ValidDates[i] = new Date(ValidExamPeriod.getRootNode().getChildrenWithName("ValidDates")[0].getChildrenWithName("Date")[i].getText());
    }
    initExamPeriod(ValidDates, StartTime, EndTime, BreakStartTime, BreakEndTime, BreakDuration, DefDurPerStudent, DefDurPerStudentPerGroup, DefBreakDurPerStudent, DefBreakDurPerGroup);
    System.out.println("ScheduleModelManager:    " + ANSI_CYAN + "Processing " + System.getProperty("user.home") + "\\.scheduler\\data\\Exams.xml" + ANSI_RESET);
    xmlReader Exams = new xmlReader(System.getProperty("user.home") + "\\.scheduler\\data\\Exams.xml");
    for (int i = 0; i < Exams.getRootNode().getChildrenWithName("WrittenExam").length; i++)
    {
      Name = Exams.getRootNode().getChildrenWithName("WrittenExam")[i].getChildrenWithName("Name")[0].getText();
      Place = Exams.getRootNode().getChildrenWithName("WrittenExam")[i].getChildrenWithName("Place")[0].getText();
      Participating = Exams.getRootNode().getChildrenWithName("WrittenExam")[i].getChildrenWithName("Participating")[0].getText();
      Examiner1 = Exams.getRootNode().getChildrenWithName("WrittenExam")[i].getChildrenWithName("Examiner1")[0].getText();
      String[] Requirements = new String[Exams.getRootNode().getChildrenWithName("WrittenExam")[i].getChildrenWithName("Requirements")[0].getChildrenWithName("Requirement").length];
      for (int j = 0; j < Exams.getRootNode().getChildrenWithName("WrittenExam")[i].getChildrenWithName("Requirements")[0].getChildrenWithName("Requirement").length; j++)
      {
        Requirements[j] = Exams.getRootNode().getChildrenWithName("WrittenExam")[i].getChildrenWithName("Requirements")[0].getChildrenWithName("Requirement")[j].getText();
      }
      Date Start = new Date(Exams.getRootNode().getChildrenWithName("WrittenExam")[i].getChildrenWithName("Start")[0].getText());
      Duration = Exams.getRootNode().getChildrenWithName("WrittenExam")[i].getChildrenWithName("Duration")[0].getText();
      newExam("Written",Name,Place,Participating,Examiner1,"!Unassigned",Requirements,Start,Duration,"","");
    }
    for (int i = 0; i < Exams.getRootNode().getChildrenWithName("OralExam").length; i++)
    {
      Name = Exams.getRootNode().getChildrenWithName("OralExam")[i].getChildrenWithName("Name")[0].getText();
      Place = Exams.getRootNode().getChildrenWithName("OralExam")[i].getChildrenWithName("Place")[0].getText();
      Participating = Exams.getRootNode().getChildrenWithName("OralExam")[i].getChildrenWithName("Participating")[0].getText();
      Examiner1 = Exams.getRootNode().getChildrenWithName("OralExam")[i].getChildrenWithName("Examiner1")[0].getText();
      Examiner2 = Exams.getRootNode().getChildrenWithName("OralExam")[i].getChildrenWithName("Examiner2")[0].getText();
      String[] Requirements = new String[Exams.getRootNode().getChildrenWithName("OralExam")[i].getChildrenWithName("Requirements")[0].getChildrenWithName("Requirement").length];
      for (int j = 0; j < Exams.getRootNode().getChildrenWithName("OralExam")[i].getChildrenWithName("Requirements")[0].getChildrenWithName("Requirement").length; j++)
      {
        Requirements[j] = Exams.getRootNode().getChildrenWithName("OralExam")[i].getChildrenWithName("Requirements")[0].getChildrenWithName("Requirement")[j].getText();
      }
      Date Start = new Date(Exams.getRootNode().getChildrenWithName("OralExam")[i].getChildrenWithName("Start")[0].getText());
      Duration = Exams.getRootNode().getChildrenWithName("OralExam")[i].getChildrenWithName("Duration")[0].getText();
      String BreakDurationPerStudent = Exams.getRootNode().getChildrenWithName("OralExam")[i].getChildrenWithName("BreakDurationPerStudent")[0].getText();
      String DurationPerStudent = Exams.getRootNode().getChildrenWithName("OralExam")[i].getChildrenWithName("DurationPerStudent")[0].getText();
      newExam("Oral",Name,Place,Participating,Examiner1,Examiner2,Requirements,Start,Duration,DurationPerStudent,BreakDurationPerStudent);
    }
    for (int i = 0; i < Exams.getRootNode().getChildrenWithName("ProjectExam").length; i++)
    {
      Name = Exams.getRootNode().getChildrenWithName("ProjectExam")[i].getChildrenWithName("Name")[0].getText();
      Place = Exams.getRootNode().getChildrenWithName("ProjectExam")[i].getChildrenWithName("Place")[0].getText();
      Participating = Exams.getRootNode().getChildrenWithName("ProjectExam")[i].getChildrenWithName("Participating")[0].getText();
      Examiner1 = Exams.getRootNode().getChildrenWithName("ProjectExam")[i].getChildrenWithName("Examiner1")[0].getText();
      Examiner2 = Exams.getRootNode().getChildrenWithName("ProjectExam")[i].getChildrenWithName("Examiner2")[0].getText();
      String[] Requirements = new String[Exams.getRootNode().getChildrenWithName("ProjectExam")[i].getChildrenWithName("Requirements")[0].getChildrenWithName("Requirement").length];
      for (int j = 0; j < Exams.getRootNode().getChildrenWithName("ProjectExam")[i].getChildrenWithName("Requirements")[0].getChildrenWithName("Requirement").length; j++)
      {
        Requirements[j] = Exams.getRootNode().getChildrenWithName("ProjectExam")[i].getChildrenWithName("Requirements")[0].getChildrenWithName("Requirement")[j].getText();
      }
      Date Start = new Date(Exams.getRootNode().getChildrenWithName("ProjectExam")[i].getChildrenWithName("Start")[0].getText());
      Duration = Exams.getRootNode().getChildrenWithName("ProjectExam")[i].getChildrenWithName("Duration")[0].getText();
      String BreakDurationPerGroup = Exams.getRootNode().getChildrenWithName("ProjectExam")[i].getChildrenWithName("BreakDurationPerGroup")[0].getText();
      String DurationPerStudentPerGroup = Exams.getRootNode().getChildrenWithName("ProjectExam")[i].getChildrenWithName("DurationPerStudentPerGroup")[0].getText();
      newExam("Project",Name,Place,Participating,Examiner1,Examiner2,Requirements,Start,Duration,DurationPerStudentPerGroup,BreakDurationPerGroup);
    }
  }

  /**This method creates or overwrites the xml files in USER.HOME\.scheduler\data\ with the current exam schedule.*/
  public void saveXMLs()
  {
    xmlWriter XMLwriter = new xmlWriter();
    node ClassesRootNode = new node("Classes", 1);
    for(int i = 0; i < Classes.size(); i++)
      ClassesRootNode.addChild("Class", Classes.get(i).getName());
    XMLwriter.write(System.getProperty("user.home") + "\\.scheduler\\data\\Classes.xml", ClassesRootNode);
    System.out.println("ScheduleModelManager:    " + ANSI_CYAN + "Updating " + System.getProperty("user.home") + "\\.scheduler\\data\\Classes.xml" + ANSI_RESET);
    node StudentsRootNode = new node("Students", 1);
    for(int i = 0; i < Students.size(); i++)
    {
      StudentsRootNode.addChild("Student");
      StudentsRootNode.getChildren()[i].addChild("Name", Students.get(i).getName());
      StudentsRootNode.getChildren()[i].addChild("VIAID", String.valueOf(Students.get(i).getVIAID()));
      StudentsRootNode.getChildren()[i].addChild("Participating", Students.get(i).getParticipating());
      StudentsRootNode.getChildren()[i].addChild("Group", String.valueOf(Students.get(i).getGroup()));
    }
    XMLwriter.write(System.getProperty("user.home") + "\\.scheduler\\data\\Students.xml", StudentsRootNode);
    System.out.println("ScheduleModelManager:    " + ANSI_CYAN + "Updating " + System.getProperty("user.home") + "\\.scheduler\\data\\Students.xml" + ANSI_RESET);
    node ExaminersRootNode = new node("Examiners", 1);
    for(int i = 0; i < Examiners.size(); i++)
    {
      ExaminersRootNode.addChild("Examiner");
      ExaminersRootNode.getChildren()[i].addChild("Name", Examiners.get(i).getName());
      for(int j = 0; j < Examiners.get(i).getUnavailabilities().length; j++)
      {
        ExaminersRootNode.getChildren()[i].addChild("Unavailability");
        ExaminersRootNode.getChildren()[i].getChildrenWithName("Unavailability")[j].addChild("Start", Examiners.get(i).getUnavailabilities()[j][0].toString());
        ExaminersRootNode.getChildren()[i].getChildrenWithName("Unavailability")[j].addChild("End", Examiners.get(i).getUnavailabilities()[j][1].toString());
      }
    }
    XMLwriter.write(System.getProperty("user.home") + "\\.scheduler\\data\\Examiners.xml", ExaminersRootNode);
    System.out.println("ScheduleModelManager:    " + ANSI_CYAN + "Updating " + System.getProperty("user.home") + "\\.scheduler\\data\\Examiners.xml" + ANSI_RESET);
    node RoomsRootNode = new node("ClassRooms", 1);
    for(int i = 0; i < Rooms.size(); i++)
    {
      RoomsRootNode.addChild("Room");
      RoomsRootNode.getChildren()[i].addChild("Name", Rooms.get(i).getName());
      RoomsRootNode.getChildren()[i].addChild("Size", String.valueOf(Rooms.get(i).getSize()));
      RoomsRootNode.getChildren()[i].addChild("Equipments");
      for(int j = 0; j < Rooms.get(i).getEquipments().length; j++)
      {
        RoomsRootNode.getChildren()[i].getChildrenWithName("Equipments")[0].addChild("Equipment", Rooms.get(i).getEquipments()[j]);
      }
    }
    XMLwriter.write(System.getProperty("user.home") + "\\.scheduler\\data\\Rooms.xml", RoomsRootNode);
    System.out.println("ScheduleModelManager:    " + ANSI_CYAN + "Updating " + System.getProperty("user.home") + "\\.scheduler\\data\\Rooms.xml" + ANSI_RESET);
    node ValidExamPeriodRootNode = new node("ExamPeriod", 1);
    ValidExamPeriodRootNode.addChild("StartTime", ValidExamPeriod.getStartTime().toString());
    ValidExamPeriodRootNode.addChild("EndTime", ValidExamPeriod.getEndTime().toString());
    ValidExamPeriodRootNode.addChild("BreakStartTime", ValidExamPeriod.getBreakStartTime().toString());
    ValidExamPeriodRootNode.addChild("BreakEndTime", ValidExamPeriod.getBreakEndTime().toString());
    ValidExamPeriodRootNode.addChild("BreakDuration", String.valueOf(ValidExamPeriod.getBreakDuration()));
    ValidExamPeriodRootNode.addChild("DefaultDurationPerStudent", String.valueOf(ValidExamPeriod.getDefaultDurationPerStudent()));
    ValidExamPeriodRootNode.addChild("DefaultDurationPerStudentPerGroup", String.valueOf(ValidExamPeriod.getDefaultDurationPerStudentPerGroup()));
    ValidExamPeriodRootNode.addChild("DefaultBreakDurationPerStudent", String.valueOf(ValidExamPeriod.getDefaultBreakDurationPerStudent()));
    ValidExamPeriodRootNode.addChild("DefaultBreakDurationPerGroup", String.valueOf(ValidExamPeriod.getDefaultBreakDurationPerGroup()));
    ValidExamPeriodRootNode.addChild("ValidDates");
    for(int j = 0; j < ValidExamPeriod.getValidDates().length; j++)
    {
      ValidExamPeriodRootNode.getChildrenWithName("ValidDates")[0].addChild("Date", ValidExamPeriod.getValidDates()[j].toDateOnly());
    }
    XMLwriter.write(System.getProperty("user.home") + "\\.scheduler\\data\\ValidExamPeriod.xml", ValidExamPeriodRootNode);
    System.out.println("ScheduleModelManager:    " + ANSI_CYAN + "Updating " + System.getProperty("user.home") + "\\.scheduler\\data\\ValidExamPeriod.xml" + ANSI_RESET);
    node ExamsRootNode = new node("Exams", 1);
    for(int i = 0; i < Exams.size(); i++)
    {
      switch (Exams.get(i).getClass().getName().substring(14, Exams.get(i).getClass().getName().length() - 4))
      {
        case "Written":
          ExamsRootNode.addChild("WrittenExam");
          ExamsRootNode.getChildren()[i].addChild("Name",Exams.get(i).getName());
          ExamsRootNode.getChildren()[i].addChild("Place",Exams.get(i).getPlace().getName());
          ExamsRootNode.getChildren()[i].addChild("Participating",Exams.get(i).getParticipatingClass().getName());
          ExamsRootNode.getChildren()[i].addChild("Examiner1",Exams.get(i).getExaminer().getName());
          ExamsRootNode.getChildren()[i].addChild("Duration",String.valueOf(Exams.get(i).getDuration()));
          ExamsRootNode.getChildren()[i].addChild("Start",Exams.get(i).getStartDate().toString());
          ExamsRootNode.getChildren()[i].addChild("Requirements");
          for(int j = 0; j < Exams.get(i).getRequirements().length; j++)
          {
            ExamsRootNode.getChildren()[i].getChildrenWithName("Requirements")[0].addChild("Requirement",Exams.get(i).getRequirements()[j]);
          }
          break;
        case "Oral":
          ExamsRootNode.addChild("OralExam");
          ExamsRootNode.getChildren()[i].addChild("Name",Exams.get(i).getName());
          ExamsRootNode.getChildren()[i].addChild("Place",Exams.get(i).getPlace().getName());
          ExamsRootNode.getChildren()[i].addChild("Participating",Exams.get(i).getParticipatingClass().getName());
          ExamsRootNode.getChildren()[i].addChild("Examiner1",Exams.get(i).getExaminer().getName());
          ExamsRootNode.getChildren()[i].addChild("Examiner2",((OralExam) Exams.get(i)).getExaminer2().getName());
          ExamsRootNode.getChildren()[i].addChild("Duration",String.valueOf(((OralExam) Exams.get(i)).getRedDuration()));
          ExamsRootNode.getChildren()[i].addChild("BreakDurationPerStudent",String.valueOf(((OralExam) Exams.get(i)).getRedBreakDurationPerStudent()));
          ExamsRootNode.getChildren()[i].addChild("DurationPerStudent",String.valueOf(((OralExam) Exams.get(i)).getRedDurationPerStudent()));
          ExamsRootNode.getChildren()[i].addChild("Start",Exams.get(i).getStartDate().toString());
          ExamsRootNode.getChildren()[i].addChild("Requirements");
          for(int j = 0; j < Exams.get(i).getRequirements().length; j++)
          {
            ExamsRootNode.getChildren()[i].getChildrenWithName("Requirements")[0].addChild("Requirement",Exams.get(i).getRequirements()[j]);
          }
          break;
        case "Project":
          ExamsRootNode.addChild("ProjectExam");
          ExamsRootNode.getChildren()[i].addChild("Name",Exams.get(i).getName());
          ExamsRootNode.getChildren()[i].addChild("Place",Exams.get(i).getPlace().getName());
          ExamsRootNode.getChildren()[i].addChild("Participating",Exams.get(i).getParticipatingClass().getName());
          ExamsRootNode.getChildren()[i].addChild("Examiner1",Exams.get(i).getExaminer().getName());
          ExamsRootNode.getChildren()[i].addChild("Examiner2",((ProjectExam) Exams.get(i)).getExaminer2().getName());
          ExamsRootNode.getChildren()[i].addChild("Duration",String.valueOf(((ProjectExam) Exams.get(i)).getRedDuration()));
          ExamsRootNode.getChildren()[i].addChild("BreakDurationPerGroup",String.valueOf(((ProjectExam) Exams.get(i)).getRedBreakDurationPerGroup()));
          ExamsRootNode.getChildren()[i].addChild("DurationPerStudentPerGroup",String.valueOf(((ProjectExam) Exams.get(i)).getRedDurationPerStudentPerGroup()));
          ExamsRootNode.getChildren()[i].addChild("Start",Exams.get(i).getStartDate().toString());
          ExamsRootNode.getChildren()[i].addChild("Requirements");
          for(int j = 0; j < Exams.get(i).getRequirements().length; j++)
          {
            ExamsRootNode.getChildren()[i].getChildrenWithName("Requirements")[0].addChild("Requirement",Exams.get(i).getRequirements()[j]);
          }
          break;
      }
    }
    XMLwriter.write(System.getProperty("user.home") + "\\.scheduler\\data\\Exams.xml", ExamsRootNode);
    System.out.println("ScheduleModelManager:    " + ANSI_CYAN + "Updating " + System.getProperty("user.home") + "\\.scheduler\\data\\Exams.xml" + ANSI_RESET);
  }

  /**This method zips the xml files in USER.HOME\.scheduler\data\ and saves it in the location given by the argument.*/
  public void zipXMLs(String path)
  {
    FileInputStream fileInputStream;
    String temp = System.getProperty("user.home") + "\\.scheduler\\data\\";
    String[] files = {temp+"Classes.xml",temp+"Students.xml",temp+"Examiners.xml",temp+"Rooms.xml",temp+"ValidExamPeriod.xml",temp+"Exams.xml"};
    try
    {
      FileOutputStream fileOutputStream = new FileOutputStream(path);
      ZipOutputStream zipOutputStream = new ZipOutputStream(new BufferedOutputStream(fileOutputStream));
      for(int i = 0; i < files.length; i++)
      {
        File input = new File(files[i]);
        fileInputStream = new FileInputStream(input);
        ZipEntry zipEntry = new ZipEntry(input.getName());
        System.out.println("ScheduleModelManager:    " + ANSI_CYAN + "Compressing " + zipEntry.getName() + " to " + path + ANSI_RESET);
        zipOutputStream.putNextEntry(zipEntry);
        byte[] tmp = new byte[4*1024];
        int size;
        while((size = fileInputStream.read(tmp)) != -1)
        {
          zipOutputStream.write(tmp, 0, size);
        }
        zipOutputStream.flush();
        fileInputStream.close();
      }
      zipOutputStream.close();
      fileOutputStream.close();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  /**This method unzips the a zip file given by the argument to USER.HOME\.scheduler\data\.*/
  public void unZipSCH(String path)
  {
    File workDir = new File(System.getProperty("user.home") + "\\.scheduler");
    if (!workDir.exists())
    {
      System.out.println("ScheduleModelManager:    " + ANSI_CYAN + "Creating " + System.getProperty("user.home") + "\\.scheduler directory" + ANSI_RESET);
      workDir.mkdir();
    }
    File dataDir = new File(System.getProperty("user.home") + "\\.scheduler\\data");
    if (!dataDir.exists())
    {
      System.out.println("ScheduleModelManager:    " + ANSI_CYAN + "Creating " + System.getProperty("user.home") + "\\.scheduler\\data directory" + ANSI_RESET);
      dataDir.mkdir();
    }

    try
    {
      ZipEntry zipEntry;
      FileInputStream fileInputStream = new FileInputStream(path);
      ZipInputStream zipInputStream = new ZipInputStream(new BufferedInputStream(fileInputStream));
      while ((zipEntry = zipInputStream.getNextEntry()) != null)
      {
        byte[] tmp = new byte[4 * 1024];
        FileOutputStream fileOutputStream;
        String opFilePath = System.getProperty("user.home") + "\\.scheduler\\data\\" + zipEntry.getName();
        System.out.println("ScheduleModelManager:    " + ANSI_CYAN + "Extracting " + zipEntry.getName() + " to " + System.getProperty("user.home") + "\\.scheduler\\data" + ANSI_RESET);
        fileOutputStream = new FileOutputStream(opFilePath);
        int size = 0;
        while ((size = zipInputStream.read(tmp)) != -1)
        {
          fileOutputStream.write(tmp, 0, size);
        }
        fileOutputStream.flush();
        fileOutputStream.close();
      }
      zipInputStream.close();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  /**This method creates or overwrites the xml file in USER.HOME\.scheduler\web\_Data\Exams.xml with the current exam schedule.*/
  public void updateWebXML()
  {
    File workDir = new File(System.getProperty("user.home") + "\\.scheduler");
    if (!workDir.exists())
    {
      System.out.println("ScheduleModelManager:    " + ANSI_CYAN + "Creating " + System.getProperty("user.home") + "\\.scheduler directory" + ANSI_RESET);
      workDir.mkdir();
    }
    File webDir = new File(System.getProperty("user.home") + "\\.scheduler\\web");
    if (!webDir.exists())
    {
      System.out.println("ScheduleModelManager:    " + ANSI_CYAN + "Creating " + System.getProperty("user.home") + "\\.scheduler\\web directory" + ANSI_RESET);
      webDir.mkdir();
    }
    File webDataDir = new File(System.getProperty("user.home") + "\\.scheduler\\web\\_Data");
    if (!webDataDir.exists())
    {
      System.out.println("ScheduleModelManager:    " + ANSI_CYAN + "Creating " + System.getProperty("user.home") + "\\.scheduler\\web\\_Data directory" + ANSI_RESET);
      webDataDir.mkdir();
    }
    
    node WebRootNode = new node("Exams", 1);
    for(int i = 0; i < Exams.size(); i++)
    {
      switch (Exams.get(i).getClass().getName().substring(14, Exams.get(i).getClass().getName().length() - 4))
      {
        case "Written":
          WebRootNode.addChild("WrittenExam");
          WebRootNode.getChildren()[i].addChild("Name",Exams.get(i).getName());
          WebRootNode.getChildren()[i].addChild("Place",Exams.get(i).getPlace().getName());
          WebRootNode.getChildren()[i].addChild("Participating",Exams.get(i).getParticipatingClass().getName());
          WebRootNode.getChildren()[i].addChild("Examiner1",Exams.get(i).getExaminer().getName());
          WebRootNode.getChildren()[i].addChild("Duration",String.valueOf(Exams.get(i).getDuration()));
          WebRootNode.getChildren()[i].addChild("Start",Exams.get(i).getStartDate().toString());
          WebRootNode.getChildren()[i].addChild("End",Exams.get(i).getEnd().toString());
          WebRootNode.getChildren()[i].addChild("Type","Written");
          break;
        case "Oral":
          WebRootNode.addChild("OralExam");
          WebRootNode.getChildren()[i].addChild("Name",Exams.get(i).getName());
          WebRootNode.getChildren()[i].addChild("Place",Exams.get(i).getPlace().getName());
          WebRootNode.getChildren()[i].addChild("Participating",Exams.get(i).getParticipatingClass().getName());
          WebRootNode.getChildren()[i].addChild("Examiner1",Exams.get(i).getExaminer().getName());
          WebRootNode.getChildren()[i].addChild("Examiner2",((OralExam) Exams.get(i)).getExaminer2().getName());
          WebRootNode.getChildren()[i].addChild("Duration",String.valueOf(Exams.get(i).getDuration()));
          WebRootNode.getChildren()[i].addChild("BreakDurationPerStudent",String.valueOf(((OralExam) Exams.get(i)).getBreakDurationPerStudent()));
          WebRootNode.getChildren()[i].addChild("DurationPerStudent",String.valueOf(((OralExam) Exams.get(i)).getDurationPerStudent()));
          WebRootNode.getChildren()[i].addChild("Start",Exams.get(i).getStartDate().toString());
          WebRootNode.getChildren()[i].addChild("End",((OralExam)Exams.get(i)).getEnd().toString());
          WebRootNode.getChildren()[i].addChild("Type","Oral");
          WebRootNode.getChildren()[i].addChild("Detailed",((OralExam)Exams.get(i)).getDetailedSchedule());
          break;
        case "Project":
          WebRootNode.addChild("ProjectExam");
          WebRootNode.getChildren()[i].addChild("Name",Exams.get(i).getName());
          WebRootNode.getChildren()[i].addChild("Place",Exams.get(i).getPlace().getName());
          WebRootNode.getChildren()[i].addChild("Participating",Exams.get(i).getParticipatingClass().getName());
          WebRootNode.getChildren()[i].addChild("Examiner1",Exams.get(i).getExaminer().getName());
          WebRootNode.getChildren()[i].addChild("Examiner2",((ProjectExam) Exams.get(i)).getExaminer2().getName());
          WebRootNode.getChildren()[i].addChild("Duration",String.valueOf(Exams.get(i).getDuration()));
          WebRootNode.getChildren()[i].addChild("BreakDurationPerGroup",String.valueOf(((ProjectExam) Exams.get(i)).getBreakDurationPerGroup()));
          WebRootNode.getChildren()[i].addChild("DurationPerStudentPerGroup",String.valueOf(((ProjectExam) Exams.get(i)).getDurationPerStudentPerGroup()));
          WebRootNode.getChildren()[i].addChild("Start",Exams.get(i).getStartDate().toString());
          WebRootNode.getChildren()[i].addChild("End",((ProjectExam)Exams.get(i)).getEnd().toString());
          WebRootNode.getChildren()[i].addChild("Type","Project");
          WebRootNode.getChildren()[i].addChild("Detailed",((ProjectExam)Exams.get(i)).getDetailedSchedule());
          break;
      }
      WebRootNode.getChildren()[i].addChild("Web");
      WebRootNode.getChildren()[i].getChildrenWithName("Web")[0].addChild("StartDay",Exams.get(i).getStartDate().getWeekOfYear() + Exams.get(i).getStartDate().toXmlDay());
      Date temp;
      if (Exams.get(i).getStartDate().getMinute() < 30)
        temp = new Date(Exams.get(i).getStartDate().getHour(),0);
      else
        temp = new Date(Exams.get(i).getStartDate().getHour(),30);
      WebRootNode.getChildren()[i].getChildrenWithName("Web")[0].addChild("StartDay",temp.toXmlTime());
      WebRootNode.getChildren()[i].getChildrenWithName("Web")[0].addChild("MidDays");
      for (int j = 1; j < Exams.get(i).getEnd().toDays() - Exams.get(i).getStartDate().toDays(); j++)
      {
        temp = Exams.get(i).getStartDate().addMinutes(60*24*j);
        if (ValidExamPeriod.isValid(temp))
          WebRootNode.getChildren()[i].getChildrenWithName("Web")[0].getChildrenWithName("MidDays")[0].addChild("MidDay",temp.getWeekOfYear() + temp.toXmlDay());
      }
      WebRootNode.getChildren()[i].getChildrenWithName("Web")[0].addChild("EndDay",Exams.get(i).getEnd().getWeekOfYear() + Exams.get(i).getEnd().toXmlDay());
      temp = Exams.get(i).getEnd().copy();
      if (Exams.get(i).getEnd().getMinute() < 30 && Exams.get(i).getEnd().getMinute() > 0)
        temp = new Date(Exams.get(i).getEnd().getHour(),30);
      else if (Exams.get(i).getEnd().getMinute() > 30)
        temp = new Date(Exams.get(i).getEnd().getHour() + 1,0);
      WebRootNode.getChildren()[i].getChildrenWithName("Web")[0].addChild("EndDay",temp.toXmlTime());
    }
    WebRootNode.addChild("Web");
    WebRootNode.getChildrenWithName("Web")[0].addChild("Classes");
    for(int i = 0; i < Classes.size(); i++)
      WebRootNode.getChildrenWithName("Web")[0].getChildren()[0].addChild("Class", Classes.get(i).getName());
    WebRootNode.getChildrenWithName("Web")[0].addChild("Weeks");
    String week = "Week ";
    week += ValidExamPeriod.getStartDate().getWeekOfYear();
    week += "~(";
    week += ValidExamPeriod.getStartDate().toMonth();
    week += " " + ValidExamPeriod.getStartDate().getDay() + " - ";
    week += ValidExamPeriod.getStartDate().stepToNextWeekDay(5).toMonth();
    week += " " + ValidExamPeriod.getStartDate().stepToNextWeekDay(5).getDay() + ")";
    WebRootNode.getChildrenWithName("Web")[0].getChildren()[1].addChild("Week", week);
    for (int i = 1; i < (ValidExamPeriod.getEndDate().toDays() - ValidExamPeriod.getStartDate().toDays() + ValidExamPeriod.getStartDate().getDayOfWeek()) / 7 + 1; i++)
    {
      week = "Week ";
      week += ValidExamPeriod.getStartDate().addMinutes(60*24).stepToNextWeekDay(1).addMinutes(60*24*7*(i-1)).getWeekOfYear();
      week += "~(";
      week += ValidExamPeriod.getStartDate().addMinutes(60*24).stepToNextWeekDay(1).addMinutes(60*24*7*(i-1)).toMonth();
      week += " " + ValidExamPeriod.getStartDate().addMinutes(60*24).stepToNextWeekDay(1).addMinutes(60*24*7*(i-1)).getDay() + " - ";
      week += ValidExamPeriod.getStartDate().addMinutes(60*24*7*i).stepToNextWeekDay(5).toMonth();
      week += " " + ValidExamPeriod.getStartDate().addMinutes(60*24*7*i).stepToNextWeekDay(5).getDay() + ")";
      WebRootNode.getChildrenWithName("Web")[0].getChildren()[1].addChild("Week", week);
    }
    WebRootNode.getChildrenWithName("Web")[0].addChild("ExamPeriod");
    int temp;
    temp = ValidExamPeriod.getStartTime().getHour() * 2;
    if (ValidExamPeriod.getStartTime().getMinute() <= 30 && ValidExamPeriod.getStartTime().getMinute() > 0)
      temp += 1;
    else if (ValidExamPeriod.getStartTime().getMinute() > 30)
      temp += 1;
    WebRootNode.getChildrenWithName("Web")[0].getChildren()[2].addChild("StartSpec", String.valueOf(temp));
    temp = ValidExamPeriod.getEndTime().getHour() * 2;
    if (ValidExamPeriod.getEndTime().getMinute() <= 30 && ValidExamPeriod.getEndTime().getMinute() > 0)
      temp += 1;
    else if (ValidExamPeriod.getEndTime().getMinute() > 30)
      temp += 1;
    WebRootNode.getChildrenWithName("Web")[0].getChildren()[2].addChild("EndSpec", String.valueOf(temp));
    xmlWriter XMLwriter = new xmlWriter();
    XMLwriter.write(System.getProperty("user.home") + "\\.scheduler\\web\\_Data\\Exams.xml", WebRootNode);
    System.out.println("ScheduleModelManager:    " + ANSI_CYAN + "Updating " + System.getProperty("user.home") + "\\.scheduler\\web\\_Data\\Exams.xml" + ANSI_RESET);
  }

  //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------



}