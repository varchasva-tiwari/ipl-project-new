import Match from './match.mjs';
import Delivery from './delivery.mjs';
import csv from 'csvtojson';

const MATCH_ID = 'id';
const MATCH_YEAR = 'season';
const Match_CITY = 'city';
const MATCH_DATE = 'date';
const MATCH_TEAM1 = 'team1';
const MATCH_TOSS_WINNER = 'toss_winner';
const MATCH_TOSS_DECISION = 'toss_decision';
const MATCH_RESULT = 'result'
const MATCH_DL_APPLIED = 'dl_applied';
const MATCH_WINNER = 'winner';
const MATCH_WIN_BY_RUNS = 'win_by_runs';
const MATCH_WIN_BY_WICKETS = 'win_by_wickets';

const DELIVERY_ID = 'match_id';
const DELIVERY_INNING = 'inning';
const DELIVERY_BATTING_TEAM = 'batting_team';
const DELIVERY_BOWLING_TEAM = 'bowling_team';
const DELIVERY_OVER = 'over';
const DELIVERY_BALL = 'ball';
const DELIVERY_BATSMAN = 'batsman';
const DELIVERY_NON_STRIKER = 'non_striker';
const DELIVERY_BOWLER = 'bowler';
const DELIVERY_IS_SUPER_OVER = 'is_super_over';
const DELIVERY_WIDE_RUNS = 'wide_runs';
const DELIVERY_BYE_RUNS = 'bye_runs';
const DELIVERY_LEG_BYE_RUNS = 'legbye_runs';
const DELIVERY_NO_BALL_RUNS = 'noball_runs';
const DELIVERY_PENALTY_RUNS = 'penalty_runs';
const DELIVERY_BATSMAN_RUNS = 'batsman_runs';
const DELIVERY_EXTRA_RUNS = 'extra_runs';
const DELIVERY_TOTAL_RUNS = 'total_runs';
const DELIVERY_RUNS_CONCEDED = 0;
const DELIVERY_BALLS_BOWLED = 1;
const DELIVERY_OVERS_BOWLED = 2;
const DELIVERY_ECONOMY = 3;

const DELIVERY_BALLS_IN_OVER = 6;
const DELIVERY_BALLS_RESET = 0;
const DELIVERY_FIRST_BALL_BOWLED = 1;
const DELIVERY_NO_OVERS_TILL_NOW = 0;
const DELIVERY_NO_ECONOMY_TILL_NOW = 0;
const DELIVERY_BOWLER_NAME = 0;
const DELIVERY_BOWLER_STATS = 1;

const getMatchesData = async () => {
    const matchesFile = 'csv_files/matches.csv';
    const matchesJSON = await csv().fromFile(matchesFile);
    let matches = [];

    matchesJSON.forEach((matchRow) => {
        let match = new Match();

        match.id = Number(matchRow[MATCH_ID]);
        match.year = Number(matchRow[MATCH_YEAR]);
        match.city = matchRow[Match_CITY];
        match.date = matchRow[MATCH_DATE];
        match.team1 = matchRow[MATCH_TEAM1];
        match.tossWinner = matchRow[MATCH_TOSS_WINNER];
        match.tossDecision = matchRow[MATCH_TOSS_DECISION];
        match.result = matchRow[MATCH_RESULT];
        match.dlApplied = matchRow[MATCH_DL_APPLIED];
        match.winner = matchRow[MATCH_WINNER];
        match.winByRuns = Number(matchRow[MATCH_WIN_BY_RUNS]);
        match.winByWickets = Number(matchRow[MATCH_WIN_BY_WICKETS]);

        matches.push(match);
    });

    return matches;
}

const getDeliveriesData = async () => {
    const deliveriesFile = 'csv_files/deliveries.csv';
    const deliveriesJSON = await csv().fromFile(deliveriesFile);
    let deliveries = [];

    deliveriesJSON.forEach((deliveryRow) => {
        let delivery = new Delivery();

        delivery.id = Number(deliveryRow[DELIVERY_ID]);
        delivery.inning = Number(deliveryRow[DELIVERY_INNING]);
        delivery.battingTeam = deliveryRow[DELIVERY_BATTING_TEAM];
        delivery.bowlingTeam = deliveryRow[DELIVERY_BOWLING_TEAM];
        delivery.over = Number(deliveryRow[DELIVERY_OVER]);
        delivery.ball = Number(deliveryRow[DELIVERY_BALL]);
        delivery.batsman = deliveryRow[DELIVERY_BATSMAN];
        delivery.nonStriker = deliveryRow[DELIVERY_NON_STRIKER];
        delivery.bowlerName = deliveryRow[DELIVERY_BOWLER];
        delivery.isSuperOver = Number(deliveryRow[DELIVERY_IS_SUPER_OVER]);
        delivery.wideRuns = Number(deliveryRow[DELIVERY_WIDE_RUNS]);
        delivery.byeRuns = Number(deliveryRow[DELIVERY_BYE_RUNS]);
        delivery.legByeRuns = Number(deliveryRow[DELIVERY_LEG_BYE_RUNS]);
        delivery.noBallRuns = Number(deliveryRow[DELIVERY_NO_BALL_RUNS]);
        delivery.penaltyRuns = Number(deliveryRow[DELIVERY_PENALTY_RUNS]);
        delivery.batsmanRuns = Number(deliveryRow[DELIVERY_BATSMAN_RUNS]);
        delivery.extraRuns = Number(deliveryRow[DELIVERY_EXTRA_RUNS]);
        delivery.totalRuns = Number(deliveryRow[DELIVERY_TOTAL_RUNS]);

        deliveries.push(delivery);
    });

    return deliveries;
}

const findNumberOfMatchesPlayedPerYear = (matches) => {
    let numberOfMatchesPlayedPerYear = {};

    for (let match of matches) {
        let matchYear = match.year;

        if (numberOfMatchesPlayedPerYear.hasOwnProperty(matchYear)) {
            let numberOfMatchesPlayedThisYear = numberOfMatchesPlayedPerYear[matchYear];
            numberOfMatchesPlayedPerYear[matchYear] = numberOfMatchesPlayedThisYear + 1;
        }

        else
            numberOfMatchesPlayedPerYear[matchYear] = 1;
    }

    console.log("PART 1");
    console.log("\n");
    console.log(numberOfMatchesPlayedPerYear);
    console.log("\n");
}

const findNumberOfMatchesWonAllYears = (matches) => {
    let matchWinner;
    let matchYear;
    let numberOfMatchesWonThisYear = {};
    let numberOfMatchesWonAllYears = {};

    for (let match of matches) {
        matchWinner = match.winner;

        if (matchWinner.length != 0) {
            matchYear = match.year;

            if (numberOfMatchesWonAllYears.hasOwnProperty(matchYear))
                numberOfMatchesWonThisYear = numberOfMatchesWonAllYears[matchYear];

            else
                numberOfMatchesWonThisYear = {};

            if (numberOfMatchesWonThisYear.hasOwnProperty(matchWinner)) {
                let countOfMatches = numberOfMatchesWonThisYear[matchWinner];
                numberOfMatchesWonThisYear[matchWinner] = countOfMatches + 1;
            }

            else
                numberOfMatchesWonThisYear[matchWinner] = 1;

            numberOfMatchesWonAllYears[matchYear] = numberOfMatchesWonThisYear;
        }
    }

    console.log("PART 2");
    console.log("\n");
    console.log(numberOfMatchesWonAllYears);
    console.log("\n");
}

const getYearId = ((matches, matchYear) => {
    let matchIdList = new Set();

    matches.forEach((match) => {
        let matchId = match.id;
        if (match.year == matchYear)
            matchIdList.add(matchId);
    });
    return matchIdList;
})

const findExtraRunsPerTeam = (matches, deliveries) => {
    let bowlingTeam;
    let matchYear = 2017;
    let currentExtraRuns;
    let newExtraRuns;

    let matchIdList = getYearId(matches, matchYear);
    let ExtraRunsPerTeam = {};

    matches.forEach((match) => {
        let matchId = match.id;
        if (match.year == matchYear)
            matchIdList.add(matchId);
    });

    deliveries.forEach((delivery) => {
        let deliveryId = delivery.id;
        if (matchIdList.has(deliveryId)) {
            bowlingTeam = delivery.bowlingTeam;
            newExtraRuns = delivery.extraRuns;

            if (ExtraRunsPerTeam.hasOwnProperty(bowlingTeam)) {
                currentExtraRuns = ExtraRunsPerTeam[bowlingTeam];
                ExtraRunsPerTeam[bowlingTeam] = currentExtraRuns + newExtraRuns;
            }

            else
                ExtraRunsPerTeam[bowlingTeam] = newExtraRuns;
        }
    });

    console.log("PART 3");
    console.log("\n");
    console.log(ExtraRunsPerTeam);
    console.log("\n");
}

const findTopEconomicalBowlers = (matches, deliveries) => {
    let matchYear = 2017;
    let runsConcededOld;
    let runsConcededNew;
    let ballsBowled;
    let oversBowled;
    let bowlerName;

    let matchIdList = getYearId(matches, matchYear);
    let bowlerStats;
    let economyOfBowlers = {};
    let topEconomicalBowlers = [];

    deliveries.forEach((delivery) => {
        let matchId = delivery.id;

        if (matchIdList.has(matchId)) {
            bowlerName = delivery.bowlerName;

            runsConcededNew = delivery.wideRuns + delivery.byeRuns + delivery.legByeRuns +
                delivery.noBallRuns + delivery.penaltyRuns + delivery.batsmanRuns + delivery.extraRuns;

            if (economyOfBowlers.hasOwnProperty(bowlerName)) {
                
                bowlerStats = economyOfBowlers[bowlerName];

                runsConcededOld = bowlerStats[DELIVERY_RUNS_CONCEDED];
                ballsBowled = bowlerStats[DELIVERY_BALLS_BOWLED];
                oversBowled = bowlerStats[DELIVERY_OVERS_BOWLED];

                bowlerStats[DELIVERY_RUNS_CONCEDED] = runsConcededOld + runsConcededNew;

                ballsBowled += 1;

                if (delivery.noBallRuns > 0 || delivery.wideRuns > 0)
                    ballsBowled -= 1;

                bowlerStats[DELIVERY_BALLS_BOWLED] = ballsBowled;

                if (ballsBowled == DELIVERY_BALLS_IN_OVER) {
                    bowlerStats[DELIVERY_BALLS_BOWLED] = DELIVERY_BALLS_RESET;
                    bowlerStats[DELIVERY_OVERS_BOWLED] = oversBowled + 1;
                    bowlerStats[DELIVERY_ECONOMY] = Math.trunc(bowlerStats[DELIVERY_RUNS_CONCEDED] / bowlerStats[DELIVERY_OVERS_BOWLED]);
                }
            }

            else {
                bowlerStats = {};
                bowlerStats[DELIVERY_RUNS_CONCEDED] = runsConcededNew;
                bowlerStats[DELIVERY_BALLS_BOWLED] = DELIVERY_FIRST_BALL_BOWLED;
                bowlerStats[DELIVERY_OVERS_BOWLED] = DELIVERY_NO_OVERS_TILL_NOW;
                bowlerStats[DELIVERY_ECONOMY] = DELIVERY_NO_ECONOMY_TILL_NOW;
            }

            economyOfBowlers[bowlerName] = bowlerStats;
        }
    });

    topEconomicalBowlers = Object.entries(economyOfBowlers);

    topEconomicalBowlers.sort((bowler1, bowler2) => {
        return bowler1[DELIVERY_BOWLER_STATS][DELIVERY_ECONOMY] - bowler2[DELIVERY_BOWLER_STATS][DELIVERY_ECONOMY];
    });
    
    console.log("PART 4");
    console.log("\n");
    topEconomicalBowlers.forEach((bowler) => {
        console.log(bowler[DELIVERY_BOWLER_NAME] + " : " + bowler[1][DELIVERY_ECONOMY]);
    });
}

(async () => {
    const matches = await getMatchesData();
    const deliveries = await getDeliveriesData();

    findNumberOfMatchesPlayedPerYear(matches);
    findNumberOfMatchesWonAllYears(matches);
    findExtraRunsPerTeam(matches, deliveries);
    findTopEconomicalBowlers(matches, deliveries);
})();
