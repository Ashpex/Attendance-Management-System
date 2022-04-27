package utils;

public class checkinStats
{
    public static int ABSENT=0;
    public static int NOT_CHECKIN=1;
    private int status;
    public checkinStats()
    {
        status=-1;
    }

    public checkinStats(int state)
    {
        status=state;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
