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
    static int[][][] profits=new int[MONTHS][DAYS][COMMS];
    

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
  /*  public static void infoS() {
        for (int i = 0; i < 12; i++) {

            for(int j=0;j<28;j++){

                for(int l=0;l<5;l++){
                    System.out.print(profits[i][j][l]);
                    System.out.println();
                }

            }

        }
    } */


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
        int commIndex=-1;
        int total=0;
          if(from>to) return -99999;
          if(from<1||from>28||to>28) return -99999;

         for (int i = 0; i < 5; i++) {
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
        int maxProfit=0;
        int total;
        int dayIndex=0;
        if(month>=0&&month<12) {
            for (int i = 0; i < 28; i++) {
                total=0;
                for (int j = 0; j < 5; j++) {
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
        int maxProfit = 0;

        for (int i = 0; i < 5; i++) {
            if (commodities[i].equals(comm)) {
                commIndex = i;
            }
        }
        if (commIndex==-1) return  "INVALID_COMMODITY";

            for (int k = 0; k < 12; k++) {
                total = 0;
                for (int j = 0; j < 28; j++) {
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
        for (int i = 0; i < 5; i++) {
            if (commodities[i].equals(comm)) {
                commIndex = i;
            }
        }
        if(commIndex==-1) return -1;
        for(int m=0;m<12;m++){
            for(int d=0;d<28;d++){
                if(profits[m][d][commIndex]<0) {
                    streakDays++;
                    if(streakDays>longestStreakDays) {
                        longestStreakDays=streakDays;
                       // System.out.println(m+" "+d); // Hangi ayın hangi gunlerinde serinin arttigini görebilmek ve test etmek için.//
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

        for (int i = 0; i < 5; i++) {
            if (commodities[i].equals(comm)) {
                commIndex = i;
            }
        }
        if(commIndex==-1) return -1;

         for(int m=0;m<12;m++) {
        for(int d=0;d<28;d++) {
            if (profits[m][d][commIndex]>threshold) {
                daysAbove++;
              //  System.out.println(m+" "+d); //Hangi ayın hangi gununde degerin uzerine cikildigini gorup test etmek icin.//
            }
         }
        }
        return daysAbove;
    }

    public static int biggestDailySwing(int month) {
        int total1=0;
        int total2=0;
        int totalSwing=0;
        int biggestDiff=0;

        for(int i=0;i<5;i++){
            total1+=profits[month][0][i];
        }

        if(month>=0&&month<12){
            for(int d=1;d<28;d++){
                 total2=0;
                for(int c=0;c<5;c++){
                        total2 += profits[month][d][c];

                }
                if(total2>total1)  totalSwing=total2-total1;
                else totalSwing=total1-total2;

                total1=total2;

                if(totalSwing>biggestDiff) {
                    biggestDiff=totalSwing;
                   // System.out.println((d)+" "+(d+1)+" "+totalSwing);  //Hangi gunlerde guncellenen aradaki en yuksek farkı test etmek icin.//
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

        for (int i = 0; i < 5; i++) {
            if (commodities[i].equals(c1)) {
                index1 = i;
            }
        }
        for (int i = 0; i < 5; i++) {
            if (commodities[i].equals(c2)) {
                index2 = i;
            }
        }
        for(int m=0;m<12;m++) {
            for(int d=0;d<28;d++){
                c1Total+=profits[m][d][index1];
            }
        }
        for(int m=0;m<12;m++) {
            for(int d=0;d<28;d++){
                c2Total+=profits[m][d][index2];
            }
        }
        if(c1Total>c2Total) return c1+" is better by "+(c1Total-c2Total);
        else if(c2Total>c1Total) return c2+" is better by "+(c2Total-c1Total);
        else return "c1 is equal to c2.";

    }
    
    public static String bestWeekOfMonth(int month) {
       int weekIndex=0;
       int bestWIndex=-1;
       int total=0;
       int maxProfit=-999999;

       if(month>=0&&month<12) {
           for (int d = 0; d < 28; d++) {
               for (int c = 0; c < 5; c++) {
                   total += profits[month][d][c];
               }
               if ((d + 1) % 7 == 0) {
                   weekIndex++;
                //   System.out.println(total); //Hafta basi olan toplam karı görebilmek ve methodu test etmek icin.//
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

        //infoS(); //Yuklenen verilerin dogru sekilde yuklenip yuklenmedigini test edebilmek icin.//
                         //TESTS//
        System.out.println(mostProfitableCommodityInMonth(1));
        System.out.println(totalProfitOnDay(0,1));
        System.out.println(commodityProfitInRange("Gold",1,1));
        System.out.println(bestDayOfMonth(3));
        System.out.println(bestMonthForCommodity("Gold"));
        System.out.println(consecutiveLossDays("Gold"));
        System.out.println(daysAboveThreshold("Gold",5800));
        System.out.println(biggestDailySwing(0));
        System.out.println(compareTwoCommodities("Gold","Copper"));
        System.out.println(bestWeekOfMonth(0));
    }
}