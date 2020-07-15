import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {

    private static String lineOfFile;
    private static String winner;
    private static String bowlerName;
    private static String bowlTeam;

    private static List<Match> matches = new ArrayList();
    private static List<Delivery> deliveries = new ArrayList();

    private static List<Integer> yearIdList = new ArrayList();
    private static List<Integer> bowlerStats = new ArrayList();

    private static int year;
    private static int countOfMatches;
    private static int matchId;
    private static int runsConcededPerTeam;
    private static int extraRuns;
    private static int runsConcededPerBowlerOld;
    private static int runsConcededPerBowlerNew;
    private static int ballsBowled;
    private static int oversBowled;

    private static final int SPECIFIED_YEAR = 2017;
    private static final int FIRST_MATCH_PLAYED = 1;
    private static final int FIRST_MATCH_WON = 1;

    private static final int FIRST_BALL = 1;
    private static final int NO_OVERS_TILL_NOW = 0;
    private static final int NO_ECONOMY_TILL_NOW = 0;
    private static final int OVER_ENDED = 0;

    private static final int RUNS = 0;
    private static final int BALLS = 1;
    private static final int OVERS = 2;
    private static final int ECONOMY = 3;
    private static final int TOTAL_BALLS_IN_OVER = 6;

    private static TreeMap<Integer, Integer> numberOfMatchesPlayedPerYear = new TreeMap();
    private static TreeMap<Integer, TreeMap<String, Integer>> numberOfMatchesWonAllYears = new TreeMap();
    private static TreeMap<String, Integer> numberOfMatchesWonThisYear = new TreeMap();
    private static TreeMap<String, Integer> extraRunsPerTeam = new TreeMap();
    private static TreeMap<String, List<Integer>> economyOfBowlers = new TreeMap();
    private static LinkedHashMap<String, List<Integer>> topEconomicalBowlers = new LinkedHashMap();

    public static void main(String[] args) {

        getMatchesData();
        getDeliveriesData();

        getNumberOfMatchesPlayedPerYear();
        getNumberOfMatchesWonAllYears();

        for (Match match : matches) {
            if (match.getYear() == SPECIFIED_YEAR)
                yearIdList.add(match.getId());
        }

        getExtraRunsPerTeam();
        getTopEconomicalBowlers();
    }

    private static void getMatchesData() {
        try (BufferedReader br = new BufferedReader(new FileReader("csv_files/matches.csv"))) {
            br.readLine();
            while ((lineOfFile = br.readLine()) != null) {
                String matchData[] = lineOfFile.split(",");

                Match match = new Match();

                match.setId(Integer.parseInt(matchData[0]));
                match.setYear(Integer.parseInt(matchData[1]));
                match.setCity(matchData[2]);
                match.setDate(matchData[3]);
                match.setTeam1(matchData[4]);
                match.setTeam2(matchData[5]);
                match.setTossWinner(matchData[6]);
                match.setTossDecision(matchData[7]);
                match.setResult(matchData[8]);
                match.setDlApplied(Integer.parseInt(matchData[9]));
                match.setWinner(matchData[10]);
                match.setWinByRuns(Integer.parseInt(matchData[11]));
                match.setWinByWickets(Integer.parseInt(matchData[12]));

                matches.add(match);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found at specified location!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void getDeliveriesData() {
        try (BufferedReader br = new BufferedReader(new FileReader("csv_files/deliveries.csv"))) {
            br.readLine();
            while ((lineOfFile = br.readLine()) != null) {
                String deliveryData[] = lineOfFile.split(",");

                Delivery delivery = new Delivery();

                delivery.setId(Integer.parseInt(deliveryData[0]));
                delivery.setInning(Integer.parseInt(deliveryData[1]));
                delivery.setBattingTeam(deliveryData[2]);
                delivery.setBowlingTeam(deliveryData[3]);
                delivery.setOver(Integer.parseInt(deliveryData[4]));
                delivery.setBall(Integer.parseInt(deliveryData[5]));
                delivery.setBatsman(deliveryData[6]);
                delivery.setNonStriker(deliveryData[7]);
                delivery.setBowler(deliveryData[8]);
                delivery.setIsSuperOver(Integer.parseInt(deliveryData[9]));
                delivery.setWideRuns(Integer.parseInt(deliveryData[10]));
                delivery.setByeRuns(Integer.parseInt(deliveryData[11]));
                delivery.setLegByeRuns(Integer.parseInt(deliveryData[12]));
                delivery.setNoBallRuns(Integer.parseInt(deliveryData[13]));
                delivery.setPenaltyRuns(Integer.parseInt(deliveryData[14]));
                delivery.setBastmanRuns(Integer.parseInt(deliveryData[15]));
                delivery.setExtraRuns(Integer.parseInt(deliveryData[16]));
                delivery.setTotalRuns(Integer.parseInt(deliveryData[17]));

                deliveries.add(delivery);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found at specified location!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void getNumberOfMatchesPlayedPerYear() {
        for (Match match : matches) {
            year = match.getYear();

            if (numberOfMatchesPlayedPerYear.containsKey(year)) {
                countOfMatches = numberOfMatchesPlayedPerYear.get(year);
                numberOfMatchesPlayedPerYear.put(year, countOfMatches + 1);
            } else
                numberOfMatchesPlayedPerYear.put(year, FIRST_MATCH_PLAYED);
        }

        System.out.println("PART 1\n\nYear : Number of matches played\n");

        for (Map.Entry<Integer, Integer> es : numberOfMatchesPlayedPerYear.entrySet()) {
            System.out.println(es.getKey() + " : " + es.getValue());
        }

        System.out.println();
    }

    private static void getNumberOfMatchesWonAllYears() {
        for (Match match : matches) {
            winner = match.getWinner();

            if (winner.length() != 0) {
                year = match.getYear();
                if (numberOfMatchesWonAllYears.containsKey(year))
                    numberOfMatchesWonThisYear = numberOfMatchesWonAllYears.get(year);

                else
                    numberOfMatchesWonThisYear = new TreeMap();

                if (numberOfMatchesWonThisYear.containsKey(winner)) {
                    countOfMatches = numberOfMatchesWonThisYear.get(winner);
                    numberOfMatchesWonThisYear.put(winner, countOfMatches + 1);
                } else
                    numberOfMatchesWonThisYear.put(winner, FIRST_MATCH_WON);

                numberOfMatchesWonAllYears.put(year, numberOfMatchesWonThisYear);
            }
        }

        System.out.println("PART 2\n");
        for (Map.Entry<Integer, TreeMap<String, Integer>> es : numberOfMatchesWonAllYears.entrySet()) {
            System.out.println("Year: " + es.getKey() + "\n");
            System.out.println("Team : Matches Won this year\n");
            TreeMap<String, Integer> tm = es.getValue();
            for (Map.Entry<String, Integer> entry : tm.entrySet()) {
                System.out.println(entry.getKey() + " : " + entry.getValue());
            }
        }

        System.out.println();
    }

    private static void getExtraRunsPerTeam() {
        for (Delivery delivery : deliveries) {
            matchId = delivery.getId();

            if (yearIdList.contains(matchId)) {
                bowlTeam = delivery.getBowlingTeam();
                runsConcededPerTeam = delivery.getExtraRuns();

                if (extraRunsPerTeam.containsKey(bowlTeam)) {
                    extraRuns = extraRunsPerTeam.get(bowlTeam);
                    extraRunsPerTeam.put(bowlTeam, extraRuns + runsConcededPerTeam);
                } else
                    extraRunsPerTeam.put(bowlTeam, runsConcededPerTeam);
            }
        }

        System.out.println("PART 3\n\nBowling Team : Extra runs given in " + SPECIFIED_YEAR + "\n");
        for (Map.Entry<String, Integer> es : extraRunsPerTeam.entrySet()) {
            System.out.println(es.getKey() + " : " + es.getValue());
        }

        System.out.println();
    }

    private static void getTopEconomicalBowlers() {
        for (Delivery delivery : deliveries) {
            matchId = delivery.getId();

            if (yearIdList.contains(matchId)) {
                bowlerName = delivery.getBowler();

                runsConcededPerBowlerNew = delivery.getWideRuns() + delivery.getByeRuns() + delivery.getLegByeRuns() + delivery.getNoBallRuns() + delivery.getPenaltyRuns() + delivery.getBastmanRuns() + delivery.getExtraRuns();

                if (economyOfBowlers.containsKey(bowlerName)) {
                    bowlerStats = economyOfBowlers.get(bowlerName);

                    runsConcededPerBowlerOld = bowlerStats.get(RUNS);

                    ballsBowled = bowlerStats.get(BALLS);

                    oversBowled = bowlerStats.get(OVERS);

                    bowlerStats.set(RUNS, runsConcededPerBowlerOld + runsConcededPerBowlerNew);

                    ballsBowled += 1;

                    if (delivery.getNoBallRuns() > 0 || delivery.getWideRuns() > 0)
                        ballsBowled -= 1;

                    bowlerStats.set(BALLS, ballsBowled);

                    if (ballsBowled == TOTAL_BALLS_IN_OVER) {
                        bowlerStats.set(BALLS, OVER_ENDED);
                        bowlerStats.set(OVERS, oversBowled + 1);
                        bowlerStats.set(ECONOMY, (int) (bowlerStats.get(RUNS) / bowlerStats.get(OVERS)));
                    }

                } else {
                    bowlerStats = new ArrayList();
                    bowlerStats.add(runsConcededPerBowlerNew);
                    bowlerStats.add(FIRST_BALL);
                    bowlerStats.add(NO_OVERS_TILL_NOW);
                    bowlerStats.add(NO_ECONOMY_TILL_NOW);
                }

                economyOfBowlers.put(bowlerName, bowlerStats);
            }
        }

        Comparator<Map.Entry<String, List<Integer>>> economyComparator = new Comparator<Map.Entry<String, List<Integer>>>() {
            @Override
            public int compare(Map.Entry<String, List<Integer>> e1, Map.Entry<String, List<Integer>> e2) {
                return e1.getValue().get(3) - e2.getValue().get(3);
            }
        };

        Set<Map.Entry<String, List<Integer>>> economyEntries = economyOfBowlers.entrySet();

        List<Map.Entry<String, List<Integer>>> economicalBowlersList = new ArrayList(economyEntries);

        Collections.sort(economicalBowlersList, economyComparator);

        for (Map.Entry<String, List<Integer>> entry : economicalBowlersList) {
            topEconomicalBowlers.put(entry.getKey(), entry.getValue());
        }

        System.out.println("PART 4\n");
        for (Map.Entry<String, List<Integer>> es : topEconomicalBowlers.entrySet()) {
            System.out.println("Baller : " + es.getKey());
            bowlerStats = es.getValue();
            System.out.println("Runs conceded : " + bowlerStats.get(RUNS));
            System.out.println("Overs bowled : " + bowlerStats.get(OVERS));
            System.out.println("Economy : " + bowlerStats.get(ECONOMY) + "\n");
        }
        System.out.println();
    }
}