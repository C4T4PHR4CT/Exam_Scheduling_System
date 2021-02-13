/* globals $:false */
readXML();

function readXML() {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            SchedulerMain(this);
        }
    };
    xhttp.open("GET", "_Data/Exams.xml", true);
    xhttp.send();
}

function SchedulerMain(xml) {
    var xmlDoc = xml.responseXML;
    var i, selectedWeek;
    var table = "<tr><th>Classes</th></tr>";
    var x = xmlDoc.getElementsByTagName("Classes");
    $(".mainT").html(x.childElementCount);
    for (i = 0; i < x[0].childElementCount; i++) {
        table += "<tr><td class=\"buttonClass\" buttonID=\"" + i + "\">" + x[0].getElementsByTagName("Class")[i].childNodes[0].nodeValue + "</tr></td>";
    }
    $(".mainT").html(table);
    $(".buttonClass").on("click", function () {
        $(".buttonClass").attr("style", "");
        $(this).attr("style", "color:#333333 ; background-color: #FFFFFF");
        var x = xmlDoc.getElementsByTagName("Weeks");
        var table = "<tr><th>Weeks</th></tr>";
        for (i = 0; i < x[0].childElementCount; i++) {
            table += "<tr><td class=\"buttonWeek\" buttonID=\"" + i + "\">" + lineBrake(x[0].getElementsByTagName("Week")[i].childNodes[0].nodeValue) + "</tr></td>";
        }
        $(".weekT").html(table);
        $(".buttonWeek").on("click", function () {
            var selectedWeek = $(this).html().split("<br>")[0].substring(5,$(this).html().split("<br>")[0].length);
            $(".buttonWeek").attr("style", "");
            $(this).attr("style", "color:#333333 ; background-color: #FFFFFF");
            //-------------------------------------------------------------------------
            $(".scheduleT").html("<div class=\"row\"><div class=\"col-xl-2 col-lg-3 col-md-4 col-4\">Hour\\Day</div><div class=\"col-xl-2 col-lg-3 col-md-4 col-8\">Monday</div><div class=\"col-xl-2 col-lg-3 col-md-4 d-none d-md-block \">Tuesday</div><div class=\"col-xl-2 col-lg-3 d-none d-lg-block\">Wednesday</div><div class=\"col-xl-2 d-none d-xl-block\">Thursday</div><div class=\"col-xl-2 d-none d-xl-block \">Friday</div></div>");
            var start = parseInt(xmlDoc.getElementsByTagName("StartSpec")[0].childNodes[0].nodeValue);
            var end = parseInt(xmlDoc.getElementsByTagName("EndSpec")[0].childNodes[0].nodeValue);
            var StringHour, StringClassHour, MaxClassHour, MinClassHour;
            for (i = start; i < end + 1; i++) {
                if (i % 2 == 0)
                    {
                        StringHour = i / 2;
                        StringHour = StringHour * 100;
                    }
                else
                    {
                        StringHour = (i - 1) / 2;
                        StringHour = StringHour * 100;
                        StringHour += 30;
                    }
                if (StringHour < 1000)
                    {
                        StringHour = "0" + StringHour;
                    }
                else
                    {
                        StringHour = "" + StringHour;
                    }
                StringClassHour = StringHour;
                /*if (i == start)
                    MaxClassHour = StringClassHour;
                if (i == end - 1)
                    MinClassHour = StringClassHour;*/
                StringHour = StringHour.substring(0,2) + ":" + StringHour.substring(2,4);
                $(".scheduleT").append("<div class=\"row\"><div class=\"col-xl-2 col-lg-3 col-md-4 col-4\">" + StringHour + "</div><div class=\"col-xl-2 col-lg-3 col-md-4 col-8 MO " + StringClassHour + "\"></div><div class=\"col-xl-2 col-lg-3 col-md-4 d-none d-md-block TU " + StringClassHour + "\"></div><div class=\"col-xl-2 col-lg-3 d-none d-lg-block WE " + StringClassHour + "\"></div><div class=\"col-xl-2 d-none d-xl-block TH " + StringClassHour + "\"></div><div class=\"col-xl-2 d-none d-xl-block FR " + StringClassHour + "\"></div></div>");
            }
            //------------------------------------------------------------------------
            var StartDayW = xmlDoc.getElementsByTagName("Exams")[0].childNodes[0].[0].childNodes[0].nodeValue;
            $(".MO").html(StartDayW);
            /*for (i = 0; i < xmlDoc.getElementsByTagName("Exams")[0].childElementCount - 1; i++)
                {
                    var StartDayW = xmlDoc.getElementsByTagName("Exams")[0].childNodes[i].getElementsByTagName("StartDay").childNodes[0].nodeValue;
                    var StartDayD = StartDayW.substring(StartDayW.length-2,StartDayW.length);
                    StartDayW = StartDayW.substring(0, StartDayW.length-2);
                    $(".MO").html(StartDayW);
                    $(".TH").html(StartDayD);
                }*/
        })
    });
}

function lineBrake(x) {
    var y = x.replace("~", "<br>");
    return y;
}