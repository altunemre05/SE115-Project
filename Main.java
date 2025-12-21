// Main.java — Students version
import java.io.*;
import java.util.*;
import java.nio.file.Paths;

public class Main {
    static final int MONTHS = 12;
    static final int DAYS = 28;
    static final int COMMS = 5;
    static String[] commodities = {"Gold", "Oil", "Silver", "Wheat", "Copper"};
    static String[] months = {"January","February","March","April","May","June",
            "July","August","September","October","November","December"};
    static int[][][] profits=new int[MONTHS][DAYS][COMMS];


    // ======== REQUIRED METHOD LOAD DATA (Students fill this) ========
    public static void loadData() {
        for (int m = 0; m < months.length; m++) {
            Scanner reader = null;

            try {
                reader = new Scanner(Paths.get("Data_Files/"+ months[m] + ".txt"));

                String line;
                line = reader.nextLine();

                while (reader.hasNextLine()) {
                    line = reader.nextLine();
                    String[] parts = line.split(",");

                        int day = Integer.parseInt(parts[0]) - 1;
                        String commodity = parts[1];
                        int profit = Integer.parseInt(parts[2]);
                        int cIndex = -1;

                        for (int i = 0; i < commodities.length; i++) {
                            if (commodities[i].equals(commodity)) {
                                cIndex = i;
                                break;
                            }
                        }
                        if (cIndex != -1 && day >= 0 && day < DAYS) {
                            profits[m][day][cIndex] = profit;
                        }
                    }
                }
                catch (Exception e) {

                }
                finally {
                    if (reader != null) {
                        reader.close();
                    }
                }
        }
    }

    // ======== 10 REQUIRED METHODS (Students fill these) ========

    public static String mostProfitableCommodityInMonth(int month) {
        int total;
        int maxProfit=-999999999;
        int index=-1;
        if(month>=0&&month<MONTHS) {
            for (int i = 0; i < COMMS; i++) {
                total = 0;
                for (int j = 0; j < DAYS; j++) {
                    total += profits[month][j][i];
                }
                if (total > maxProfit) {
                    maxProfit = total;
                    index = i;
                }
            }
            return commodities[index]+" "+maxProfit;
        }
        else return "INVALID_MONTH";
    }

    public static int totalProfitOnDay(int month, int day) {
        int totalProfit=0;
        if(month>=0&&month<12&&day>=1&&day<=28) {
            for (int i = 0; i < COMMS; i++) {
                totalProfit += profits[month][day-1][i];
            }
            return totalProfit;
        }
        else return -99999;

    }

    public static int commodityProfitInRange(String commodity, int from, int to) {
        int commIndex=-1;
        int total=0;

        if(from<1||from>28||to<1||to>28||from>to) return -99999;

        for (int i = 0; i < COMMS; i++) {
            if (commodities[i].equals(commodity)) {
                commIndex = i;
            }
        }
        if(commIndex==-1) return -99999;
        for(int m=0;m<12;m++) {
            for (int i = from - 1; i <= to - 1; i++) {
                total+=profits[m][i][commIndex];
            }
        }
        return total;
    }

    public static int bestDayOfMonth(int month) {
        int maxProfit=-999999;
        int total;
        int dayIndex=0;
        if(month>=0&&month<MONTHS) {
            for (int i = 0; i < DAYS; i++) {
                total=0;
                for (int j = 0; j < COMMS; j++) {
                    total += profits[month][i][j];
                }
                if (total > maxProfit) {
                    maxProfit = total;
                    dayIndex = i;
                }
            }
            return dayIndex+1;
        }
        else return -1;
    }

    public static String bestMonthForCommodity(String comm) {
        int commIndex = -1;
        int bestMonthIndex = -1;
        int total;
        int maxProfit =-999999;

        for (int i = 0; i < COMMS; i++) {
            if (commodities[i].equals(comm)) {
                commIndex = i;
            }
        }
        if (commIndex==-1) return  "INVALID_COMMODITY";

        for (int k = 0; k < MONTHS; k++) {
            total = 0;
            for (int j = 0; j < DAYS; j++) {
                total += profits[k][j][commIndex];
            }
            if (total > maxProfit) {
                maxProfit = total;
                bestMonthIndex = k;
            }
        }
        return months[bestMonthIndex];
    }

    public static int consecutiveLossDays(String comm) {
        int commIndex=-1;
        int streakDays=0;
        int longestStreakDays=0;
        for (int i = 0; i < COMMS; i++) {
            if (commodities[i].equals(comm)) {
                commIndex = i;
            }
        }
        if(commIndex==-1) return -1;
        for(int m=0;m<MONTHS;m++){
            for(int d=0;d<DAYS;d++){
                if(profits[m][d][commIndex]<0) {
                    streakDays++;
                    if(streakDays>longestStreakDays) {
                        longestStreakDays=streakDays;
                    }
                }
                else streakDays=0;
            }
        }
        return longestStreakDays;
    }

    public static int daysAboveThreshold(String comm, int threshold) {
        int daysAbove=0;
        int commIndex=-1;

        for (int i = 0; i < COMMS; i++) {
            if (commodities[i].equals(comm)) {
                commIndex = i;
            }
        }
        if(commIndex==-1) return -1;

        for(int m=0;m<MONTHS;m++) {
            for(int d=0;d<DAYS;d++) {
                if (profits[m][d][commIndex]>threshold) {
                    daysAbove++;
                }
            }
        }
        return daysAbove;
    }

    public static int biggestDailySwing(int month) {
        int total1=0;
        int total2;
        int totalSwing;
        int biggestDiff=0;


        if(month>=0&&month<MONTHS){
            for(int i=0;i<COMMS;i++){
                total1+=profits[month][0][i];
            }

            for(int d=1;d<DAYS;d++){
                total2=0;
                for(int c=0;c<COMMS;c++){
                    total2 += profits[month][d][c];

                }
                if(total2>total1)  totalSwing=total2-total1;
                else totalSwing=total1-total2;

                total1=total2;

                if(totalSwing>biggestDiff) {
                    biggestDiff=totalSwing;
                }

            }

            return biggestDiff;
        }
        else return -99999;
    }

    public static String compareTwoCommodities(String c1, String c2) {
        int index1=-1;
        int index2=-1;
        int c1Total=0;
        int c2Total=0;

            for (int i = 0; i < COMMS; i++) {
                if (commodities[i].equals(c1)) {
                    index1 = i;
                }
            }
            for (int i = 0; i < COMMS; i++) {
                if (commodities[i].equals(c2)) {
                    index2 = i;
                }
            }

            if(index1!=-1&&index2!=-1) {
                for (int m = 0; m < MONTHS; m++) {
                    for (int d = 0; d < DAYS; d++) {
                        c1Total += profits[m][d][index1];
                    }
                }
                for (int m = 0; m < MONTHS; m++) {
                    for (int d = 0; d < DAYS; d++) {
                        c2Total += profits[m][d][index2];
                    }
                }
                if (c1Total > c2Total) return c1 + " is better by " + (c1Total - c2Total);
                else if (c2Total > c1Total) return c2 + " is better by " + (c2Total - c1Total);
                else return  c1+" is equal to "+c2;
            }
            else return  "INVALID_COMMODITY";
    }

    public static String bestWeekOfMonth(int month) {
        int weekIndex=0;
        int bestWIndex=-1;
        int total=0;
        int maxProfit=-99999999;

        if(month>=0&&month<MONTHS) {
            for (int d = 0; d < DAYS; d++) {
                for (int c = 0; c < COMMS; c++) {
                    total += profits[month][d][c];
                }
                if ((d + 1) % 7 == 0) {
                    weekIndex++;
                    if (total > maxProfit) {
                        maxProfit = total;
                        bestWIndex = weekIndex;
                    }
                    total = 0;
                }
            }
            return "Week "+bestWIndex+" "+maxProfit;
        }
        else return "INVALID_MONTH";
    }

    public static void main(String[] args) {
        loadData();
        System.out.println("Data loaded – ready for queries");

    }
}