package ScheduleModel;

/**This class describes a ProjectExam, by extending the Exam class with its specific methods and attributes, like:</br>
 * &nbsp;&nbsp;&nbsp;&nbsp;	• a second Examiner<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;	• DurationPerStudentPerGroup (in minutes)<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;	• BreakDurationPerGroup (in minutes)*/
public class ProjectExam extends Exam
{
  /**ProjectExam's second Examiner (a pointer of Examiner)*/
  private Examiner Examiner2;
  /**This Duration is the same as the superclass' and its only present for comfort reasons*/
  private int Duration;
  /**ProjectExam's DurationPerStudentPerGroup (in minutes)*/
  private int DurationPerStudentPerGroup;
  /**ProjectExam's BreakDurationPerGroup per student (in minutes)*/
  private int BreakDurationPerGroup;
  /** This value is true if the Duration attribute in the superclass is redundant, meaning that the user did not input the data, but the program calculated it. */
  private boolean RedundantDuration;
  /** This value is true if the DurationPerStudent attribute is redundant, meaning that the user did not input the data, but the program calculated it. */
  private boolean RedundantDurPerStudentPerGroup;
  /** This value is true if the BreakDurationPerStudent attribute is redundant, meaning that the user did not input the data, but the program calculated it. */
  private boolean RedundantBreakDurPerGroup;

  /**The constructor calls the superclass' (Exam's) constructor and
   * &nbsp;&nbsp;&nbsp;&nbsp;	- prioritizes between the possible redundant information in the case of Duration, DurationPerStudentPerGroup, and BreakDurationPerGroup<br/>
   * &nbsp;&nbsp;&nbsp;&nbsp;	- complements the possible missing information in the case of Duration, DurationPerStudentPerGroup, and BreakDurationPerGroup<br/>
   * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(replacing the needed information from the ExamPeriod's Default values)<br/>
   * &nbsp;&nbsp;&nbsp;&nbsp;	- and initializes the ProjectExam specific instance variables*/
  public ProjectExam(String Name, Room Place, Class Participating, Examiner Examiner1, Examiner Examiner2, String[] Requirements, Date Start, ExamPeriod ValidExamPeriod, int Duration, int DurationPerStudentPerGroup, int BreakDurationPerGroup)
  {
    super(Name, Place, Participating, Examiner1, Requirements, Start, ValidExamPeriod, Duration);
    this.Examiner2 = Examiner2;

    RedundantDuration = true;
    RedundantDurPerStudentPerGroup = true;
    RedundantBreakDurPerGroup = true;

    if(Duration > 0 && DurationPerStudentPerGroup > 0 && BreakDurationPerGroup > 0)
    {
      Duration = 0;
    }

    if(Duration <= 0 && DurationPerStudentPerGroup <= 0 && BreakDurationPerGroup <= 0)
    {
      RedundantDurPerStudentPerGroup = false;
      RedundantBreakDurPerGroup = false;
      DurationPerStudentPerGroup = ValidExamPeriod.getDefaultDurationPerStudentPerGroup();
      BreakDurationPerGroup = ValidExamPeriod.getDefaultBreakDurationPerGroup();
    }

    if(Duration > 0 && DurationPerStudentPerGroup <= 0 && BreakDurationPerGroup <= 0)
    {
      RedundantDuration = false;
      RedundantBreakDurPerGroup = false;
      BreakDurationPerGroup = ValidExamPeriod.getDefaultBreakDurationPerGroup();
    }

    if(Duration <= 0 && DurationPerStudentPerGroup > 0 && BreakDurationPerGroup <= 0)
    {
      RedundantDuration = false;
      RedundantBreakDurPerGroup = false;
      BreakDurationPerGroup = ValidExamPeriod.getDefaultBreakDurationPerGroup();
    }

    if(Duration <= 0 && DurationPerStudentPerGroup <= 0 && BreakDurationPerGroup > 0)
    {
      RedundantDurPerStudentPerGroup = false;
      RedundantBreakDurPerGroup = false;
      DurationPerStudentPerGroup = ValidExamPeriod.getDefaultDurationPerStudentPerGroup();
    }

    if(Duration <= 0 && DurationPerStudentPerGroup > 0 && BreakDurationPerGroup > 0)
    {
      RedundantDurPerStudentPerGroup = false;
      RedundantBreakDurPerGroup = false;
      Duration = DurationPerStudentPerGroup * Participating.getNumberOfStudents() + BreakDurationPerGroup * Participating.getNumberOfGroups();
    }

    if(Duration > 0 && DurationPerStudentPerGroup <= 0 && BreakDurationPerGroup > 0)
    {
      RedundantDuration = false;
      RedundantBreakDurPerGroup = false;
      DurationPerStudentPerGroup = (Duration - (BreakDurationPerGroup * Participating.getNumberOfGroups())) / Participating.getNumberOfStudents();
    }

    if(Duration > 0 && DurationPerStudentPerGroup > 0 && BreakDurationPerGroup <= 0)
    {
      RedundantDuration = false;
      RedundantDurPerStudentPerGroup = false;
      BreakDurationPerGroup = (Duration - (DurationPerStudentPerGroup * Participating.getNumberOfStudents())) / Participating.getNumberOfGroups();
    }

    super.setDuration(Duration);
    this.Duration = Duration;
    this.DurationPerStudentPerGroup = DurationPerStudentPerGroup;
    this.BreakDurationPerGroup = BreakDurationPerGroup;

    //         HOTFIX
    RedundantDuration = true;
    RedundantDurPerStudentPerGroup = false;
    RedundantBreakDurPerGroup = false;
    //         HOTFIX
  }

  /**This getter returns the second Examiner of this ProjectExam.*/
  public Examiner getExaminer2()
  {
    return Examiner2;
  }

  /**This getter returns the Duration if its not redundant, otherwise it returns 0.*/
  int getRedDuration()
  {
    if (RedundantDuration)
      return 0;
    else
      return Duration;
  }

  /**This getter returns the DurationPerStudentPerGroup if its not redundant, otherwise it returns 0.*/
  int getRedDurationPerStudentPerGroup()
  {
    if (RedundantDurPerStudentPerGroup)
      return 0;
    else
      return DurationPerStudentPerGroup;
  }

  /**This getter returns the BreakDurationPerGroup if its not redundant, otherwise it returns 0.*/
  int getRedBreakDurationPerGroup()
  {
    if (RedundantBreakDurPerGroup)
      return 0;
    else
      return BreakDurationPerGroup;
  }

  /**This getter returns the DurationPerStudentPerGroup.*/
  public int getDurationPerStudentPerGroup()
  {
    return DurationPerStudentPerGroup;
  }

  /**This getter returns the BreakDurationPerGroup.*/
  public int getBreakDurationPerGroup()
  {
    return BreakDurationPerGroup;
  }

  /**This method returns the automatically generated, detailed schedule for this ProjectExam.*/
  public String getDetailedSchedule()
  {
    boolean first = true;
    String temp = "";
    Date stepper = getStartDate().copy();
    Date day = new Date(1,1,1,0,0);
    for (int i = 1; i < getParticipatingClass().getNumberOfGroups() + 1; i++)
    {
      if (!day.equals(stepper.chopHM()) && getStartDate().isBefore(stepper.mergeDate(getValidExamPeriod().getBreakStartTime())) && stepper.chopYMD().isBetween(getValidExamPeriod().getBreakStartTime(), getValidExamPeriod().getBreakEndTime()))
      {
        day = stepper.chopHM();
        stepper = stepper.addMinutes(getValidExamPeriod().getBreakDuration() - BreakDurationPerGroup);
      }
      if (getValidExamPeriod().getEndTime().isBefore(stepper.addMinutes(DurationPerStudentPerGroup * getParticipatingClass().getGroup(i).length + BreakDurationPerGroup).chopYMD()))
      {
        first = true;
        stepper = stepper.mergeDate(getValidExamPeriod().getEndTime()).addMinutes((24 - getValidExamPeriod().getEndTime().getHour() + getValidExamPeriod().getStartTime().getHour()) * 60 - getValidExamPeriod().getEndTime().getMinute() + getValidExamPeriod().getStartTime().getMinute());
        if (super.getEnd().equals(new Date(1,1,1,0,0)))
        {
          temp = "Error: exam end out of scope";
          break;
        }
        else
        {
          for (int j = 0; j < 20; j++)
          {
            if (getValidExamPeriod().isValid(stepper))
            {
              break;
            }
            else
            {
              stepper = stepper.addMinutes(24*60);
            }
          }
        }
      }
      if (!first)
        stepper = stepper.addMinutes(BreakDurationPerGroup);
      first = false;
      temp += "Group " + i + "~" + stepper.toString() + " - ";
      stepper = stepper.addMinutes(DurationPerStudentPerGroup * getParticipatingClass().getGroup(i).length);
      temp += stepper.toTimeOnly() + "~";
      for (int j = 0; j < getParticipatingClass().getGroup(i).length; j++)
        temp += getParticipatingClass().getGroup(i)[j].getName() + "~";
      temp += "~";
    }
    return temp;
  }

  public Date getEnd()
  {
    boolean first = true;
    Date stepper = getStartDate().copy();
    Date day = new Date(1,1,1,0,0);
    for (int i = 1; i < getParticipatingClass().getNumberOfGroups() + 1; i++)
    {
      if (!day.equals(stepper.chopHM()) && getStartDate().isBefore(stepper.mergeDate(getValidExamPeriod().getBreakStartTime())) && stepper.chopYMD().isBetween(getValidExamPeriod().getBreakStartTime(), getValidExamPeriod().getBreakEndTime()))
      {
        day = stepper.chopHM();
        stepper = stepper.addMinutes(getValidExamPeriod().getBreakDuration() - BreakDurationPerGroup);
      }
      if (getValidExamPeriod().getEndTime().isBefore(stepper.addMinutes(DurationPerStudentPerGroup * getParticipatingClass().getGroup(i).length + BreakDurationPerGroup).chopYMD()))
      {
        first = true;
        stepper = stepper.mergeDate(getValidExamPeriod().getEndTime()).addMinutes((24 - getValidExamPeriod().getEndTime().getHour() + getValidExamPeriod().getStartTime().getHour()) * 60 - getValidExamPeriod().getEndTime().getMinute() + getValidExamPeriod().getStartTime().getMinute());
        if (super.getEnd().equals(new Date(1,1,1,0,0)))
        {
          stepper = new Date(1,1,1,0,0);
          break;
        }
        else
        {
          for (int j = 0; j < 20; j++)
          {
            if (getValidExamPeriod().isValid(stepper))
            {
              break;
            }
            else
            {
              stepper = stepper.addMinutes(24*60);
            }
          }
        }
      }
      if (!first)
        stepper = stepper.addMinutes(BreakDurationPerGroup);
      first = false;
      stepper = stepper.addMinutes(DurationPerStudentPerGroup * getParticipatingClass().getGroup(i).length);
    }
    return stepper;
  }

  /**This method overrides the default toString method by returning the attributes of this ProjectExam.*/
  public String toString()
  {
    String text = "";
    text += "Name: " + super.getName() + "\n";
    text += "Class: " + super.getParticipatingClass().getName() + "\n";
    text += "Place: " + super.getPlace().getName() + "\n";
    text += "Examiners: " + super.getExaminer().getName() + ", " + Examiner2.getName() + "\n";
    text += "Start: " + super.getStartDate() + "\n";
    text += "End: " + super.getEnd();
    return text;
  }
}