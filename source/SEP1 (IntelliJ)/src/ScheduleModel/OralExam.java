package ScheduleModel;

/**This class describes an OralExam, by extending the Exam class with its specific methods and attributes, like:</br>
 * &nbsp;&nbsp;&nbsp;&nbsp;	• a second Examiner<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;	• DurationPerStudent (in minutes)<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;	• BreakDurationPerStudent (in minutes)*/
public class OralExam extends Exam
{
  /** OralExam's second Examiner (a pointer of an Examiner) */
  private Examiner Examiner2;
  /** This Duration is the same as the superclass' and its only present for comfort reasons*/
  private int Duration;
  /** OralExam's DurationPerStudent (in minutes) */
  private int DurationPerStudent;
  /** OralExam's BreakDurationPerStudent (in minutes) */
  private int BreakDurationPerStudent;
  /** This value is true if the Duration attribute in the superclass is redundant, meaning that the user did not input the data, but the program calculated it. */
  private boolean RedundantDuration;
  /** This value is true if the DurationPerStudent attribute is redundant, meaning that the user did not input the data, but the program calculated it. */
  private boolean RedundantDurPerStudent;
  /** This value is true if the BreakDurationPerStudent attribute is redundant, meaning that the user did not input the data, but the program calculated it. */
  private boolean RedundantBreakDurPerStudent;

  /**
   * The constructor calls the superclass' (Exam's) constructor and
   * &nbsp;&nbsp;&nbsp;&nbsp;	- prioritizes between the possible redundant information in the case of Duration, DurationPerStudent, and BreakDurationPerStudent<br/>
   * &nbsp;&nbsp;&nbsp;&nbsp;	- complements the possible missing information in the case of Duration, DurationPerStudent, and BreakDurationPerStudent<br/>
   * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(replacing the needed information from the ExamPeriod's Default values)<br/>
   * &nbsp;&nbsp;&nbsp;&nbsp;	- and initializes the OralExam specific instance variables
   */
  public OralExam(String Name, Room Place, Class Participating, Examiner Examiner1, Examiner Examiner2, String[] Requirements, Date Start,
      ExamPeriod ValidExamPeriod, int Duration, int DurationPerStudent, int BreakDurationPerStudent)
  {
    super(Name, Place, Participating, Examiner1, Requirements, Start,
        ValidExamPeriod, Duration);
    this.Examiner2 = Examiner2;

    RedundantDuration = true;
    RedundantDurPerStudent = true;
    RedundantBreakDurPerStudent = true;

    if (Duration > 0 && DurationPerStudent > 0 && BreakDurationPerStudent > 0)
    {
      Duration = 0;
    }

    if (Duration <= 0 && DurationPerStudent <= 0 && BreakDurationPerStudent <= 0)
    {
      RedundantDurPerStudent = false;
      RedundantBreakDurPerStudent = false;
      DurationPerStudent = ValidExamPeriod.getDefaultDurationPerStudent();
      BreakDurationPerStudent = ValidExamPeriod.getDefaultBreakDurationPerStudent();
    }

    if (Duration > 0 && DurationPerStudent <= 0 && BreakDurationPerStudent <= 0)
    {
      RedundantDuration = false;
      RedundantBreakDurPerStudent = false;
      BreakDurationPerStudent = ValidExamPeriod.getDefaultBreakDurationPerStudent();
    }

    if (Duration <= 0 && DurationPerStudent > 0 && BreakDurationPerStudent <= 0)
    {
      RedundantDurPerStudent = false;
      RedundantBreakDurPerStudent = false;
      BreakDurationPerStudent = ValidExamPeriod.getDefaultBreakDurationPerStudent();
    }

    if (Duration <= 0 && DurationPerStudent <= 0 && BreakDurationPerStudent > 0)
    {
      RedundantDuration = false;
      RedundantBreakDurPerStudent = false;
      DurationPerStudent = ValidExamPeriod.getDefaultDurationPerStudent();
    }

    if (Duration <= 0 && DurationPerStudent > 0 && BreakDurationPerStudent > 0)
    {
      RedundantDurPerStudent = false;
      RedundantBreakDurPerStudent = false;
      Duration = (DurationPerStudent + BreakDurationPerStudent) * Participating.getNumberOfStudents();
    }

    if (Duration > 0 && DurationPerStudent <= 0 && BreakDurationPerStudent > 0)
    {
      RedundantDuration = false;
      RedundantBreakDurPerStudent = false;
      DurationPerStudent = (Duration - (BreakDurationPerStudent * Participating.getNumberOfStudents())) / Participating.getNumberOfStudents();
    }

    if (Duration > 0 && DurationPerStudent > 0 && BreakDurationPerStudent <= 0)
    {
      RedundantDuration = false;
      RedundantDurPerStudent = false;
      BreakDurationPerStudent = (Duration - (DurationPerStudent * Participating.getNumberOfStudents())) / Participating.getNumberOfStudents();
    }

    super.setDuration(Duration);
    this.Duration = Duration;
    this.DurationPerStudent = DurationPerStudent;
    this.BreakDurationPerStudent = BreakDurationPerStudent;

    //         HOTFIX
    RedundantDuration = true;
    RedundantDurPerStudent = false;
    RedundantBreakDurPerStudent = false;
    //         HOTFIX
  }

  /**This getter returns the second Examiner of this OralExam.*/
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

  /**This getter returns the DurationPerStudent if its not redundant, otherwise it returns 0.*/
  int getRedDurationPerStudent()
  {
    if (RedundantDurPerStudent)
      return 0;
    else
      return DurationPerStudent;
  }

  /**This getter returns the BreakDurationPerStudent if its not redundant, otherwise it returns 0.*/
  int getRedBreakDurationPerStudent()
  {
    if (RedundantBreakDurPerStudent)
      return 0;
    else
      return BreakDurationPerStudent;
  }

  /**This getter returns the DurationPerStudent.*/
  public int getDurationPerStudent()
  {
    return DurationPerStudent;
  }

  /**This getter returns the BreakDurationPerStudent.*/
  public int getBreakDurationPerStudent()
  {
    return BreakDurationPerStudent;
  }

  /**This method returns the automatically generated, detailed schedule for this OralExam.*/
  public String getDetailedSchedule()
  {
    boolean first = true;
    String temp = "";
    Date stepper = getStartDate().copy();
    Date day = new Date(1,1,1,0,0);
    for (int i = 0; i < getParticipatingClass().getNumberOfStudents(); i++)
    {
      if (!day.equals(stepper.chopHM()) && getStartDate().isBefore(stepper.mergeDate(getValidExamPeriod().getBreakStartTime())) && stepper.chopYMD().isBetween(getValidExamPeriod().getBreakStartTime(), getValidExamPeriod().getBreakEndTime()))
      {
        day = stepper.chopHM();
        stepper = stepper.addMinutes(getValidExamPeriod().getBreakDuration() - BreakDurationPerStudent);
      }
      if (getValidExamPeriod().getEndTime().isBefore(stepper.addMinutes(DurationPerStudent + BreakDurationPerStudent).chopYMD()))
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
        stepper = stepper.addMinutes(BreakDurationPerStudent);
      first = false;
      temp += getParticipatingClass().getStudents()[i].getName() + " " + stepper.toString() + " - ";
      stepper = stepper.addMinutes(DurationPerStudent);
      temp += stepper.toTimeOnly() + "~";
    }
    return temp;
  }

  public Date getEnd()
  {
    boolean first = true;
    Date stepper = getStartDate().copy();
    Date day = new Date(1,1,1,0,0);
    for (int i = 0; i < getParticipatingClass().getNumberOfStudents(); i++)
    {
      if (!day.equals(stepper.chopHM()) && getStartDate().isBefore(stepper.mergeDate(getValidExamPeriod().getBreakStartTime())) && stepper.chopYMD().isBetween(getValidExamPeriod().getBreakStartTime(), getValidExamPeriod().getBreakEndTime()))
      {
        day = stepper.chopHM();
        stepper = stepper.addMinutes(getValidExamPeriod().getBreakDuration() - BreakDurationPerStudent);
      }
      if (getValidExamPeriod().getEndTime().isBefore(stepper.addMinutes(DurationPerStudent + BreakDurationPerStudent).chopYMD()))
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
        stepper = stepper.addMinutes(BreakDurationPerStudent);
      first = false;
      stepper = stepper.addMinutes(DurationPerStudent);
    }
    return stepper;
  }

  /**This method overrides the default toString method by returning the attributes of this OralExam.*/
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