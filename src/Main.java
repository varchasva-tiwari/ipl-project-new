import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class Main {

    private static final String MATCH_ID = "id";
    private static final String MATCH_YEAR = "season";
    private static final String MATCH_CITY = "city";
    private static final String MATCH_DATE = "date";
    private static final String MATCH_TEAM1 = "team1";
    private static final String MATCH_TEAM2 = "team2";
    private static final String MATCH_TOSS_WINNER = "toss_winner";
    private static final String MATCH_TOSS_DECISION = "toss_decision";
    private static final String MATCH_RESULT = "result";
    private static final String MATCH_DL_APPLIED = "dl_applied";
    private static final String MATCH_WINNER = "winner";
    private static final String MATCH_WIN_BY_RUNS = "win_by_runs";
    private static final String MATCH_WIN_BY_WICKETS = "win_by_wickets";
    private static final String MATCH_PLAYER_OF_MATCH = "player_of_match";
    private static final String MATCH_VENUE = "venue";
    private static final String MATCH_UMPIRE1 = "umpire1";
    private static final String MATCH_UMPIRE2 = "umpire2";
    private static final String MATCH_UMPIRE3 = "umpire3";

    private static final int MATCH_FIRST_MATCH_PLAYED = 1;
    private static final int MATCH_FIRST_MATCH_WON = 1;

    private static final String DELIVERY_ID = "match_id";
    private static final String DELIVERY_INNING = "inning";
    private static final String DELIVERY_BATTING_TEAM = "batting_team";
    private static final String DELIVERY_BOWLING_TEAM = "bowling_team";
    private static final String DELIVERY_OVER = "over";
    private static final String DELIVERY_BALL = "ball";
    private static final String DELIVERY_BATSMAN = "batsman";
    private static final String DELIVERY_NON_STRIKER = "non_striker";
    private static final String DELIVERY_BOWLER = "bowler";
    private static final String DELIVERY_IS_SUPER_OVER = "is_super_over";
    private static final String DELIVERY_WIDE_RUNS = "wide_runs";
    private static final String DELIVERY_BYE_RUNS = "bye_runs";
    private static final String DELIVERY_LEG_BYE_RUNS = "legbye_runs";
    private static final String DELIVERY_NO_BALL = "noball_runs";
    private static final String DELIVERY_PENALTY_RUNS = "penalty_runs";
    private static final String DELIVERY_BATSMAN_RUNS = "batsman_runs";
    private static final String DELIVERY_EXTRA_RUNS = "extra_runs";
    private static final String DELIVERY_TOTAL_RUNS = "total_runs";
    private static final String DELIVERY_PLAYER_DISMISSED = "player_dismissed";
    private static final String DELIVERY_DISMISSAL_KIND = "dismissal_kind";
    private static final String DELIVERY_FIELDER = "fielder";

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

        try(Connection dbConnection = ConnectionUtil.getConnection()) {

            String getMatchesQuery = "SELECT *FROM matches";
            Statement getMatches = dbConnection.createStatement();
            ResultSet MatchesData = getMatches.executeQuery(getMatchesQuery);

            while(MatchesData.next()) {
                Match match = new Match();

                match.setId(MatchesData.getInt(MATCH_ID));
                match.setYear(MatchesData.getInt(MATCH_YEAR));
                match.setCity(MatchesData.getString(MATCH_CITY));
                match.setDate(MatchesData.getString(MATCH_DATE));
                match.setTeam1(MatchesData.getString(MATCH_TEAM1));
                match.setTossWinner(MatchesData.getString(MATCH_TOSS_WINNER));
                match.setTossDecision(MatchesData.getString(MATCH_TOSS_DECISION));
                match.setResult(MatchesData.getString(MATCH_RESULT));
                match.setDlApplied(MatchesData.getInt(MATCH_DL_APPLIED));
                match.setWinner(MatchesData.getString(MATCH_WINNER));
                match.setWinByRuns(MatchesData.getInt(MATCH_WIN_BY_RUNS));
                match.setWinByWickets(MatchesData.getInt(MATCH_WIN_BY_WICKETS));

                matches.add(match);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return matches;
    }

    private static List<Delivery> getDeliveriesData() {
        List<Delivery> deliveries = new ArrayList();

        try(Connection dbConnection = ConnectionUtil.getConnection()) {
            String getDeliveriesQuery = "SELECT *FROM deliveries";
            Statement getDeliveries = dbConnection.createStatement();
            ResultSet DeliveriesData = getDeliveries.executeQuery(getDeliveriesQuery);

            while(DeliveriesData.next()) {
                Delivery delivery = new Delivery();

                delivery.setId(DeliveriesData.getInt(DELIVERY_ID));
                delivery.setInning(DeliveriesData.getInt(DELIVERY_INNING));
                delivery.setBattingTeam(DeliveriesData.getString(DELIVERY_BATTING_TEAM));
                delivery.setBowlingTeam(DeliveriesData.getString(DELIVERY_BOWLING_TEAM));
                delivery.setOver(DeliveriesData.getInt(DELIVERY_OVER));
                delivery.setBall(DeliveriesData.getInt(DELIVERY_BALL));
                delivery.setBatsman(DeliveriesData.getString(DELIVERY_BATSMAN));
                delivery.setNonStriker(DeliveriesData.getString(DELIVERY_NON_STRIKER));
                delivery.setBowler(DeliveriesData.getString(DELIVERY_BOWLER));
                delivery.setIsSuperOver(DeliveriesData.getInt(DELIVERY_IS_SUPER_OVER));
                delivery.setWideRuns(DeliveriesData.getInt(DELIVERY_WIDE_RUNS));
                delivery.setByeRuns(DeliveriesData.getInt(DELIVERY_BYE_RUNS));
                delivery.setLegByeRuns(DeliveriesData.getInt(DELIVERY_LEG_BYE_RUNS));
                delivery.setNoBallRuns(DeliveriesData.getInt(DELIVERY_NO_BALL));
                delivery.setPenaltyRuns(DeliveriesData.getInt(DELIVERY_PENALTY_RUNS));
                delivery.setBatsmanRuns(DeliveriesData.getInt(DELIVERY_BATSMAN_RUNS));
                delivery.setExtraRuns(DeliveriesData.getInt(DELIVERY_EXTRA_RUNS));
                delivery.setTotalRuns(DeliveriesData.getInt(DELIVERY_TOTAL_RUNS));

                deliveries.add(delivery);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return deliveries;
    }

    private static void findNumberOfMatchesPlayedPerYear(List<Match> matches) {
        TreeMap<Integer, Integer> numberOfMatchesPlayedPerYear = new TreeMap();

        for (Match match : matches) {
            int matchYear = match.getYear();

            if (numberOfMatchesPlayedPerYear.containsKey(matchYear)) {
                int countOfMatches = numberOfMatchesPlayedPerYear.get(matchYear);
                numberOfMatchesPlayedPerYear.put(matchYear, countOfMatches + 1);
            } else
                numberOfMatchesPlayedPerYear.put(matchYear, MATCH_FIRST_MATCH_PLAYED);
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
                int matchYear = match.getYear();
                if (numberOfMatchesWonAllYears.containsKey(matchYear))
                    numberOfMatchesWonThisYear = numberOfMatchesWonAllYears.get(matchYear);

                else
                    numberOfMatchesWonThisYear = new TreeMap();

                if (numberOfMatchesWonThisYear.containsKey(winner)) {
                    int countOfMatches = numberOfMatchesWonThisYear.get(winner);
                    numberOfMatchesWonThisYear.put(winner, countOfMatches + 1);
                } else
                    numberOfMatchesWonThisYear.put(winner, MATCH_FIRST_MATCH_WON);

                numberOfMatchesWonAllYears.put(matchYear, numberOfMatchesWonThisYear);
            }
        }

        System.out.println("PART 2\n");
        for (Map.Entry<Integer, TreeMap<String, Integer>> es : numberOfMatchesWonAllYears.entrySet()) {
            System.out.println("\nYear: " + es.getKey() + "\n");
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
        System.out.println("Enter year for which extra runs per team are required: ");

        int year = sc.nextInt();
        int runsConcededPerTeam = 0;
        int extraRuns = 0;
        String bowlTeam = "";
        List<Integer> yearIdList = getYearId(matches, year);
        TreeMap<String, Integer> extraRunsPerTeam = new TreeMap();

        for (Delivery delivery : deliveries) {
            int matchId = delivery.getId();

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
        System.out.println("Enter year for which top economical bowlers are required: ");

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
            int matchId = delivery.getId();

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
                return e1.getValue().get(DELIVERY_ECONOMY) - e2.getValue().get(DELIVERY_ECONOMY);
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