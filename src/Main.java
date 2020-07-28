import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

public class Main {
    private static String lineOfFile;
    private static int year;
    private static int countOfMatches;
    private static int matchId;

    private static final int MATCH_ID = 0;
    private static final int MATCH_YEAR = 1;
    private static final int MATCH_CITY = 2;
    private static final int MATCH_DATE = 3;
    private static final int MATCH_TEAM1 = 4;
    private static final int MATCH_TEAM2 = 5;
    private static final int MATCH_TOSS_WINNER = 6;
    private static final int MATCH_TOSS_DECISION = 7;
    private static final int MATCH_RESULT = 8;
    private static final int MATCH_DL_APPLIED = 9;
    private static final int MATCH_WINNER = 10;
    private static final int MATCH_WIN_BY_RUNS = 11;
    private static final int MATCH_WIN_BY_WICKETS = 12;
    private static final int MATCH_PLAYER_OF_MATCH = 13;
    private static final int MATCH_VENUE = 14;
    private static final int MATCH_UMPIRE1 = 15;
    private static final int MATCH_UMPIRE2 = 16;
    private static final int MATCH_UMPIRE3 = 17;
    private static final int MATCH_FIRST_MATCH_PLAYED = 1;
    private static final int MATCH_FIRST_MATCH_WON = 1;
    private static final int DELIVERY_ID = 0;
    private static final int DELIVERY_INNING = 1;
    private static final int DELIVERY_BATTING_TEAM = 2;
    private static final int DELIVERY_BOWLING_TEAM = 3;
    private static final int DELIVERY_OVER = 4;
    private static final int DELIVERY_BALL = 5;
    private static final int DELIVERY_BATSMAN = 6;
    private static final int DELIVERY_NON_STRIKER = 7;
    private static final int DELIVERY_BOWLER = 8;
    private static final int DELIVERY_IS_SUPER_OVER = 9;
    private static final int DELIVERY_WIDE_RUNS = 10;
    private static final int DELIVERY_BYE_RUNS = 11;
    private static final int DELIVERY_LEG_BYE_RUNS = 12;
    private static final int DELIVERY_NO_BALL = 13;
    private static final int DELIVERY_PENALTY_RUNS = 14;
    private static final int DELIVERY_BATSMAN_RUNS = 15;
    private static final int DELIVERY_EXTRA_RUNS = 16;
    private static final int DELIVERY_TOTAL_RUNS = 17;
    private static final int DELIVERY_PLAYER_DISMISSED = 18;
    private static final int DELIVERY_DISMISSAL_KIND = 19;
    private static final int DELIVERY_FIELDER = 20;
    private static final int DELIVERY_FIRST_BALL = 1;
    private static final int DELIVERY_NO_OVERS_TILL_NOW = 0;
    private static final int DELIVERY_NO_ECONOMY_TILL_NOW = 0;
    private static final int DELIVERY_OVER_ENDED = 0;
    private static final int DELIVERY_RUNS = 0;
    private static final int DELIVERY_BALLS = 1;
    private static final int DELIVERY_OVERS = 2;
    private static final int DELIVERY_ECONOMY = 3;
    private static final int DELIVERY_TOTAL_BALLS_IN_OVER = 6;

    public static void main(String[] args) {
        List<Match> matches = getMatchesData();
        List<Delivery> deliveries = getDeliveriesData();

        findNumberOfMatchesPlayedPerYear(matches);
        findNumberOfMatchesWonAllYears(matches);

        findExtraRunsPerTeam(matches, deliveries);
        findTopEconomicalBowlers(matches, deliveries);
    }

    private static List<Match> getMatchesData() {
        List<Match> matches = new ArrayList();

        try (BufferedReader br = new BufferedReader(new FileReader("csv_files/matches.csv"))) {
            br.readLine();
            while ((lineOfFile = br.readLine()) != null) {
                String matchData[] = lineOfFile.split(",");

                Match match = new Match();

                match.setId(Integer.parseInt(matchData[MATCH_ID]));
                match.setYear(Integer.parseInt(matchData[MATCH_YEAR]));
                match.setCity(matchData[MATCH_CITY]);
                match.setDate(matchData[MATCH_DATE]);
                match.setTeam1(matchData[MATCH_TEAM1]);
                match.setTossWinner(matchData[MATCH_TOSS_WINNER]);
                match.setTossDecision(matchData[MATCH_TOSS_DECISION]);
                match.setResult(matchData[MATCH_RESULT]);
                match.setDlApplied(Integer.parseInt(matchData[MATCH_DL_APPLIED]));
                match.setWinner(matchData[MATCH_WINNER]);
                match.setWinByRuns(Integer.parseInt(matchData[MATCH_WIN_BY_RUNS]));
                match.setWinByWickets(Integer.parseInt(matchData[MATCH_WIN_BY_WICKETS]));

                matches.add(match);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return matches;
    }

    private static List<Delivery> getDeliveriesData() {
        List<Delivery> deliveries = new ArrayList();

        try (BufferedReader br = new BufferedReader(new FileReader("csv_files/deliveries.csv"))) {
            br.readLine();
            while ((lineOfFile = br.readLine()) != null) {
                String deliveryData[] = lineOfFile.split(",");

                Delivery delivery = new Delivery();

                delivery.setId(Integer.parseInt(deliveryData[DELIVERY_ID]));
                delivery.setInning(Integer.parseInt(deliveryData[DELIVERY_INNING]));
                delivery.setBattingTeam(deliveryData[DELIVERY_BATTING_TEAM]);
                delivery.setBowlingTeam(deliveryData[DELIVERY_BOWLING_TEAM]);
                delivery.setOver(Integer.parseInt(deliveryData[DELIVERY_OVER]));
                delivery.setBall(Integer.parseInt(deliveryData[DELIVERY_BALL]));
                delivery.setBatsman(deliveryData[DELIVERY_BATSMAN]);
                delivery.setNonStriker(deliveryData[DELIVERY_NON_STRIKER]);
                delivery.setBowler(deliveryData[DELIVERY_BOWLER]);
                delivery.setIsSuperOver(Integer.parseInt(deliveryData[DELIVERY_IS_SUPER_OVER]));
                delivery.setWideRuns(Integer.parseInt(deliveryData[DELIVERY_WIDE_RUNS]));
                delivery.setByeRuns(Integer.parseInt(deliveryData[DELIVERY_BYE_RUNS]));
                delivery.setLegByeRuns(Integer.parseInt(deliveryData[DELIVERY_LEG_BYE_RUNS]));
                delivery.setNoBallRuns(Integer.parseInt(deliveryData[DELIVERY_NO_BALL]));
                delivery.setPenaltyRuns(Integer.parseInt(deliveryData[DELIVERY_PENALTY_RUNS]));
                delivery.setBatsmanRuns(Integer.parseInt(deliveryData[DELIVERY_BATSMAN_RUNS]));
                delivery.setExtraRuns(Integer.parseInt(deliveryData[DELIVERY_EXTRA_RUNS]));
                delivery.setTotalRuns(Integer.parseInt(deliveryData[DELIVERY_TOTAL_RUNS]));

                deliveries.add(delivery);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return deliveries;
    }

    private static void findNumberOfMatchesPlayedPerYear(List<Match> matches) {
        TreeMap<Integer, Integer> numberOfMatchesPlayedPerYear = new TreeMap();

        for (Match match : matches) {
            year = match.getYear();

            if (numberOfMatchesPlayedPerYear.containsKey(year)) {
                countOfMatches = numberOfMatchesPlayedPerYear.get(year);
                numberOfMatchesPlayedPerYear.put(year, countOfMatches + 1);
            } else
                numberOfMatchesPlayedPerYear.put(year, MATCH_FIRST_MATCH_PLAYED);
        }

        System.out.println("PART 1\n\nYear : Number of matches played\n");
        for (Map.Entry<Integer, Integer> es : numberOfMatchesPlayedPerYear.entrySet()) {
            System.out.println(es.getKey() + " : " + es.getValue());
        }

        System.out.println();
    }

    private static void findNumberOfMatchesWonAllYears(List<Match> matches) {
        String winner = "";
        TreeMap<String, Integer> numberOfMatchesWonThisYear = new TreeMap();
        TreeMap<Integer, TreeMap<String, Integer>> numberOfMatchesWonAllYears = new TreeMap();

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
                    numberOfMatchesWonThisYear.put(winner, MATCH_FIRST_MATCH_WON);

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

    private static List<Integer> getYearId(List<Match> matches, int year) {
        List<Integer> yearIdList = new ArrayList();

        for (Match match : matches) {
            if (match.getYear() == year)
                yearIdList.add(match.getId());
        }
        return yearIdList;
    }

    private static void findExtraRunsPerTeam(List<Match> matches, List<Delivery> deliveries) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter year for which stats are required: ");

        int year = sc.nextInt();
        int runsConcededPerTeam = 0;
        int extraRuns = 0;
        String bowlTeam = "";
        List<Integer> yearIdList = getYearId(matches, year);
        TreeMap<String, Integer> extraRunsPerTeam = new TreeMap();

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

        System.out.println("\nPART 3\n\nBowling Team : Extra runs given in " + year + "\n");
        for (Map.Entry<String, Integer> es : extraRunsPerTeam.entrySet()) {
            System.out.println(es.getKey() + " : " + es.getValue());
        }

        System.out.println();
    }

    private static void findTopEconomicalBowlers(List<Match> matches, List<Delivery> deliveries) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter year for which stats are required: ");

        int year = sc.nextInt();
        int runsConcededPerBowlerOld = 0;
        int runsConcededPerBowlerNew = 0;
        int ballsBowled = 0;
        int oversBowled = 0;
        String bowlerName = "";
        List<Integer> yearIdList = getYearId(matches, year);
        List<Integer> bowlerStats = new ArrayList();
        LinkedHashMap<String, List<Integer>> topEconomicalBowlers = new LinkedHashMap();
        TreeMap<String, List<Integer>> economyOfBowlers = new TreeMap();

        for (Match match : matches) {
            if (match.getYear() == year)
                yearIdList.add(match.getId());
        }

        for (Delivery delivery : deliveries) {
            matchId = delivery.getId();

            if (yearIdList.contains(matchId)) {
                bowlerName = delivery.getBowler();

                runsConcededPerBowlerNew = delivery.getWideRuns() + delivery.getByeRuns() + delivery.getLegByeRuns() + delivery.getNoBallRuns() + delivery.getPenaltyRuns() + delivery.getBatsmanRuns() + delivery.getExtraRuns();

                if (economyOfBowlers.containsKey(bowlerName)) {
                    bowlerStats = economyOfBowlers.get(bowlerName);

                    runsConcededPerBowlerOld = bowlerStats.get(DELIVERY_RUNS);

                    ballsBowled = bowlerStats.get(DELIVERY_BALLS);

                    oversBowled = bowlerStats.get(DELIVERY_OVERS);

                    bowlerStats.set(DELIVERY_RUNS, runsConcededPerBowlerOld + runsConcededPerBowlerNew);

                    ballsBowled += 1;

                    if (delivery.getNoBallRuns() > 0 || delivery.getWideRuns() > 0)
                        ballsBowled -= 1;

                    bowlerStats.set(DELIVERY_BALLS, ballsBowled);

                    if (ballsBowled == DELIVERY_TOTAL_BALLS_IN_OVER) {
                        bowlerStats.set(DELIVERY_BALLS, DELIVERY_OVER_ENDED);
                        bowlerStats.set(DELIVERY_OVERS, oversBowled + 1);
                        bowlerStats.set(DELIVERY_ECONOMY, (int) (bowlerStats.get(DELIVERY_RUNS) / bowlerStats.get(DELIVERY_OVERS)));
                    }

                } else {
                    bowlerStats = new ArrayList();
                    bowlerStats.add(runsConcededPerBowlerNew);
                    bowlerStats.add(DELIVERY_FIRST_BALL);
                    bowlerStats.add(DELIVERY_NO_OVERS_TILL_NOW);
                    bowlerStats.add(DELIVERY_NO_ECONOMY_TILL_NOW);
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

        System.out.println("\nPART 4\n");
        for (Map.Entry<String, List<Integer>> es : topEconomicalBowlers.entrySet()) {
            System.out.println("Baller : " + es.getKey());
            bowlerStats = es.getValue();
            System.out.println("Runs conceded : " + bowlerStats.get(DELIVERY_RUNS));
            System.out.println("Overs bowled : " + bowlerStats.get(DELIVERY_OVERS));
            System.out.println("Economy : " + bowlerStats.get(DELIVERY_ECONOMY) + "\n");
        }
        System.out.println();
    }
}