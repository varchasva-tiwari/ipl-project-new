import com.sun.source.tree.Tree;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.Map;

public class IPLAnalysis {

    static int year, count, runs, conceded1, conceded2, overs;

    static String line, winner;
    static String[] matches, deliveries;

    static TreeMap<Integer, Integer> years = new TreeMap();
    static TreeMap<String, List<Integer>> economyBowlers = new TreeMap();
    static TreeMap<String, Integer> extraRuns = new TreeMap();
    static TreeMap<Integer, TreeMap<String, Integer>> winners = new TreeMap();
    static TreeMap<String, Integer> tm = new TreeMap();

    static List<Integer> idList1 = new ArrayList();
    static List<Integer> idList2 = new ArrayList();
    static List<Integer> bowlerList = new ArrayList();

    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new FileReader("csv_files/matches.csv"))) {
            br.readLine();
            while ((line = br.readLine()) != null) {
                matches = line.split(",");

                //First Part begin
                year = Integer.parseInt(matches[1]); //Year of match

                if (years.containsKey(year)) { //If year already present, increase count by 1
                    count = years.get(year);
                    years.put(year, count + 1);
                } else
                    years.put(year, 1); //If year not present till now, make count 1
                //First Part end

                //Second part begin
                winner = matches[10];

                if(winner.length() != 0) { //Checking if winner team name not empty
                    if (winners.containsKey(year)) //If year already present, update existing Map
                        tm = winners.get(year);

                    else
                        tm = new TreeMap();

                    if (tm.containsKey(winner)) {
                        count = tm.get(winner);
                        tm.put(winner, count + 1);
                    } else
                        tm.put(winner, 1);

                    winners.put(year,tm);
                }
                //Second part end

                if(year == 2016)
                    idList1.add(Integer.parseInt(matches[0])); //Id of all matches held in 2016

                if(year == 2015)
                    idList2.add(Integer.parseInt(matches[0])); //Id of all matches held in 2015
            }
        } catch (FileNotFoundException e) {
            System.out.println("Incorrect file path!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (BufferedReader br = new BufferedReader(new FileReader("csv_files/deliveries.csv"))) {
            br.readLine();
            while ((line = br.readLine()) != null) {
                deliveries = line.split(",");

                //Third part begin
                if(idList1.contains(Integer.parseInt(deliveries[0]))) { //If match year is same as 2016
                    String bowlTeam = deliveries[3];
                    conceded1 = Integer.parseInt(deliveries[16]);

                    if(extraRuns.containsKey(bowlTeam)) {
                        int extras = extraRuns.get(bowlTeam);
                        extraRuns.put(bowlTeam,extras+conceded1);
                    }

                    else
                        extraRuns.put(bowlTeam,conceded1);
                }
                //Third part end


                //Fourth part begin
                if(idList2.contains(Integer.parseInt(deliveries[0]))) {
                    String bowler = deliveries[8];
                    overs = Integer.parseInt(deliveries[4]);
                    conceded2 = 0;

                    for(int i=10;i<=16;i++)
                        conceded2 += Integer.parseInt(deliveries[i]);

                    if(economyBowlers.containsKey(bowler)) {
                        bowlerList = economyBowlers.get(bowler);
                        runs = bowlerList.get(0);

                        bowlerList.set(0, runs+conceded2);
                        bowlerList.set(1, overs);
                        bowlerList.set(2,(int)(bowlerList.get(0)/bowlerList.get(1))); //Calculating the economy of bowler = runs conceded/overs bowled
                    }

                    else {
                        bowlerList = new ArrayList();
                        bowlerList.add(conceded2);
                        bowlerList.add(overs);
                        bowlerList.add((int)(bowlerList.get(0)/bowlerList.get(1)));
                    }

                    economyBowlers.put(bowler,bowlerList);
                }
                //Fourth part end
            }
        } catch (FileNotFoundException e) {
            System.out.println("Incorrect file path!");
        } catch (Exception e) {
            e.printStackTrace();
        }


        System.out.println("PART 1\n\nYear : Number of matches played\n-------------------------------");

        for (Map.Entry<Integer, Integer> es : years.entrySet()) {
            System.out.println(es.getKey() + " : " + es.getValue());
        }
        System.out.println();

        System.out.println("PART 2");
        for (Map.Entry<Integer, TreeMap<String, Integer>> es : winners.entrySet()) {
            System.out.println("\nYear: " + es.getKey() + "\n");
            System.out.println("Team : Matches Won this year\n");
            TreeMap <String, Integer> tm = es.getValue();
            for (Map.Entry<String, Integer> entry : tm.entrySet()) {
                System.out.println(entry.getKey() + " : " + entry.getValue());
            }
        }
        System.out.println();

        System.out.println("PART 3\n");
        System.out.println("Bowling Team : Extra runs given in 2016\n");
        for (Map.Entry<String, Integer> es : extraRuns.entrySet()) {
            System.out.println(es.getKey() + " : " + es.getValue());
        }
        System.out.println();

       System.out.println("PART 4\n");
        for (Map.Entry<String, List<Integer>> es : economyBowlers.entrySet()) {
            System.out.println("Baller : "+es.getKey());
            idList1 = es.getValue();
            System.out.println("Runs conceded : "+idList1.get(0));
            System.out.println("Overs bowled : "+idList1.get(1));
            System.out.println("Economy : "+idList1.get(2)+"\n");
        }
        System.out.println();
    }
}