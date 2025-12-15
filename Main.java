// Main.java — Students version
import java.io.*;
import java.util.*;

public class Main {
    static final int MONTHS = 12;
    static final int DAYS = 28;
    static final int COMMS = 5;
    static String[] commodities = {"Gold", "Oil", "Silver", "Wheat", "Copper"};
    static String[] months = {"January","February","March","April","May","June",
                              "July","August","September","October","November","December"};
    static int[][][] profits=new int[12][28][5];
    

    // ======== REQUIRED METHOD LOAD DATA (Students fill this) ========
    public static void loadData() {
        for(int m=0;m<months.length;m++) {
            try {
                FileReader fr = new FileReader("Data_Files/" + months[m] + ".txt");
                BufferedReader br = new BufferedReader(fr);
                String line;
                line=br.readLine();
                while((line=br.readLine())!=null){
                    String[] parts=line.split(",");

                    int day = Integer.parseInt(parts[0])-1;
                    String commodity = parts[1];
                    int profit = Integer.parseInt(parts[2]);
                    int cIndex = -1;

                    for (int i = 0; i < commodities.length; i++) {
                        if (commodities[i].equals(commodity)) {
                            cIndex = i;
                            break;
                        }
                    }
                    if (cIndex != -1 && day >= 0 && day < 28) {
                        profits[m][day][cIndex] = profit;
                    }
                }
                br.close();
            }
            catch (Exception e) {
             System.out.println("Error.");

            }
        }
    }
    public static void infoS() {
        for (int i = 0; i < 12; i++) {

            for(int j=0;j<28;j++){

                for(int l=0;l<5;l++){
                    System.out.print(profits[i][j][l]);
                    System.out.println();
                }

            }

        }
    }


    // ======== 10 REQUIRED METHODS (Students fill these) ========

    public static String mostProfitableCommodityInMonth(int month) {
        int total;
        int maxProfit=0;
        int index=-1;
        if(month>=0&&month<12) {
            for (int i = 0; i < 5; i++) {
                total = 0;
                for (int j = 0; j < 28; j++) {
                    total += profits[month][j][i];
                }
                if (total > maxProfit) {
                    maxProfit = total;
                    index = i;
                }
            }
            return commodities[index]+" "+maxProfit;
        }
        else {
            return "INVALID_MONTH";
        }
    }

    public static int totalProfitOnDay(int month, int day) {
        int totalProfit=0;
        if(month>=0&&month<12&&day>=1&&day<=28) {
            for (int i = 0; i < 5; i++) {
                totalProfit += profits[month][day-1][i];
            }
            return totalProfit;
        }
        else return -99999;

    }

    public static int commodityProfitInRange(String commodity, int from, int to) {
        return 1234;
    }

    public static int bestDayOfMonth(int month) { 
        return 1234; 
    }
    
    public static String bestMonthForCommodity(String comm) { 
        return "DUMMY"; 
    }

    public static int consecutiveLossDays(String comm) { 
        return 1234; 
    }
    
    public static int daysAboveThreshold(String comm, int threshold) { 
        return 1234; 
    }

    public static int biggestDailySwing(int month) { 
        return 1234; 
    }
    
    public static String compareTwoCommodities(String c1, String c2) { 
        return "DUMMY is better by 1234"; 
    }
    
    public static String bestWeekOfMonth(int month) { 
        return "DUMMY"; 
    }

    public static void main(String[] args) {
        loadData();
        System.out.println("Data loaded – ready for queries");
        //infoS();
        System.out.println(mostProfitableCommodityInMonth(1));
        System.out.println(totalProfitOnDay(0,1));
    }
}