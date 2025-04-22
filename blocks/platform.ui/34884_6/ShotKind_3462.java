package org.eclipse.ui.examples.views.properties.tabbed.hockeyleague;

import org.eclipse.emf.ecore.EObject;

public interface PlayerStats extends EObject {
	String getYear();

	void setYear(String value);

	Team getTeam();

	void setTeam(Team value);

	int getGamesPlayedIn();

	void setGamesPlayedIn(int value);

	int getGoals();

	void setGoals(int value);

	int getAssists();

	void setAssists(int value);

	int getPoints();

	void setPoints(int value);

	int getPlusMinus();

	void setPlusMinus(int value);

	int getPenaltyMinutes();

	void setPenaltyMinutes(int value);

	int getPowerPlayGoals();

	void setPowerPlayGoals(int value);

	int getShortHandedGoals();

	void setShortHandedGoals(int value);

	int getGameWinningGoals();

	void setGameWinningGoals(int value);

	int getShots();

	void setShots(int value);

	float getShotPercentage();

	void setShotPercentage(float value);

} // PlayerStats
