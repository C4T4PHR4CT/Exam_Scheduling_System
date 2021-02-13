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
    var i;
    var table = "<tr><th>Classes</th></tr>";
    var x = xmlDoc.getElementsByTagName("Classes");
    $(".mainT").html(x.childElementCount);
    for (i = 0; i < x[0].childElementCount; i++) {
        table += "<tr><td class=\"buttonClass\" buttonID=\"" + i + "\">" + x[0].getElementsByTagName("Class")[i].childNodes[0].nodeValue + "</tr></td>";
    }
    $(".mainT").html(table);
    $(".buttonClass").on("click", function () {
        var selectedClass = $(this).html().toString();
        $(".scheduleT").html("");
        $(".buttonClass").attr("style", "");
        $(this).attr("style", "color:#333333 ; background-color: #FFFFFF");
        var x = xmlDoc.getElementsByTagName("Weeks");
        var table = "<tr><th>Weeks</th></tr>";
        for (i = 0; i < x[0].childElementCount; i++) {
            table += "<tr><td class=\"buttonWeek\" buttonID=\"" + i + "\">" + lineBrake(x[0].getElementsByTagName("Week")[i].childNodes[0].nodeValue) + "</tr></td>";
        }
        $(".weekT").html(table);
        $(".buttonWeek").on("click", function () {
            var selectedWeek = $(this).html().split("<br>")[0].substring(5, $(this).html().split("<br>")[0].length);
            $(".buttonWeek").attr("style", "");
            $(this).attr("style", "color:#333333 ; background-color: #FFFFFF");
            //-------------------------------------------------------------------------
            $(".scheduleT").html("<div class=\"row\"><div class=\"col-xl-2 col-lg-3 col-md-4 col-4\">Hour\\Day</div><div class=\"col-xl-2 col-lg-3 col-md-4 col-8\">Monday</div><div class=\"col-xl-2 col-lg-3 col-md-4 d-none d-md-block \">Tuesday</div><div class=\"col-xl-2 col-lg-3 d-none d-lg-block\">Wednesday</div><div class=\"col-xl-2 d-none d-xl-block\">Thursday</div><div class=\"col-xl-2 d-none d-xl-block \">Friday</div></div>");
            var start = parseInt(xmlDoc.getElementsByTagName("StartSpec")[0].childNodes[0].nodeValue);
            var end = parseInt(xmlDoc.getElementsByTagName("EndSpec")[0].childNodes[0].nodeValue);
            var StringHour, StringClassHour, MaxHour, MinHour, MinClassHour;
            for (i = start; i < end + 1; i++) {
                if (i % 2 == 0) {
                    StringHour = i / 2;
                    StringHour = StringHour * 100;
                }
                else {
                    StringHour = (i - 1) / 2;
                    StringHour = StringHour * 100;
                    StringHour += 30;
                }
                if (StringHour < 1000) {
                    StringHour = "0" + StringHour;
                }
                else {
                    StringHour = "" + StringHour;
                }
                StringClassHour = StringHour;
                StringHour = StringHour.substring(0, 2) + ":" + StringHour.substring(2, 4);
                if (i == start)
                    {
                        MinClassHour = StringClassHour;
                        MinHour = StringHour;
                    }
                if (i == end)
                    MaxHour = StringHour;
                $(".scheduleT").append("<div class=\"row\"><div class=\"col-xl-2 col-lg-3 col-md-4 col-4\">" + StringHour + "</div><div class=\"col-xl-2 col-lg-3 col-md-4 col-8 MO " + StringClassHour + "\"></div><div class=\"col-xl-2 col-lg-3 col-md-4 d-none d-md-block TU " + StringClassHour + "\"></div><div class=\"col-xl-2 col-lg-3 d-none d-lg-block WE " + StringClassHour + "\"></div><div class=\"col-xl-2 d-none d-xl-block TH " + StringClassHour + "\"></div><div class=\"col-xl-2 d-none d-xl-block FR " + StringClassHour + "\"></div></div>");
            }
            //------------------------------------------------------------------------
            for (i = 1; i < xmlDoc.getElementsByTagName("Exams")[0].childElementCount * 2 - 2; i = i + 2) {
                var Name = xmlDoc.getElementsByTagName("Exams")[0].childNodes[i].getElementsByTagName("Name")[0].childNodes[0].nodeValue;
                var Participating = xmlDoc.getElementsByTagName("Exams")[0].childNodes[i].getElementsByTagName("Participating")[0].childNodes[0].nodeValue;
                var Start = xmlDoc.getElementsByTagName("Exams")[0].childNodes[i].getElementsByTagName("Start")[0].childNodes[0].nodeValue;
                var End = xmlDoc.getElementsByTagName("Exams")[0].childNodes[i].getElementsByTagName("End")[0].childNodes[0].nodeValue;
                var StartDayW = xmlDoc.getElementsByTagName("Exams")[0].childNodes[i].getElementsByTagName("Web")[0].getElementsByTagName("StartDay")[0].childNodes[0].nodeValue;
                var StartDayD = StartDayW.substring(StartDayW.length - 2, StartDayW.length);
                StartDayW = StartDayW.substring(0, StartDayW.length - 2);
                var StartDayH = xmlDoc.getElementsByTagName("Exams")[0].childNodes[i].getElementsByTagName("Web")[0].getElementsByTagName("StartDay")[1].childNodes[0].nodeValue;
                var EndDayW = xmlDoc.getElementsByTagName("Exams")[0].childNodes[i].getElementsByTagName("Web")[0].getElementsByTagName("EndDay")[0].childNodes[0].nodeValue;
                var EndDayD = EndDayW.substring(EndDayW.length - 2, EndDayW.length);
                EndDayW = EndDayW.substring(0, EndDayW.length - 2);
                var EndDayH = xmlDoc.getElementsByTagName("Exams")[0].childNodes[i].getElementsByTagName("Web")[0].getElementsByTagName("EndDay")[1].childNodes[0].nodeValue;
                
                if (StartDayD == EndDayD && StartDayW == EndDayW && selectedWeek == StartDayW && Participating == selectedClass) {
                    $("." + StartDayD + "." + StartDayH).html("<div class=\"begin\">" + Name + "</div>");
                    start = StartDayH;
                    if (start.charAt(2) == '3')
                        start = parseInt(start.substring(0, 2)) * 2 + 1;
                    else
                        start = parseInt(start.substring(0, 2)) * 2;
                    start++;
                    end = EndDayH;
                    if (end.charAt(2) == '3')
                        end = parseInt(end.substring(0, 2)) * 2 + 1;
                    else
                        end = parseInt(end.substring(0, 2)) * 2;
                    for (j = start; j < end; j++) {
                        if (j % 2 == 0) {
                            StringClassHour = j / 2;
                            StringClassHour = StringClassHour * 100;
                        }
                        else {
                            StringClassHour = (j - 1) / 2;
                            StringClassHour = StringClassHour * 100;
                            StringClassHour += 30;
                        }
                        if (StringClassHour < 1000) {
                            StringClassHour = "0" + StringClassHour;
                        }
                        else {
                            StringClassHour = "" + StringClassHour;
                        }
                        if (j == end - 1)
                            $("." + StartDayD + "." + StringClassHour).html("<div class=\"end\"></div>");
                        else if
                            (j == start) $("." + StartDayD + "." + StringClassHour).html("<div class=\"middle\">" + Start.substring(11, 16) + "-" + End.substring(11, 16) + "</div>");
                        else
                            $("." + StartDayD + "." + StringClassHour).html("<div class=\"middle\"></div>");
                    }
                }
                else {
                    if (selectedWeek == StartDayW && Participating == selectedClass) {
                        $("." + StartDayD + "." + StartDayH).html("<div class=\"begin\">" + Name + "</div>");
                        start = StartDayH;
                        if (start.charAt(2) == '3')
                            start = parseInt(start.substring(0, 2)) * 2 + 1;
                        else
                            start = parseInt(start.substring(0, 2)) * 2;
                        start++;
                        end = parseInt(xmlDoc.getElementsByTagName("EndSpec")[0].childNodes[0].nodeValue);
                        for (j = start; j < end; j++) {
                            if (j % 2 == 0) {
                                StringClassHour = j / 2;
                                StringClassHour = StringClassHour * 100;
                            }
                            else {
                                StringClassHour = (j - 1) / 2;
                                StringClassHour = StringClassHour * 100;
                                StringClassHour += 30;
                            }
                            if (StringClassHour < 1000) {
                                StringClassHour = "0" + StringClassHour;
                            }
                            else {
                                StringClassHour = "" + StringClassHour;
                            }
                            if (j == end - 1)
                                $("." + StartDayD + "." + StringClassHour).html("<div class=\"end\"></div>");
                            else if
                                (j == start) $("." + StartDayD + "." + StringClassHour).html("<div class=\"middle\">" + Start.substring(11, 16) + "-" + MaxHour + "</div>");
                            else
                                $("." + StartDayD + "." + StringClassHour).html("<div class=\"middle\"></div>");
                        }
                    }
                    if (selectedWeek == EndDayW && Participating == selectedClass) {
                        $("." + EndDayD + "." + MinClassHour).html("<div class=\"begin\">" + Name + "</div>");
                        start = parseInt(xmlDoc.getElementsByTagName("StartSpec")[0].childNodes[0].nodeValue) + 1;
                        end = EndDayH;
                        if (end.charAt(2) == '3')
                            end = parseInt(end.substring(0, 2)) * 2;
                        else
                            end = parseInt(end.substring(0, 2)) * 2 - 1;
                        end++;
                        for (j = start; j < end; j++) {
                            if (j % 2 == 0) {
                                StringClassHour = j / 2;
                                StringClassHour = StringClassHour * 100;
                            }
                            else {
                                StringClassHour = (j - 1) / 2;
                                StringClassHour = StringClassHour * 100;
                                StringClassHour += 30;
                            }
                            if (StringClassHour < 1000) {
                                StringClassHour = "0" + StringClassHour;
                            }
                            else {
                                StringClassHour = "" + StringClassHour;
                            }
                            if (j == end - 1)
                                $("." + EndDayD + "." + StringClassHour).html("<div class=\"end\"></div>");
                            else if
                                (j == start) $("." + EndDayD + "." + StringClassHour).html("<div class=\"middle\">" + MinHour + "-" + End.substring(11, 16) + "</div>");
                            else
                                $("." + EndDayD + "." + StringClassHour).html("<div class=\"middle\"></div>");
                        }
                    }
                    for (k = 1; k < xmlDoc.getElementsByTagName("Exams")[0].childNodes[i].getElementsByTagName("Web")[0].getElementsByTagName("MidDays")[0].childElementCount * 2; k = k + 2) {
                        var MidDayW = xmlDoc.getElementsByTagName("Exams")[0].childNodes[i].getElementsByTagName("Web")[0].getElementsByTagName("MidDays")[0].childNodes[k].childNodes[0].nodeValue;
                        var MidDayD = MidDayW.substring(MidDayW.length - 2, MidDayW.length);
                        MidDayW = MidDayW.substring(0, MidDayW.length - 2);
                        
                        if (selectedWeek == MidDayW && Participating == selectedClass) {
                            $("." + MidDayD + "." + MinClassHour).html("<div class=\"begin\">" + Name + "</div>");
                            start = parseInt(xmlDoc.getElementsByTagName("StartSpec")[0].childNodes[0].nodeValue) + 1;
                            end = parseInt(xmlDoc.getElementsByTagName("EndSpec")[0].childNodes[0].nodeValue);
                            for (j = start; j < end; j++) {
                                if (j % 2 == 0) {
                                    StringClassHour = j / 2;
                                    StringClassHour = StringClassHour * 100;
                                }
                                else {
                                    StringClassHour = (j - 1) / 2;
                                    StringClassHour = StringClassHour * 100;
                                    StringClassHour += 30;
                                }
                                if (StringClassHour < 1000) {
                                    StringClassHour = "0" + StringClassHour;
                                }
                                else {
                                    StringClassHour = "" + StringClassHour;
                                }
                                if (j == end - 1)
                                    $("." + MidDayD + "." + StringClassHour).html("<div class=\"end\"></div>");
                                else if
                                    (j == start) $("." + MidDayD + "." + StringClassHour).html("<div class=\"middle\">" + MinHour + "-" + MaxHour + "</div>");
                                else
                                    $("." + MidDayD + "." + StringClassHour).html("<div class=\"middle\"></div>");
                            }
                        }
                    }
                }
            }
        })
    });
}

function lineBrake(x) {
    var y = x.replace("~", "<br>");
    return y;
}