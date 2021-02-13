package ScheduleModel;

public interface ScheduleModel
{
  void newClass(String Name);
  void removeClass(String Name, boolean RemoveStudent);
  void reNameClass(String Name, String newName);
  Class getClass(String Name);
  Class[] getClasses();
  void orderClasses(String by, boolean reverse);
  void newStudent(String Name, int VIAID, String Participating, int Group);
  void removeStudent(int VIAID);
  Student getStudent(int VIAID);
  Student[] getStudents();
  void orderStudents(String by, boolean reverse);
  void newExaminer(String Name, Date[] Unavailabilities);
  void removeExaminer(String Name);
  Examiner getExaminer(String Name);
  Examiner[] getExaminers();
  void orderExaminers(String by, boolean reverse);
  void newRoom(String Name, int Size, String[] Equipments);
  void removeRoom(String Name);
  Room getRoom(String Name);
  Room[] getRooms();
  void orderRooms(String by, boolean reverse);
  void initExamPeriod(Date[] ValidDates, Date StartTime, Date EndTime, Date BreakStartTime, Date BreakEndTime, int BreakDuration, int DefDurPerStudent, int DefDurPerStudentPerGroup, int DefBreakDurPerStudent, int DefBreakDurPerGroup);
  ExamPeriod getExamPeriod();
  void newExam(String type, String Name, String Place, String Class, String Examiner1, String Examiner2, String[] Requirements, Date StartDate, String Duration, String BreakDuartion, String DurationPerStudent);
  void removeExam(String Name);
  Exam getExam(String Name);
  Exam[] getExams();
  void orderExams(String by, boolean reverse);
  void clearAll();
  void loadXMLs();
  void saveXMLs();
  void zipXMLs(String path);
  void unZipSCH(String path);
  void updateWebXML();
}
