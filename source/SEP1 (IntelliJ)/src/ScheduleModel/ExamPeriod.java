package ScheduleModel;

import java.util.ArrayList;

/**This class describes an ExamPeriod which defines this examination period by the following attributes:</br>
 * &nbsp;&nbsp;&nbsp;&nbsp;	• ValidDates, a list of valid dates when exams can take place</br>
 * &nbsp;&nbsp;&nbsp;&nbsp;	• StartTime, the time from when exams can take place on a valid date</br>
 * &nbsp;&nbsp;&nbsp;&nbsp;	• EndTime, the time from when exams can not take place on a valid date<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;	• BreakStartTime, the time after the break takes place for the examiners of the oral exams and the project exams<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;	• BreakEndTime, the time before the break takes place for the examiners of the oral exams and the project exams<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;	• BreakDuration, the duration (in minutes) of the break for the examiners of the oral exams and the project exams<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;	• DefaultDurationPerStudent, default duration (in minutes) per student for OralExams</br>
 * &nbsp;&nbsp;&nbsp;&nbsp;	• DefaultDurationPerStudentPerGroup, default duration (in minutes) per student, per group for ProjectExams</br>
 * &nbsp;&nbsp;&nbsp;&nbsp;	• DefaultBreakDurationPerStudent, default break duration (in minutes) per student for OralExams</br>
 * &nbsp;&nbsp;&nbsp;&nbsp;	• DefaultBreakDurationPerGroup, default break duration (in minutes) per group for ProjectExams*/
public class ExamPeriod
{
  private static final String ANSI_RESET = "\u001B[0m";    //default
  private static final String ANSI_RED = "\u001B[31m";     //Error
  private static final String ANSI_GREEN = "\u001B[32m";   //Added
  private static final String ANSI_YELLOW = "\u001B[33m";  //Modified or Removed

  /**ExamPeriod's ArrayList of valid dates, when exams can take place*/
  private ArrayList<Date> ValidDates;
  /**The time from when exams can take place on a valid date*/
  private Date StartTime;
  /**The time from when exams can not take place on a valid date*/
  private Date EndTime;
  /**The time after the break takes place for the examiners of the oral exams and the project exams*/
  private Date BreakStartTime;
  /**The time before the break takes place for the examiners of the oral exams and the project exams*/
  private Date BreakEndTime;
  /**The duration (in minutes) of the break for the examiners of the oral exams and the project exams*/
  private int BreakDuration;
  /**The default value of the DurationPerStudent (used in OralExam)*/
  private int DefDurPerStudent;
  /**The default value of the DurationPerGroup (used in ProjectExam)*/
  private int DefDurPerStudentPerGroup;
  /**The default value of the BreakDurationPerStudent (used in OralExam)*/
  private int DefBreakDurPerStudent;
  /**The default value of the BreakDurationPerGroup (used in ProjectExam)*/
  private int DefBreakDurPerGroup;

  /**The constructor initializes all instance variables, where all dates in the ValidDates ArrayList is "chopped" and only the D/M/Y is stored, also the Times are chopped in a way that only h:m gets stored.*/
  public ExamPeriod(Date[] ValidDates, Date StartTime, Date EndTime, Date BreakStartTime, Date BreakEndTime, int BreakDuration, int DefDurPerStudent, int DefDurPerStudentPerGroup, int DefBreakDurPerStudent, int DefBreakDurPerGroup)
  {
    this.ValidDates = new ArrayList<Date>();
    for (int i = 0; i < ValidDates.length; i++)
    {
      addValidDate(ValidDates[i]);
    }
    this.StartTime = StartTime.chopYMD();
    this.EndTime = EndTime.chopYMD();
    this.BreakStartTime = BreakStartTime.chopYMD();
    this.BreakEndTime = BreakEndTime.chopYMD();
    this.BreakDuration = BreakDuration;
    this.DefDurPerStudent = DefDurPerStudent;
    this.DefDurPerStudentPerGroup = DefDurPerStudentPerGroup;
    this.DefBreakDurPerStudent = DefBreakDurPerStudent;
    this.DefBreakDurPerGroup = DefBreakDurPerGroup;
  }

  /**This setter modifies the StartTime.*/
  public void setStartTime(Date StartTime)
  {
    this.StartTime = StartTime.chopYMD();
    System.out.println("ExamPeriod:    " + ANSI_YELLOW + "StartTime successfully modified to " + StartTime + ANSI_RESET);
  }

  /**This setter modifies the EndTime.*/
  public void setEndTime(Date EndTime)
  {
    this.EndTime = EndTime.chopYMD();
    System.out.println("ExamPeriod:    " + ANSI_YELLOW + "EndTime successfully modified to " + EndTime + ANSI_RESET);
  }

  /**This setter modifies the BreakStartTime.*/
  public void setBreakStartTime(Date BreakStartTime)
  {
    this.BreakStartTime = BreakStartTime.chopYMD();
    System.out.println("ExamPeriod:    " + ANSI_YELLOW + "BreakStartTime successfully modified to " + BreakStartTime + ANSI_RESET);
  }

  /**This setter modifies the BreakEndTime.*/
  public void setBreakEndTime(Date BreakEndTime)
  {
    this.BreakEndTime = BreakEndTime;
    System.out.println("ExamPeriod:    " + ANSI_YELLOW + "BreakEndTime successfully modified to " + BreakEndTime + ANSI_RESET);
  }

  /**This setter modifies the BreakDuration.*/
  public void setBreakDuration(int BreakDuration)
  {
    this.BreakDuration = BreakDuration;
    System.out.println("ExamPeriod:    " + ANSI_YELLOW + "BreakDuration successfully modified to " + BreakDuration + ANSI_RESET);
  }

  /**This setter modifies the DefaultDurationPerStudent.*/
  public void setDefaultDurationPerStudent(int DefDurPerStudent)
  {
    this.DefDurPerStudent = DefDurPerStudent;
    System.out.println("ExamPeriod:    " + ANSI_YELLOW + "DefaultDurationPerStudent successfully modified to " + DefDurPerStudent + ANSI_RESET);
  }

  /**This setter modifies the DefaultDurationPerStudentPerGroup.*/
  public void setDefaultDurationPerStudentPerGroup(int DefDurPerStudentPerGroup)
  {
    this.DefDurPerStudentPerGroup = DefDurPerStudentPerGroup;
    System.out.println("ExamPeriod:    " + ANSI_YELLOW + "DefaultDurationPerStudentPerGroup successfully modified to " + DefDurPerStudentPerGroup + ANSI_RESET);
  }

  /**This setter modifies the DefaultBreakDurationPerStudent.*/
  public void setDefaultBreakDurationPerStudent(int DefBreakDurPerStudent)
  {
    this.DefBreakDurPerStudent = DefBreakDurPerStudent;
    System.out.println("ExamPeriod:    " + ANSI_YELLOW + "DefaultBreakDurationPerStudent successfully modified to " + DefBreakDurPerStudent + ANSI_RESET);
  }

  /**This setter modifies the DefaultBreakDurationPerGroup.*/
  public void setDefaultBreakDurationPerGroup(int DefBreakDurPerGroup)
  {
    this.DefBreakDurPerGroup = DefBreakDurPerGroup;
    System.out.println("ExamPeriod:    " + ANSI_YELLOW + "DefaultBreakDurationPerGroup successfully modified to " + DefBreakDurPerGroup + ANSI_RESET);
  }

  /**This method adds a valid Date to the ValidDates in a time ordered fashion (if not already present).*/
  public void addValidDate(Date date)
  {
    date = date.chopHM();
    if (!this.ValidDates.contains(date))
    {
      if (ValidDates.size() == 0)
      {
        this.ValidDates.add(date);
      }
      else
      {
        if (ValidDates.get(ValidDates.size() - 1).isBefore(date))
          this.ValidDates.add(date);
        for (int i = 0; i < ValidDates.size(); i++)
        {
          if (date.isBefore(ValidDates.get(i)))
          {
            this.ValidDates.add(i, date);
            break;
          }
        }
      }
      System.out.println("ExamPeriod:    " + ANSI_GREEN + "Valid Date " + date.toDateOnly() + " successfully registered" + ANSI_RESET);
    }
    else
    {
      System.out.println("ExamPeriod:    " + ANSI_RED + "Error registering: " + date.toDateOnly() + " is already a Valid Date" + ANSI_RESET);
    }
  }

  /**This method removes a the valid Date inputted in the argument from the ValidDates (if present).*/
  public void removeValidDate(Date date)
  {
    date = date.chopHM();
    if (this.ValidDates.contains(date))
    {
      for (int i = 0; i < ValidDates.size(); i++)
      {
        if (date.equals(ValidDates.get(i)))
        {
          this.ValidDates.remove(i);
          break;
        }
      }
      System.out.println("ExamPeriod:    " + ANSI_YELLOW + "Valid Date " + date.toDateOnly() + " successfully removed" + ANSI_RESET);
    }
    else
    {
      System.out.println("ExamPeriod:    " + ANSI_RED + "Error removing: " + date.toDateOnly() + " is not a Valid Date" + ANSI_RESET);
    }
  }

  /**This method returns the first ("smallest") Date of the exam period.*/
  public Date getStartDate()
  {
    Date temp = ValidDates.get(0);
    for (int i = 0; i < ValidDates.size(); i++)
    {
      if (!temp.isBefore(ValidDates.get(i)))
        temp = ValidDates.get(i);
    }
    return temp;
  }

  /**This method returns the last ("biggest") Date of the exam period.*/
  public Date getEndDate()
  {
    Date temp = ValidDates.get(0);
    for (int i = 0; i < ValidDates.size(); i++)
    {
      if (temp.isBefore(ValidDates.get(i)))
        temp = ValidDates.get(i);
    }
    return temp;
  }

  /**This method returns true if the given Date in the argument can be found in the ValidDates ArrayList, according to its D/M/Y attributes (ignoring the h:m).*/
  public boolean isValid(Date date)
  {
    return (ValidDates.contains(date.chopHM()));
  }

  /**This method returns true if the given Date in the argument has the h:m time between the StartTime and EndTime.*/
  public boolean isValidTime(Date date)
  {
    return (!date.chopYMD().isBefore(StartTime) && !EndTime.isBefore(date.chopYMD()));
  }

  /**This getter returns all valid dates of this ExamPeriod.*/
  public Date[] getValidDates()
  {
    Date[] temp = new Date[ValidDates.size()];
    temp = ValidDates.toArray(temp);
    return temp;
  }

  /**This getter returns the StartTime of this ExamPeriod.*/
  public Date getStartTime()
  {
    return StartTime;
  }

  /**This getter returns the EndTime of this ExamPeriod.*/
  public Date getEndTime()
  {
    return EndTime;
  }

  /**This getter returns the BreakStartTime of this ExamPeriod.*/
  public Date getBreakStartTime()
  {
    return BreakStartTime;
  }

  /**This getter returns the BreakEndTime of this ExamPeriod.*/
  public Date getBreakEndTime()
  {
    return BreakEndTime;
  }

  /**This getter returns the BreakDuration of this ExamPeriod.*/
  public int getBreakDuration()
  {
    return BreakDuration;
  }

  /**This getter returns the DefaultDurationPerStudent of this ExamPeriod.*/
  public int getDefaultDurationPerStudent()
  {
    return DefDurPerStudent;
  }

  /**This getter returns the DefaultDurationPerStudentPerGroup of this ExamPeriod.*/
  public int getDefaultDurationPerStudentPerGroup()
  {
    return DefDurPerStudentPerGroup;
  }

  /**This getter returns the DefaultBreakDurationPerStudent of this ExamPeriod.*/
  public int getDefaultBreakDurationPerStudent()
  {
    return DefBreakDurPerStudent;
  }

  /**This getter returns the DefaultBreakDurationPerGroup of this ExamPeriod.*/
  public int getDefaultBreakDurationPerGroup()
  {
    return DefBreakDurPerGroup;
  }
}