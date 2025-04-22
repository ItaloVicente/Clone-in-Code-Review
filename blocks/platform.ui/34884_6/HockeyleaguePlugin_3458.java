package org.eclipse.ui.examples.views.properties.tabbed.hockeyleague;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

public interface HockeyleaguePackage extends EPackage {
	String eNAME = "hockeyleague"; //$NON-NLS-1$

	String eNS_URI = "http:///org/eclipse/ui/views/properties/tabbed/examples/org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.ecore"; //$NON-NLS-1$

	String eNS_PREFIX = "org.eclipse.ui.examples.views.properties.tabbed.hockeyleague"; //$NON-NLS-1$

	HockeyleaguePackage eINSTANCE = org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.impl.HockeyleaguePackageImpl.init();

	int HOCKEYLEAGUE_OBJECT = 5;

	int HOCKEYLEAGUE_OBJECT__NAME = 0;

	int HOCKEYLEAGUE_OBJECT_FEATURE_COUNT = 1;

	int ARENA = 0;

	int ARENA__NAME = HOCKEYLEAGUE_OBJECT__NAME;

	int ARENA__ADDRESS = HOCKEYLEAGUE_OBJECT_FEATURE_COUNT + 0;

	int ARENA__CAPACITY = HOCKEYLEAGUE_OBJECT_FEATURE_COUNT + 1;

	int ARENA_FEATURE_COUNT = HOCKEYLEAGUE_OBJECT_FEATURE_COUNT + 2;

	int PLAYER = 7;

	int PLAYER__NAME = HOCKEYLEAGUE_OBJECT__NAME;

	int PLAYER__BIRTHPLACE = HOCKEYLEAGUE_OBJECT_FEATURE_COUNT + 0;

	int PLAYER__NUMBER = HOCKEYLEAGUE_OBJECT_FEATURE_COUNT + 1;

	int PLAYER__HEIGHT_MESUREMENT = HOCKEYLEAGUE_OBJECT_FEATURE_COUNT + 2;

	int PLAYER__HEIGHT_VALUE = HOCKEYLEAGUE_OBJECT_FEATURE_COUNT + 3;

	int PLAYER__WEIGHT_MESUREMENT = HOCKEYLEAGUE_OBJECT_FEATURE_COUNT + 4;

	int PLAYER__WEIGHT_VALUE = HOCKEYLEAGUE_OBJECT_FEATURE_COUNT + 5;

	int PLAYER__SHOT = HOCKEYLEAGUE_OBJECT_FEATURE_COUNT + 6;

	int PLAYER__BIRTHDATE = HOCKEYLEAGUE_OBJECT_FEATURE_COUNT + 7;

	int PLAYER_FEATURE_COUNT = HOCKEYLEAGUE_OBJECT_FEATURE_COUNT + 8;

	int DEFENCE = 1;

	int DEFENCE__NAME = PLAYER__NAME;

	int DEFENCE__BIRTHPLACE = PLAYER__BIRTHPLACE;

	int DEFENCE__NUMBER = PLAYER__NUMBER;

	int DEFENCE__HEIGHT_MESUREMENT = PLAYER__HEIGHT_MESUREMENT;

	int DEFENCE__HEIGHT_VALUE = PLAYER__HEIGHT_VALUE;

	int DEFENCE__WEIGHT_MESUREMENT = PLAYER__WEIGHT_MESUREMENT;

	int DEFENCE__WEIGHT_VALUE = PLAYER__WEIGHT_VALUE;

	int DEFENCE__SHOT = PLAYER__SHOT;

	int DEFENCE__BIRTHDATE = PLAYER__BIRTHDATE;

	int DEFENCE__POSITION = PLAYER_FEATURE_COUNT + 0;

	int DEFENCE__PLAYER_STATS = PLAYER_FEATURE_COUNT + 1;

	int DEFENCE_FEATURE_COUNT = PLAYER_FEATURE_COUNT + 2;

	int FORWARD = 2;

	int FORWARD__NAME = PLAYER__NAME;

	int FORWARD__BIRTHPLACE = PLAYER__BIRTHPLACE;

	int FORWARD__NUMBER = PLAYER__NUMBER;

	int FORWARD__HEIGHT_MESUREMENT = PLAYER__HEIGHT_MESUREMENT;

	int FORWARD__HEIGHT_VALUE = PLAYER__HEIGHT_VALUE;

	int FORWARD__WEIGHT_MESUREMENT = PLAYER__WEIGHT_MESUREMENT;

	int FORWARD__WEIGHT_VALUE = PLAYER__WEIGHT_VALUE;

	int FORWARD__SHOT = PLAYER__SHOT;

	int FORWARD__BIRTHDATE = PLAYER__BIRTHDATE;

	int FORWARD__POSITION = PLAYER_FEATURE_COUNT + 0;

	int FORWARD__PLAYER_STATS = PLAYER_FEATURE_COUNT + 1;

	int FORWARD_FEATURE_COUNT = PLAYER_FEATURE_COUNT + 2;

	int GOALIE = 3;

	int GOALIE__NAME = PLAYER__NAME;

	int GOALIE__BIRTHPLACE = PLAYER__BIRTHPLACE;

	int GOALIE__NUMBER = PLAYER__NUMBER;

	int GOALIE__HEIGHT_MESUREMENT = PLAYER__HEIGHT_MESUREMENT;

	int GOALIE__HEIGHT_VALUE = PLAYER__HEIGHT_VALUE;

	int GOALIE__WEIGHT_MESUREMENT = PLAYER__WEIGHT_MESUREMENT;

	int GOALIE__WEIGHT_VALUE = PLAYER__WEIGHT_VALUE;

	int GOALIE__SHOT = PLAYER__SHOT;

	int GOALIE__BIRTHDATE = PLAYER__BIRTHDATE;

	int GOALIE__GOALIE_STATS = PLAYER_FEATURE_COUNT + 0;

	int GOALIE_FEATURE_COUNT = PLAYER_FEATURE_COUNT + 1;

	int GOALIE_STATS = 4;

	int GOALIE_STATS__YEAR = 0;

	int GOALIE_STATS__TEAM = 1;

	int GOALIE_STATS__GAMES_PLAYED_IN = 2;

	int GOALIE_STATS__MINUTES_PLAYED_IN = 3;

	int GOALIE_STATS__GOALS_AGAINST_AVERAGE = 4;

	int GOALIE_STATS__WINS = 5;

	int GOALIE_STATS__LOSSES = 6;

	int GOALIE_STATS__TIES = 7;

	int GOALIE_STATS__EMPTY_NET_GOALS = 8;

	int GOALIE_STATS__SHUTOUTS = 9;

	int GOALIE_STATS__GOALS_AGAINST = 10;

	int GOALIE_STATS__SAVES = 11;

	int GOALIE_STATS__PENALTY_MINUTES = 12;

	int GOALIE_STATS__GOALS = 13;

	int GOALIE_STATS__ASSISTS = 14;

	int GOALIE_STATS__POINTS = 15;

	int GOALIE_STATS_FEATURE_COUNT = 16;

	int LEAGUE = 6;

	int LEAGUE__NAME = HOCKEYLEAGUE_OBJECT__NAME;

	int LEAGUE__HEADOFFICE = HOCKEYLEAGUE_OBJECT_FEATURE_COUNT + 0;

	int LEAGUE__TEAMS = HOCKEYLEAGUE_OBJECT_FEATURE_COUNT + 1;

	int LEAGUE_FEATURE_COUNT = HOCKEYLEAGUE_OBJECT_FEATURE_COUNT + 2;

	int PLAYER_STATS = 8;

	int PLAYER_STATS__YEAR = 0;

	int PLAYER_STATS__TEAM = 1;

	int PLAYER_STATS__GAMES_PLAYED_IN = 2;

	int PLAYER_STATS__GOALS = 3;

	int PLAYER_STATS__ASSISTS = 4;

	int PLAYER_STATS__POINTS = 5;

	int PLAYER_STATS__PLUS_MINUS = 6;

	int PLAYER_STATS__PENALTY_MINUTES = 7;

	int PLAYER_STATS__POWER_PLAY_GOALS = 8;

	int PLAYER_STATS__SHORT_HANDED_GOALS = 9;

	int PLAYER_STATS__GAME_WINNING_GOALS = 10;

	int PLAYER_STATS__SHOTS = 11;

	int PLAYER_STATS__SHOT_PERCENTAGE = 12;

	int PLAYER_STATS_FEATURE_COUNT = 13;

	int TEAM = 9;

	int TEAM__NAME = HOCKEYLEAGUE_OBJECT__NAME;

	int TEAM__FORWARDS = HOCKEYLEAGUE_OBJECT_FEATURE_COUNT + 0;

	int TEAM__DEFENCEMEN = HOCKEYLEAGUE_OBJECT_FEATURE_COUNT + 1;

	int TEAM__GOALIES = HOCKEYLEAGUE_OBJECT_FEATURE_COUNT + 2;

	int TEAM__ARENA = HOCKEYLEAGUE_OBJECT_FEATURE_COUNT + 3;

	int TEAM_FEATURE_COUNT = HOCKEYLEAGUE_OBJECT_FEATURE_COUNT + 4;

	int DEFENCE_POSITION_KIND = 10;

	int FORWARD_POSITION_KIND = 11;

	int HEIGHT_KIND = 12;

	int SHOT_KIND = 13;

	int WEIGHT_KIND = 14;


	EClass getArena();

	EAttribute getArena_Address();

	EAttribute getArena_Capacity();

	EClass getDefence();

	EAttribute getDefence_Position();

	EReference getDefence_PlayerStats();

	EClass getForward();

	EAttribute getForward_Position();

	EReference getForward_PlayerStats();

	EClass getGoalie();

	EReference getGoalie_GoalieStats();

	EClass getGoalieStats();

	EAttribute getGoalieStats_Year();

	EReference getGoalieStats_Team();

	EAttribute getGoalieStats_GamesPlayedIn();

	EAttribute getGoalieStats_MinutesPlayedIn();

	EAttribute getGoalieStats_GoalsAgainstAverage();

	EAttribute getGoalieStats_Wins();

	EAttribute getGoalieStats_Losses();

	EAttribute getGoalieStats_Ties();

	EAttribute getGoalieStats_EmptyNetGoals();

	EAttribute getGoalieStats_Shutouts();

	EAttribute getGoalieStats_GoalsAgainst();

	EAttribute getGoalieStats_Saves();

	EAttribute getGoalieStats_PenaltyMinutes();

	EAttribute getGoalieStats_Goals();

	EAttribute getGoalieStats_Assists();

	EAttribute getGoalieStats_Points();

	EClass getHockeyleagueObject();

	EAttribute getHockeyleagueObject_Name();

	EClass getLeague();

	EAttribute getLeague_Headoffice();

	EReference getLeague_Teams();

	EClass getPlayer();

	EAttribute getPlayer_Birthplace();

	EAttribute getPlayer_Number();

	EAttribute getPlayer_HeightMesurement();

	EAttribute getPlayer_HeightValue();

	EAttribute getPlayer_WeightMesurement();

	EAttribute getPlayer_WeightValue();

	EAttribute getPlayer_Shot();

	EAttribute getPlayer_Birthdate();

	EClass getPlayerStats();

	EAttribute getPlayerStats_Year();

	EReference getPlayerStats_Team();

	EAttribute getPlayerStats_GamesPlayedIn();

	EAttribute getPlayerStats_Goals();

	EAttribute getPlayerStats_Assists();

	EAttribute getPlayerStats_Points();

	EAttribute getPlayerStats_PlusMinus();

	EAttribute getPlayerStats_PenaltyMinutes();

	EAttribute getPlayerStats_PowerPlayGoals();

	EAttribute getPlayerStats_ShortHandedGoals();

	EAttribute getPlayerStats_GameWinningGoals();

	EAttribute getPlayerStats_Shots();

	EAttribute getPlayerStats_ShotPercentage();

	EClass getTeam();

	EReference getTeam_Forwards();

	EReference getTeam_Defencemen();

	EReference getTeam_Goalies();

	EReference getTeam_Arena();

	EEnum getDefencePositionKind();

	EEnum getForwardPositionKind();

	EEnum getHeightKind();

	EEnum getShotKind();

	EEnum getWeightKind();

	HockeyleagueFactory getHockeyleagueFactory();

	interface Literals {
		EClass ARENA = eINSTANCE.getArena();

		EAttribute ARENA__ADDRESS = eINSTANCE.getArena_Address();

		EAttribute ARENA__CAPACITY = eINSTANCE.getArena_Capacity();

		EClass DEFENCE = eINSTANCE.getDefence();

		EAttribute DEFENCE__POSITION = eINSTANCE.getDefence_Position();

		EReference DEFENCE__PLAYER_STATS = eINSTANCE.getDefence_PlayerStats();

		EClass FORWARD = eINSTANCE.getForward();

		EAttribute FORWARD__POSITION = eINSTANCE.getForward_Position();

		EReference FORWARD__PLAYER_STATS = eINSTANCE.getForward_PlayerStats();

		EClass GOALIE = eINSTANCE.getGoalie();

		EReference GOALIE__GOALIE_STATS = eINSTANCE.getGoalie_GoalieStats();

		EClass GOALIE_STATS = eINSTANCE.getGoalieStats();

		EAttribute GOALIE_STATS__YEAR = eINSTANCE.getGoalieStats_Year();

		EReference GOALIE_STATS__TEAM = eINSTANCE.getGoalieStats_Team();

		EAttribute GOALIE_STATS__GAMES_PLAYED_IN = eINSTANCE.getGoalieStats_GamesPlayedIn();

		EAttribute GOALIE_STATS__MINUTES_PLAYED_IN = eINSTANCE.getGoalieStats_MinutesPlayedIn();

		EAttribute GOALIE_STATS__GOALS_AGAINST_AVERAGE = eINSTANCE.getGoalieStats_GoalsAgainstAverage();

		EAttribute GOALIE_STATS__WINS = eINSTANCE.getGoalieStats_Wins();

		EAttribute GOALIE_STATS__LOSSES = eINSTANCE.getGoalieStats_Losses();

		EAttribute GOALIE_STATS__TIES = eINSTANCE.getGoalieStats_Ties();

		EAttribute GOALIE_STATS__EMPTY_NET_GOALS = eINSTANCE.getGoalieStats_EmptyNetGoals();

		EAttribute GOALIE_STATS__SHUTOUTS = eINSTANCE.getGoalieStats_Shutouts();

		EAttribute GOALIE_STATS__GOALS_AGAINST = eINSTANCE.getGoalieStats_GoalsAgainst();

		EAttribute GOALIE_STATS__SAVES = eINSTANCE.getGoalieStats_Saves();

		EAttribute GOALIE_STATS__PENALTY_MINUTES = eINSTANCE.getGoalieStats_PenaltyMinutes();

		EAttribute GOALIE_STATS__GOALS = eINSTANCE.getGoalieStats_Goals();

		EAttribute GOALIE_STATS__ASSISTS = eINSTANCE.getGoalieStats_Assists();

		EAttribute GOALIE_STATS__POINTS = eINSTANCE.getGoalieStats_Points();

		EClass HOCKEYLEAGUE_OBJECT = eINSTANCE.getHockeyleagueObject();

		EAttribute HOCKEYLEAGUE_OBJECT__NAME = eINSTANCE.getHockeyleagueObject_Name();

		EClass LEAGUE = eINSTANCE.getLeague();

		EAttribute LEAGUE__HEADOFFICE = eINSTANCE.getLeague_Headoffice();

		EReference LEAGUE__TEAMS = eINSTANCE.getLeague_Teams();

		EClass PLAYER = eINSTANCE.getPlayer();

		EAttribute PLAYER__BIRTHPLACE = eINSTANCE.getPlayer_Birthplace();

		EAttribute PLAYER__NUMBER = eINSTANCE.getPlayer_Number();

		EAttribute PLAYER__HEIGHT_MESUREMENT = eINSTANCE.getPlayer_HeightMesurement();

		EAttribute PLAYER__HEIGHT_VALUE = eINSTANCE.getPlayer_HeightValue();

		EAttribute PLAYER__WEIGHT_MESUREMENT = eINSTANCE.getPlayer_WeightMesurement();

		EAttribute PLAYER__WEIGHT_VALUE = eINSTANCE.getPlayer_WeightValue();

		EAttribute PLAYER__SHOT = eINSTANCE.getPlayer_Shot();

		EAttribute PLAYER__BIRTHDATE = eINSTANCE.getPlayer_Birthdate();

		EClass PLAYER_STATS = eINSTANCE.getPlayerStats();

		EAttribute PLAYER_STATS__YEAR = eINSTANCE.getPlayerStats_Year();

		EReference PLAYER_STATS__TEAM = eINSTANCE.getPlayerStats_Team();

		EAttribute PLAYER_STATS__GAMES_PLAYED_IN = eINSTANCE.getPlayerStats_GamesPlayedIn();

		EAttribute PLAYER_STATS__GOALS = eINSTANCE.getPlayerStats_Goals();

		EAttribute PLAYER_STATS__ASSISTS = eINSTANCE.getPlayerStats_Assists();

		EAttribute PLAYER_STATS__POINTS = eINSTANCE.getPlayerStats_Points();

		EAttribute PLAYER_STATS__PLUS_MINUS = eINSTANCE.getPlayerStats_PlusMinus();

		EAttribute PLAYER_STATS__PENALTY_MINUTES = eINSTANCE.getPlayerStats_PenaltyMinutes();

		EAttribute PLAYER_STATS__POWER_PLAY_GOALS = eINSTANCE.getPlayerStats_PowerPlayGoals();

		EAttribute PLAYER_STATS__SHORT_HANDED_GOALS = eINSTANCE.getPlayerStats_ShortHandedGoals();

		EAttribute PLAYER_STATS__GAME_WINNING_GOALS = eINSTANCE.getPlayerStats_GameWinningGoals();

		EAttribute PLAYER_STATS__SHOTS = eINSTANCE.getPlayerStats_Shots();

		EAttribute PLAYER_STATS__SHOT_PERCENTAGE = eINSTANCE.getPlayerStats_ShotPercentage();

		EClass TEAM = eINSTANCE.getTeam();

		EReference TEAM__FORWARDS = eINSTANCE.getTeam_Forwards();

		EReference TEAM__DEFENCEMEN = eINSTANCE.getTeam_Defencemen();

		EReference TEAM__GOALIES = eINSTANCE.getTeam_Goalies();

		EReference TEAM__ARENA = eINSTANCE.getTeam_Arena();

		EEnum DEFENCE_POSITION_KIND = eINSTANCE.getDefencePositionKind();

		EEnum FORWARD_POSITION_KIND = eINSTANCE.getForwardPositionKind();

		EEnum HEIGHT_KIND = eINSTANCE.getHeightKind();

		EEnum SHOT_KIND = eINSTANCE.getShotKind();

		EEnum WEIGHT_KIND = eINSTANCE.getWeightKind();

	}

} //HockeyleaguePackage
