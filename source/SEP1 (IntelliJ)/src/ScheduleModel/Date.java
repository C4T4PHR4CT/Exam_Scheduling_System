package ScheduleModel;

import java.time.LocalDate;
import java.util.regex.Pattern;

/**This class describes date and time, down to minute precision.*/
public class Date
{
  /**Stores the valid dates for each month, where index0 is January and index11 is December.*/
  private static final int[] days = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
  /**Stores the month names, where index0 is January and index11 is December.*/
  private static final String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
  /**Stores the shortened day names, where index0 is Monday and index6 is Sunday.*/
  private static final String[] xmlDays = {"MO", "TU", "WE", "TH", "FR", "SA", "SU"};
  /**Year*/
  private int Year;
  /**Month where 1 is January and 12 is December*/
  private int Month;
  /**Day where 1 is the 1th day of the Month*/
  private int Day;
  /**Hour from 0-23*/
  private int Hour;
  /**Minute from 0-59*/
  private int Minute;

  /**This constructor checks and corrects the inputted arguments that are out of bounds, by setting them to the value of the closer bound, and then initializes all instance variables.*/
  public Date(int Year, int Month, int Day, int Hour, int Minute)
  {
    if (Minute > 59)
      Minute = 59;
    if (Minute < 0)
      Minute = 0;
    if (Hour > 23)
      Hour = 23;
    if (Hour < 0)
      Hour = 0;
    if (Month < 1)
      Month = 1;
    if (Month > 12)
      Month = 12;
    if (Day < 1)
      Day = 1;
    if (isLeapYear())
    {
      if (Day > 29 && Month == 2)
        Day = 29;
    }
    else if (Day > days[Month - 1])
    {
      Day = days[Month - 1];
    }
    Year = Math.abs(Year);
    this.Year = Year;
    this.Month = Month;
    this.Day = Day;
    this.Hour = Hour;
    this.Minute = Minute;
  }

  /**This constructor has the same checking solution as the main one but it only takes YMD and sets the hm to 00:00.*/
  public Date(int Year, int Month, int Day)
  {
    if (Month < 1)
      Month = 1;
    if (Month > 12)
      Month = 12;
    if (Day < 1)
      Day = 1;
    if (isLeapYear())
    {
      if (Day > 29 && Month == 2)
        Day = 29;
    }
    else if (Day > days[Month - 1])
    {
      Day = days[Month - 1];
    }
    Year = Math.abs(Year);
    this.Year = Year;
    this.Month = Month;
    this.Day = Day;
    this.Hour = 0;
    this.Minute = 0;
  }

  /**This constructor has the same checking solution as the main one but it only takes hm and sets the YMD to 1.1.1.*/
  public Date(int Hour, int Minute)
  {
    if (Minute > 59)
      Minute = 59;
    if (Minute < 0)
      Minute = 0;
    if (Hour > 23)
      Hour = 23;
    if (Hour < 0)
      Hour = 0;
    this.Year = 1;
    this.Month = 1;
    this.Day = 1;
    this.Hour = Hour;
    this.Minute = Minute;
  }

  /**This constructor has the same checking solution as the main one but it takes a java.time.LocalDate and sets the hm to 00:00.*/
  public Date(LocalDate date)
  {
    int Year = date.getYear();
    int Month = date.getMonthValue();
    int Day = date.getDayOfMonth();
    if (Month < 1)
      Month = 1;
    if (Month > 12)
      Month = 12;
    if (Day < 1)
      Day = 1;
    if (isLeapYear())
    {
      if (Day > 29 && Month == 2)
        Day = 29;
    }
    else if (Day > days[Month - 1])
    {
      Day = days[Month - 1];
    }
    Year = Math.abs(Year);
    this.Year = Year;
    this.Month = Month;
    this.Day = Day;
    this.Hour = 0;
    this.Minute = 0;
  }

  /**This constructor processes the "DD/MM/YYYY hh:mm" or "DD/MM/YYYY" or "hh:mm" formatted strings and then checks and corrects them with the same way as the main constructor.*/
  public Date(String date)
  {
    date = date.replaceAll(" {2,}", " ").trim();
    int Year = 1;
    int Month = 1;
    int Day = 1;
    int Hour = 0;
    int Minute = 0;
    String[] split1, split2;
    if (Pattern.compile("\\d \\d").matcher(date).find())
    {
      split1 = date.split("\\d \\d");
      split1[0] += date.substring(date.indexOf(split1[0]) + split1[0].length(),
          date.indexOf(split1[0]) + split1[0].length() + 1);
      split1[1] =
          date.substring(date.indexOf(split1[1]) - 1, date.indexOf(split1[1])) + split1[1];
      split2 = split1[1].split(":");
      split1 = split1[0].split("/");
      Minute = Integer.parseInt(split2[1].trim());
      Hour = Integer.parseInt(split2[0].trim());
      Day = Integer.parseInt(split1[0].trim());
      Month = Integer.parseInt(split1[1].trim());
      Year = Integer.parseInt(split1[2].trim());
    }
    else if (date.contains(":"))
    {
      split1 = date.split(":");
      Minute = Integer.parseInt(split1[1].trim());
      Hour = Integer.parseInt(split1[0].trim());
      Day = 1;
      Month = 1;
      Year = 1;
    }
    else if (date.contains("/"))
    {
      split1 = date.split("/");
      Minute = 0;
      Hour = 0;
      Day = Integer.parseInt(split1[0].trim());
      Month = Integer.parseInt(split1[1].trim());
      Year = Integer.parseInt(split1[2].trim());
    }
    if (Minute > 59)
      Minute = 59;
    if (Minute < 0)
      Minute = 0;
    if (Hour > 23)
      Hour = 23;
    if (Hour < 0)
      Hour = 0;
    if (Month < 1)
      Month = 1;
    if (Month > 12)
      Month = 12;
    if (Day < 1)
      Day = 1;
    if (isLeapYear())
    {
      if (Day > 29 && Month == 2)
        Day = 29;
    }
    else if (Day > days[Month - 1])
    {
      Day = days[Month - 1];
    }
    Year = Math.abs(Year);
    this.Year = Year;
    this.Month = Month;
    this.Day = Day;
    this.Hour = Hour;
    this.Minute = Minute;
  }

  /**This method returns the corresponding day number of the week in the format of 1-7 where 1 is Monday and 7 is Sunday.*/
  public int getDayOfWeek()
  {
    int q = Day;
    int m = Month;
    int y = Year;
    if (m < 3)
      y--;
    if (m < 3)
      m += 12;
    int k = y % 100;
    int j = y / 100;
    int h = (q + ((13 * (m + 1)) / 5) + k + k / 4 + j / 4 + 5 * j + 5) % 7 + 1;
    return h;
  }

  /**This method returns the corresponding day number of the year in the format of 1-366 where 1 is first day of the year.*/
  public int getDayOfYear()
  {
    int dayOyear = 0;
    for (int i = 0; i < Month - 1; i++)
      if (isLeapYear() && i == 1)
      {
        dayOyear += 29;
      }
      else
      {
        dayOyear += days[i];
      }
    dayOyear += Day;
    return dayOyear;
  }

  /**This method returns the corresponding week number of the year in the format of 1-54 where 1 is first week of the year.*/
  public int getWeekOfYear()
  {
    return ((modifyDate(-1, 1, 1, 1, 1).getDayOfWeek() + getDayOfYear() - 2) / 7 + 1);
  }

  /**This method returns true if this Date is before the inputted Date in the argument.*/
  public boolean isBefore(Date date)
  {
    if (date.Year > Year)
      return true;
    else if (date.Year < Year)
      return false;
    if (date.Month > Month)
      return true;
    else if (date.Month < Month)
      return false;
    if (date.Day > Day)
      return true;
    else if (date.Day < Day)
      return false;
    if (date.Hour > Hour)
      return true;
    else if (date.Hour < Hour)
      return false;
    if (date.Minute > Minute)
      return true;
    else if (date.Minute < Minute)
      return false;
    return false;
  }

  /** This method returns a new Date without the Hour and the Minute attributes of this Date (sets them to 0) (this is used to compare dates only in D/M/Y format). */
  public Date chopHM()
  {
    return new Date(Year, Month, Day, 0, 0);
  }

  /** This method returns a new Date without the Year, Month and the Day attributes of this Date (sets them to 1) (this is used to compare dates only in h:m format). */
  public Date chopYMD()
  {
    return new Date(1, 1, 1, Hour, Minute);
  }

  /** This method returns a new Date with the copy this Date. */
  public Date copy()
  {
    return new Date(Year, Month, Day, Hour, Minute);
  }

  /** This method returns a new Date with minutes added to this Date, where the excess minutes (Minute mod 60) get converted into hours, hours into days, days into months (LIMITATION: assuming the number of days is not going to exceed 2 month's total number of days), and month into years. */
  public Date addMinutes(int minutes)
  {
    int newMinute = Minute + minutes;
    int newHour = newMinute / 60 + Hour;
    newMinute = newMinute % 60;
    int newDay = newHour / 24 + Day;
    newHour = newHour % 24;
    int newMonth = Month;
    if (isLeapYear())
    {
      if (newDay > 29 && Month == 2)
      {
        newMonth++;
        newDay = newDay - 29;
      }
    }
    else if (newDay > days[Month - 1])
    {
      newMonth++;
      newDay = newDay - days[Month - 1];
    }
    int newYear = Year;
    if (newMonth == 13)
      newMonth = 1;
    return new Date(newYear, newMonth, newDay, newHour, newMinute);
  }

  /** This method returns a new Date where the some values of this Date can be changed by inputting a new value to the corresponding argument or, can be left the same by inputting a negative value to the corresponding argument. */
  public Date modifyDate(int Year, int Month, int Day, int Hour, int Minute)
  {
    if (Minute > 59)
      Minute = 59;
    if (Hour > 23)
      Hour = 23;
    if (Month == 0)
      Month = 1;
    if (Month > 12)
      Month = 12;
    if (Day == 0)
      Day = 1;
    try
    {
      if (isLeapYear())
      {
        if (Day > 29 && Month == 2)
          Day = 29;
      }
      else if (Day > days[Month - 1])
      {
        Day = days[Month - 1];
      }
    }
    catch (Exception e)
    {
      if (Day > days[this.Month - 1])
      {
        Day = days[this.Month - 1];
      }
    }
    if (Year < 0)
      Year = this.Year;
    if (Month < 0)
      Month = this.Month;
    if (Day < 0)
      Day = this.Day;
    if (Hour < 0)
      Hour = this.Hour;
    if (Minute < 0)
      Minute = this.Minute;
    return new Date(Year, Month, Day, Hour, Minute);
  }

  /** This method returns a new Date where the D/M/Y of this Date is merged with the h:m of the Date in the argument. */
  public Date mergeDate(Date HM)
  {
    return new Date(Year, Month, Day, HM.getHour(), HM.getMinute());
  }

  /** This method overrides the default equals method by returning true if all of the inputted Date's instance variables in the argument has the same values of this Date's. */
  public boolean equals(Object obj)
  {
    if (!(obj instanceof Date))
      return false;
    Date date = (Date) obj;
    return (date.Year == Year && date.Month == Month && date.Day == Day
        && date.Hour == Hour && date.Minute == Minute);
  }

  /**This method overrides the default toString method by returning the date in DD/MM/YYYY hh:mm format.*/
  public String toString()
  {
    String temp = "";
    if (Day < 10)
      temp += "0";
    temp += Day + "/";
    if (Month < 10)
      temp += "0";
    temp += Month + "/" + Year + " ";
    temp += Hour + ":";
    if (Minute < 10)
      temp += "0";
    temp += Minute;
    return temp;
  }

  /**This method returns the date in DD/MM/YYYY format.*/
  public String toDateOnly()
  {
    String temp = "";
    if (Day < 10)
      temp += "0";
    temp += Day + "/";
    if (Month < 10)
      temp += "0";
    temp += Month + "/" + Year;
    return temp;
  }

  /**This method returns the date in hh:mm format.*/
  public String toTimeOnly()
  {
    String temp = "";
    temp += Hour + ":";
    if (Minute < 10)
      temp += "0";
    temp += Minute;
    return temp;
  }

  /**This method returns a new Date with the next day in time with the given week day in the argument in the format of 1-7 where 1 is Monday.*/
  public Date stepToNextWeekDay(int Day)
  {
    Date stepper = copy();
    while (Day != stepper.getDayOfWeek())
      stepper = stepper.addMinutes(60 * 24);
    return stepper;
  }

  /**This method returns the number of days passed from 0000.01.01.*/
  public int toDays()
  {
    int Days = 0;
    for (int i = 0; i < Year - 1; i++)
    {
      if (new Date(i, 1, 1).isLeapYear())
      {
        Days += 366;
      }
      else
      {
        Days += 365;
      }
    }
    Days += getDayOfYear();
    return Days;
  }

  /**This method returns true if this date is between the two given dates in the argument, where equality is also included.*/
  public boolean isBetween(Date first, Date second)
  {
    return  (first.isBefore(this) && this.isBefore(second) || this.equals(first) || this.equals(second));
  }

  /**This method returns the shortened week day name of this date.*/
  public String toXmlDay()
  {
    return xmlDays[getDayOfWeek() - 1];
  }

  /**This method returns the time in the format of hhmm.*/
  public String toXmlTime()
  {
    String time = toTimeOnly();
    if (time.length() < 5)
      time = "0" + time;
    time = time.substring(0,2) + time.substring(3,5);
    return time;
  }

  /**This method returns the month name of this date.*/
  public String toMonth()
  {
    return months[Month - 1];
  }

  /**This method returns true if this date's year is a leap year.*/
  public boolean isLeapYear()
  {
    return (Year % 4 == 0 && (Year % 100 != 0 || Year % 400 == 0));
  }

  /**This method returns this date in java.time.LocalDate format.*/
  public LocalDate toLocalDate()
  {
    return LocalDate.of(Year, Month, Day);
  }

  /**This getter returns the Year of this Date.*/
  public int getYear()
  {
    return Year;
  }

  /**This getter returns the Month of this Date.*/
  public int getMonth()
  {
    return Month;
  }

  /**This getter returns the Day of this Date.*/
  public int getDay()
  {
    return Day;
  }

  /**This getter returns the Hour of this Date.*/
  public int getHour()
  {
    return Hour;
  }

  /**This getter returns the Minute of this Date.*/
  public int getMinute()
  {
    return Minute;
  }
}