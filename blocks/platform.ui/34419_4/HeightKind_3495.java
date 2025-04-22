package org.eclipse.ui.examples.views.properties.tabbed.hockeyleague;

import org.eclipse.emf.ecore.EObject;

public interface GoalieStats extends EObject {
	String getYear();

	void setYear(String value);

	Team getTeam();

	void setTeam(Team value);

	int getGamesPlayedIn();

	void setGamesPlayedIn(int value);

	int getMinutesPlayedIn();

	void setMinutesPlayedIn(int value);

	float getGoalsAgainstAverage();

	void setGoalsAgainstAverage(float value);

	int getWins();

	void setWins(int value);

	int getLosses();

	void setLosses(int value);

	int getTies();

	void setTies(int value);

	int getEmptyNetGoals();

	void setEmptyNetGoals(int value);

	int getShutouts();

	void setShutouts(int value);

	int getGoalsAgainst();

	void setGoalsAgainst(int value);

	int getSaves();

	void setSaves(int value);

	int getPenaltyMinutes();

	void setPenaltyMinutes(int value);

	int getGoals();

	void setGoals(int value);

	int getAssists();

	void setAssists(int value);

	int getPoints();

	void setPoints(int value);

} // GoalieStats
