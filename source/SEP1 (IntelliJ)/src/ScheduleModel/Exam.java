package ScheduleModel;

import java.util.ArrayList;

/**This class describes an Exam, with the properties of its:</br>
 * &nbsp;&nbsp;&nbsp;&nbsp;	• Name</br>
 * &nbsp;&nbsp;&nbsp;&nbsp;	• Place (where the exam takes place)</br>
 * &nbsp;&nbsp;&nbsp;&nbsp;	• Participating Class<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;	• Examiner<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;	• Requirements (for the classroom)<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;	• Start Date<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;	• Duration (in minutes)*/
public class Exam
{
  /**Exam's Name*/
  private String Name;
  /**Exam's Place (a pointer of a Room)*/
  private Room Place;
  /**Exam's Participating Class (a pointer of a Class)*/
  private Class Participating;
  /**Exam's Examiner (a pointer of an Examiner)*/
  private Examiner Examiner1;
  /**ArrayList of Requirements needed for this Exam*/
  private ArrayList<String> Requirements;
  /**Exam's Start Date*/
  private Date Start;
  /**Exam's Duration (in minutes)*/
  private int Duration;
  /**A pointer to the ValidExamPeriod (for checking conflicts and validity)*/
  private ExamPeriod ValidExamPeriod;
  private String Conflicts;

  /**The constructor checks for duplicates in the inputted Requirements in the argument and eliminates the duplicates, and then initializes all instance variables.*/
  public Exam(String Name, Room Place, Class Participating, Examiner Examiner1, String[] Requirements, Date Start, ExamPeriod ValidExamPeriod, int Duration)
  {
    this.Name = Name;
    this.Place = Place;
    this.Participating = Participating;
    this.Examiner1 = Examiner1;
    this.Requirements = new ArrayList<String>();
    for (int i = 0; i < Requirements.length; i++)
    {
      addRequirement(Requirements[i]);
    }
    this.Start = Start;
    this.Duration = Duration;
    this.ValidExamPeriod = ValidExamPeriod;
    this.Conflicts = "";
  }

  /**This constructor only gives a user defined value to the Name<br>
   * (this is used for testing and comparing purposes, since the Name is the unique identifier of an exam)*/
  public Exam(String Name)
  {
    this.Name = Name;
  }

  /**This method returns true if the Place (Room) has all Requirements needed for this Exam (all Requirements).*/
  public boolean isEquipped()
  {
    boolean temp = true;
    for (int i = 0; i < Requirements.size(); i++)
    {
      if (!Place.hasEquipment(Requirements.get(i)))
        temp = false;
    }
    return temp;
  }

  /**This method returns (first calculates) the End Date.*/
  public Date getEnd()
  {
    Date end = Start.addMinutes(Duration);
    int dayStepper = -1;
    do
    {
      dayStepper++;
      if (!ValidExamPeriod.isValid(Start.addMinutes(dayStepper * 60 * 24)))
      {
        end = end.addMinutes(60 * 24);
      }
      else
      {
        if (Start.mergeDate(ValidExamPeriod.getBreakEndTime()).addMinutes(dayStepper * 60 * 24).isBefore(end) && (Start.isBefore(Start.mergeDate(ValidExamPeriod.getBreakStartTime()).addMinutes(dayStepper * 60 * 24)) || Start.equals(Start.mergeDate(ValidExamPeriod.getBreakStartTime()).addMinutes(dayStepper * 60 * 24))))
          end = end.addMinutes(ValidExamPeriod.getBreakDuration());
        if (Start.mergeDate(ValidExamPeriod.getEndTime()).addMinutes(dayStepper * 60 * 24).isBefore(end))
          end = end.addMinutes((24 - ValidExamPeriod.getEndTime().getHour() + ValidExamPeriod.getStartTime().getHour()) * 60 - ValidExamPeriod.getEndTime().getMinute() + ValidExamPeriod.getStartTime().getMinute());
      }
    }
    while (dayStepper != end.toDays() - Start.toDays() && dayStepper < 20);
    if (dayStepper == 20)
      end = new Date(1,1,1,0,0);
    return end;
  }

  /**This setter changes the Duration of this Exam after the initialization.</br>
   * !ONLY USED BY SUBCLASSES!*/
  public void setDuration(int Duration)
  {
    this.Duration = Duration;
  }

  /**This getter returns the Name of this Exam.*/
  public String getName()
  {
    return Name;
  }

  /**This getter returns the Place (Room) of this Exam.*/
  public Room getPlace()
  {
    return Place;
  }

  /**This getter returns the Participating Class of this Exam.*/
  public Class getParticipatingClass()
  {
    return Participating;
  }

  /**This getter returns the Examiner of this Exam.*/
  public Examiner getExaminer()
  {
    return Examiner1;
  }

  /**This method adds a requirement to the Requirements of this exam, in an abc ordered fashion (if not already present).*/
  public void addRequirement(String requirement)
  {
    if (!this.Requirements.contains(requirement))
    {
      if (Requirements.size() == 0)
      {
        this.Requirements.add(requirement);
      }
      else
      {
        if (Requirements.get(Requirements.size() - 1).compareTo(requirement) < 0)
          this.Requirements.add(requirement);
        for (int i = 0; i < Requirements.size(); i++)
        {
          if (requirement.compareTo(Requirements.get(i)) < 0)
          {
            this.Requirements.add(i, requirement);
            break;
          }
        }
      }
    }
  }

  /**This method generates and returns the Conflicts between this and the inputted Exams in the argument.*/
  public void generateConflicts(Exam[] otherExams)
  {
    String Conflicts = "";
    if (!ValidExamPeriod.isValid(Start))
      Conflicts += "Start Date not valid, ";
    if (!ValidExamPeriod.isValidTime(Start))
      Conflicts += "Start Time not valid, ";
    if (!ValidExamPeriod.isValid(getEnd()))
      Conflicts += "End Date not valid, ";
    for(int i = 0; i < otherExams.length; i++)
    {
      if (!otherExams[i].getName().equals(Name))
      {
        if (otherExams[i].getStartDate().isBetween(Start, getEnd()) || otherExams[i].getEnd().isBetween(Start, getEnd()) || Start.isBetween(otherExams[i].getStartDate(), otherExams[i].getEnd()))
        {
          if (otherExams[i].getParticipatingClass().equals(Participating))
          {
            Conflicts += "class presence also required at " + otherExams[i].getName() + ", ";
          }
          if (otherExams[i].getPlace().equals(Place))
          {
            Conflicts += "classroom occupied by " + otherExams[i].getName() + ", ";
          }

          if (this instanceof WrittenExam && otherExams[i] instanceof WrittenExam)
          {
            if (otherExams[i].getExaminer().equals(Examiner1))
            {
              Conflicts += "examiner presence also required at " + otherExams[i].getName() + ", ";
            }
          }
          else if (this instanceof OralExam && otherExams[i] instanceof WrittenExam)
          {
            if ((otherExams[i].getExaminer().equals(Examiner1) || otherExams[i].getExaminer().equals(((OralExam) this).getExaminer2())))
            {
              Conflicts += "examiner presence also required at " + otherExams[i].getName() + ", ";
            }
          }
          else if (this instanceof ProjectExam && otherExams[i] instanceof WrittenExam)
          {
            if ((otherExams[i].getExaminer().equals(Examiner1) || otherExams[i].getExaminer().equals(((ProjectExam) this).getExaminer2())))
            {
              Conflicts += "examiner presence also required at " + otherExams[i].getName() + ", ";
            }
          }
          else if (this instanceof WrittenExam && otherExams[i] instanceof OralExam)
          {
            if ((otherExams[i].getExaminer().equals(Examiner1) || ((OralExam)otherExams[i]).getExaminer2().equals(Examiner1)))
            {
              Conflicts += "examiner presence also required at " + otherExams[i].getName() + ", ";
            }
          }
          else if (this instanceof WrittenExam && otherExams[i] instanceof ProjectExam)
          {
            if ((otherExams[i].getExaminer().equals(Examiner1) || ((ProjectExam)otherExams[i]).getExaminer2().equals(Examiner1)))
            {
              Conflicts += "examiner presence also required at " + otherExams[i].getName() + ", ";
            }
          }
          else if (this instanceof OralExam && otherExams[i] instanceof OralExam)
          {
            if ((otherExams[i].getExaminer().equals(Examiner1) || otherExams[i].getExaminer().equals(((OralExam) this).getExaminer2())) || ((OralExam)otherExams[i]).getExaminer2().equals(Examiner1) || ((OralExam)otherExams[i]).getExaminer2().equals(((OralExam) this).getExaminer2()))
            {
              Conflicts += "examiner presence also required at " + otherExams[i].getName() + ", ";
            }
          }
          else if (this instanceof ProjectExam && otherExams[i] instanceof ProjectExam)
          {
            if ((otherExams[i].getExaminer().equals(Examiner1) || otherExams[i].getExaminer().equals(((ProjectExam) this).getExaminer2())) || ((ProjectExam)otherExams[i]).getExaminer2().equals(Examiner1) || ((ProjectExam)otherExams[i]).getExaminer2().equals(((ProjectExam) this).getExaminer2()))
            {
              Conflicts += "examiner presence also required at " + otherExams[i].getName() + ", ";
            }
          }
          else if (this instanceof OralExam && otherExams[i] instanceof ProjectExam)
          {
            if ((otherExams[i].getExaminer().equals(Examiner1) || otherExams[i].getExaminer().equals(((OralExam) this).getExaminer2())) || ((ProjectExam)otherExams[i]).getExaminer2().equals(Examiner1) || ((ProjectExam)otherExams[i]).getExaminer2().equals(((OralExam) this).getExaminer2()))
            {
              Conflicts += "examiner presence also required at " + otherExams[i].getName() + ", ";
            }
          }
          else if (this instanceof ProjectExam && otherExams[i] instanceof OralExam)
          {
            if ((otherExams[i].getExaminer().equals(Examiner1) || otherExams[i].getExaminer().equals(((ProjectExam) this).getExaminer2())) || ((OralExam)otherExams[i]).getExaminer2().equals(Examiner1) || ((OralExam)otherExams[i]).getExaminer2().equals(((ProjectExam) this).getExaminer2()))
            {
              Conflicts += "examiner presence also required at " + otherExams[i].getName() + ", ";
            }
          }
        }
      }
    }
    if (this instanceof WrittenExam)
    {
      if (Place.getSize() <= Participating.getNumberOfStudents())
      {
        Conflicts += "insufficient seating capacity, ";
      }
    }
    boolean req = false;
    for (int i = 0; i < Requirements.size(); i++)
    {
      if (!Place.hasEquipment(Requirements.get(i)))
      {
        req = true;
      }
    }
    if(req)
    {
      Conflicts += "classroom requirements not met, ";
    }
    if (Examiner1.isUnavailable(Start, getEnd()))
    {
      Conflicts += "examiner1 is unavailable, ";
    }
    if (this instanceof OralExam)
    {
      if (((OralExam)this).getExaminer2().isUnavailable(Start, getEnd()))
      {
        Conflicts += "examiner2 is unavailable, ";
      }
    }
    if (this instanceof ProjectExam)
    {
      if (((ProjectExam)this).getExaminer2().isUnavailable(Start, getEnd()))
      {
        Conflicts += "examiner2 is unavailable, ";
      }
    }
    if (Conflicts.length() >= 2)
    {
      Conflicts = Conflicts.substring(0, Conflicts.length() - 2);
    }
    this.Conflicts = Conflicts;
  }
  public String getConflicts()
  {
    return Conflicts;
  }
  
  /**This getter returns all of the Requirements of this Exam.*/
  public String[] getRequirements()
  {
    String[] temp = new String[Requirements.size()];
    temp = Requirements.toArray(temp);
    return temp;
  }

  /**This getter returns the Start Date of this Exam.*/
  public Date getStartDate()
  {
    return Start;
  }

  /**This getter returns the Duration of this Exam.*/
  public int getDuration()
  {
    return Duration;
  }

  public ExamPeriod getValidExamPeriod()
  {
    return ValidExamPeriod;
  }

  /**This method overrides the default equals method by returning true if the inputted Exam in the argument has the same unique Name (Exam.Name) as this' (since the Name is the unique identifier of an Exam).*/
  public boolean equals(Object obj)
  {
    if(!(obj instanceof Exam))
      return false;
    return (((Exam) obj).getName().equals(Name));
  }
}