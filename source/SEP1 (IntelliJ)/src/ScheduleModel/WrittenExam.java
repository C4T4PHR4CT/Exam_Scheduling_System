package ScheduleModel;

/**This class describes a WrittenExam, by extending the Exam class with its specific methods.*/
public class WrittenExam extends Exam
{
  /**The constructor calls the superclass' (Exam's) constructor.*/
  public WrittenExam(String Name, Room Place, Class Participating, Examiner Examiner1, String[] Requirements, Date Start, ExamPeriod ValidExamPeriod, int Duration)
  {
    super(Name, Place, Participating, Examiner1, Requirements, Start, ValidExamPeriod, Duration);
  }

  /**This method returns true if this WrittenExam's Participating Class has equal or less students plus examiners (in this case only one examiner) than the Place's (Room's) seating capacity.*/
  public boolean doesFit()
  {
    return (super.getPlace().getSize() >= super.getParticipatingClass().getNumberOfStudents() + 1);
  }

  /**This method overrides the default toString method by returning the attributes of this WrittenExam.*/
  public String toString()
  {
    String text = "";
    text += "Name: " + super.getName() + "\n";
    text += "Class: " + super.getParticipatingClass().getName() + "\n";
    text += "Place: " + super.getPlace() + "\n";
    text += "Examiner: " + super.getExaminer() + "\n";
    text += "Start: " + super.getStartDate() + "\n";
    text += "End: " + super.getEnd();
    return text;
  }

  /**This method overrides the superclass' getEnd method because this has a different behaviour.*/
  public Date getEnd()
  {
    return super.getStartDate().addMinutes(super.getDuration());
  }
}