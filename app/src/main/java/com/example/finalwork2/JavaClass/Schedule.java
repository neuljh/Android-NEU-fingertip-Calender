package com.example.finalwork2.JavaClass;

//"title TEXT," +
//        "finish TEXT," +
//        "registerdate TEXT," +
//        "scheduledate TEXT," +
//        "category TEXT," +
//        "remindtime TEXT," +
//        "priority TEXT," +
//        "starttime TEXT," +
//        "endtime TEXT," +
//        "tips TEXT)"

public class Schedule {
    private String id;
    private String title;
    private boolean finish;
    private String registerdate;
    private String scheduledate;
    private String category;
    private String remindtime;
    private String priority;
    private String starttime;
    private String endtime;
    private String tips;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Schedule(String id, String title, boolean finish, String registerdate, String scheduledate, String category, String remindtime, String priority, String starttime, String endtime, String tips) {
        this.id = id;
        this.title = title;
        this.finish = finish;
        this.registerdate = registerdate;
        this.scheduledate = scheduledate;
        this.category = category;
        this.remindtime = remindtime;
        this.priority = priority;
        this.starttime = starttime;
        this.endtime = endtime;
        this.tips = tips;
    }

    public Schedule(String title, boolean finish, String registerdate, String scheduledate, String category, String remindtime, String priority, String starttime, String endtime, String tips) {
        this.title = title;
        this.finish = finish;
        this.registerdate = registerdate;
        this.scheduledate = scheduledate;
        this.category = category;
        this.remindtime = remindtime;
        this.priority = priority;
        this.starttime = starttime;
        this.endtime = endtime;
        this.tips = tips;
    }

    public Schedule() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    public String getRegisterdate() {
        return registerdate;
    }

    public void setRegisterdate(String registerdate) {
        this.registerdate = registerdate;
    }

    public String getScheduledate() {
        return scheduledate;
    }

    public void setScheduledate(String scheduledate) {
        this.scheduledate = scheduledate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRemindtime() {
        return remindtime;
    }

    public void setRemindtime(String remindtime) {
        this.remindtime = remindtime;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", finish=" + finish +
                ", registerdate='" + registerdate + '\'' +
                ", scheduledate='" + scheduledate + '\'' +
                ", category='" + category + '\'' +
                ", remindtime='" + remindtime + '\'' +
                ", priority='" + priority + '\'' +
                ", starttime='" + starttime + '\'' +
                ", endtime='" + endtime + '\'' +
                ", tips='" + tips + '\'' +
                '}';
    }
}
