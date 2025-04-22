package org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.impl;


import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.GoalieStats;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.HockeyleaguePackage;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.Team;

public class GoalieStatsImpl extends EObjectImpl implements GoalieStats {
	protected static final String YEAR_EDEFAULT = null;

	protected String year = YEAR_EDEFAULT;

	protected Team team;

	protected static final int GAMES_PLAYED_IN_EDEFAULT = 0;

	protected int gamesPlayedIn = GAMES_PLAYED_IN_EDEFAULT;

	protected static final int MINUTES_PLAYED_IN_EDEFAULT = 0;

	protected int minutesPlayedIn = MINUTES_PLAYED_IN_EDEFAULT;

	protected static final float GOALS_AGAINST_AVERAGE_EDEFAULT = 0.0F;

	protected float goalsAgainstAverage = GOALS_AGAINST_AVERAGE_EDEFAULT;

	protected static final int WINS_EDEFAULT = 0;

	protected int wins = WINS_EDEFAULT;

	protected static final int LOSSES_EDEFAULT = 0;

	protected int losses = LOSSES_EDEFAULT;

	protected static final int TIES_EDEFAULT = 0;

	protected int ties = TIES_EDEFAULT;

	protected static final int EMPTY_NET_GOALS_EDEFAULT = 0;

	protected int emptyNetGoals = EMPTY_NET_GOALS_EDEFAULT;

	protected static final int SHUTOUTS_EDEFAULT = 0;

	protected int shutouts = SHUTOUTS_EDEFAULT;

	protected static final int GOALS_AGAINST_EDEFAULT = 0;

	protected int goalsAgainst = GOALS_AGAINST_EDEFAULT;

	protected static final int SAVES_EDEFAULT = 0;

	protected int saves = SAVES_EDEFAULT;

	protected static final int PENALTY_MINUTES_EDEFAULT = 0;

	protected int penaltyMinutes = PENALTY_MINUTES_EDEFAULT;

	protected static final int GOALS_EDEFAULT = 0;

	protected int goals = GOALS_EDEFAULT;

	protected static final int ASSISTS_EDEFAULT = 0;

	protected int assists = ASSISTS_EDEFAULT;

	protected static final int POINTS_EDEFAULT = 0;

	protected int points = POINTS_EDEFAULT;

	protected GoalieStatsImpl() {
		super();
	}

	protected EClass eStaticClass() {
		return HockeyleaguePackage.Literals.GOALIE_STATS;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String newYear) {
		String oldYear = year;
		year = newYear;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HockeyleaguePackage.GOALIE_STATS__YEAR, oldYear, year));
	}

	public Team getTeam() {
		if (team != null && team.eIsProxy()) {
			InternalEObject oldTeam = (InternalEObject)team;
			team = (Team)eResolveProxy(oldTeam);
			if (team != oldTeam) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, HockeyleaguePackage.GOALIE_STATS__TEAM, oldTeam, team));
			}
		}
		return team;
	}

	public Team basicGetTeam() {
		return team;
	}

	public void setTeam(Team newTeam) {
		Team oldTeam = team;
		team = newTeam;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HockeyleaguePackage.GOALIE_STATS__TEAM, oldTeam, team));
	}

	public int getGamesPlayedIn() {
		return gamesPlayedIn;
	}

	public void setGamesPlayedIn(int newGamesPlayedIn) {
		int oldGamesPlayedIn = gamesPlayedIn;
		gamesPlayedIn = newGamesPlayedIn;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HockeyleaguePackage.GOALIE_STATS__GAMES_PLAYED_IN, oldGamesPlayedIn, gamesPlayedIn));
	}

	public int getMinutesPlayedIn() {
		return minutesPlayedIn;
	}

	public void setMinutesPlayedIn(int newMinutesPlayedIn) {
		int oldMinutesPlayedIn = minutesPlayedIn;
		minutesPlayedIn = newMinutesPlayedIn;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HockeyleaguePackage.GOALIE_STATS__MINUTES_PLAYED_IN, oldMinutesPlayedIn, minutesPlayedIn));
	}

	public float getGoalsAgainstAverage() {
		return goalsAgainstAverage;
	}

	public void setGoalsAgainstAverage(float newGoalsAgainstAverage) {
		float oldGoalsAgainstAverage = goalsAgainstAverage;
		goalsAgainstAverage = newGoalsAgainstAverage;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HockeyleaguePackage.GOALIE_STATS__GOALS_AGAINST_AVERAGE, oldGoalsAgainstAverage, goalsAgainstAverage));
	}

	public int getWins() {
		return wins;
	}

	public void setWins(int newWins) {
		int oldWins = wins;
		wins = newWins;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HockeyleaguePackage.GOALIE_STATS__WINS, oldWins, wins));
	}

	public int getLosses() {
		return losses;
	}

	public void setLosses(int newLosses) {
		int oldLosses = losses;
		losses = newLosses;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HockeyleaguePackage.GOALIE_STATS__LOSSES, oldLosses, losses));
	}

	public int getTies() {
		return ties;
	}

	public void setTies(int newTies) {
		int oldTies = ties;
		ties = newTies;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HockeyleaguePackage.GOALIE_STATS__TIES, oldTies, ties));
	}

	public int getEmptyNetGoals() {
		return emptyNetGoals;
	}

	public void setEmptyNetGoals(int newEmptyNetGoals) {
		int oldEmptyNetGoals = emptyNetGoals;
		emptyNetGoals = newEmptyNetGoals;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HockeyleaguePackage.GOALIE_STATS__EMPTY_NET_GOALS, oldEmptyNetGoals, emptyNetGoals));
	}

	public int getShutouts() {
		return shutouts;
	}

	public void setShutouts(int newShutouts) {
		int oldShutouts = shutouts;
		shutouts = newShutouts;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HockeyleaguePackage.GOALIE_STATS__SHUTOUTS, oldShutouts, shutouts));
	}

	public int getGoalsAgainst() {
		return goalsAgainst;
	}

	public void setGoalsAgainst(int newGoalsAgainst) {
		int oldGoalsAgainst = goalsAgainst;
		goalsAgainst = newGoalsAgainst;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HockeyleaguePackage.GOALIE_STATS__GOALS_AGAINST, oldGoalsAgainst, goalsAgainst));
	}

	public int getSaves() {
		return saves;
	}

	public void setSaves(int newSaves) {
		int oldSaves = saves;
		saves = newSaves;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HockeyleaguePackage.GOALIE_STATS__SAVES, oldSaves, saves));
	}

	public int getPenaltyMinutes() {
		return penaltyMinutes;
	}

	public void setPenaltyMinutes(int newPenaltyMinutes) {
		int oldPenaltyMinutes = penaltyMinutes;
		penaltyMinutes = newPenaltyMinutes;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HockeyleaguePackage.GOALIE_STATS__PENALTY_MINUTES, oldPenaltyMinutes, penaltyMinutes));
	}

	public int getGoals() {
		return goals;
	}

	public void setGoals(int newGoals) {
		int oldGoals = goals;
		goals = newGoals;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HockeyleaguePackage.GOALIE_STATS__GOALS, oldGoals, goals));
	}

	public int getAssists() {
		return assists;
	}

	public void setAssists(int newAssists) {
		int oldAssists = assists;
		assists = newAssists;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HockeyleaguePackage.GOALIE_STATS__ASSISTS, oldAssists, assists));
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int newPoints) {
		int oldPoints = points;
		points = newPoints;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HockeyleaguePackage.GOALIE_STATS__POINTS, oldPoints, points));
	}

	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case HockeyleaguePackage.GOALIE_STATS__YEAR:
				return getYear();
			case HockeyleaguePackage.GOALIE_STATS__TEAM:
				if (resolve) return getTeam();
				return basicGetTeam();
			case HockeyleaguePackage.GOALIE_STATS__GAMES_PLAYED_IN:
				return new Integer(getGamesPlayedIn());
			case HockeyleaguePackage.GOALIE_STATS__MINUTES_PLAYED_IN:
				return new Integer(getMinutesPlayedIn());
			case HockeyleaguePackage.GOALIE_STATS__GOALS_AGAINST_AVERAGE:
				return new Float(getGoalsAgainstAverage());
			case HockeyleaguePackage.GOALIE_STATS__WINS:
				return new Integer(getWins());
			case HockeyleaguePackage.GOALIE_STATS__LOSSES:
				return new Integer(getLosses());
			case HockeyleaguePackage.GOALIE_STATS__TIES:
				return new Integer(getTies());
			case HockeyleaguePackage.GOALIE_STATS__EMPTY_NET_GOALS:
				return new Integer(getEmptyNetGoals());
			case HockeyleaguePackage.GOALIE_STATS__SHUTOUTS:
				return new Integer(getShutouts());
			case HockeyleaguePackage.GOALIE_STATS__GOALS_AGAINST:
				return new Integer(getGoalsAgainst());
			case HockeyleaguePackage.GOALIE_STATS__SAVES:
				return new Integer(getSaves());
			case HockeyleaguePackage.GOALIE_STATS__PENALTY_MINUTES:
				return new Integer(getPenaltyMinutes());
			case HockeyleaguePackage.GOALIE_STATS__GOALS:
				return new Integer(getGoals());
			case HockeyleaguePackage.GOALIE_STATS__ASSISTS:
				return new Integer(getAssists());
			case HockeyleaguePackage.GOALIE_STATS__POINTS:
				return new Integer(getPoints());
		}
		return super.eGet(featureID, resolve, coreType);
	}

	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case HockeyleaguePackage.GOALIE_STATS__YEAR:
				setYear((String)newValue);
				return;
			case HockeyleaguePackage.GOALIE_STATS__TEAM:
				setTeam((Team)newValue);
				return;
			case HockeyleaguePackage.GOALIE_STATS__GAMES_PLAYED_IN:
				setGamesPlayedIn(((Integer)newValue).intValue());
				return;
			case HockeyleaguePackage.GOALIE_STATS__MINUTES_PLAYED_IN:
				setMinutesPlayedIn(((Integer)newValue).intValue());
				return;
			case HockeyleaguePackage.GOALIE_STATS__GOALS_AGAINST_AVERAGE:
				setGoalsAgainstAverage(((Float)newValue).floatValue());
				return;
			case HockeyleaguePackage.GOALIE_STATS__WINS:
				setWins(((Integer)newValue).intValue());
				return;
			case HockeyleaguePackage.GOALIE_STATS__LOSSES:
				setLosses(((Integer)newValue).intValue());
				return;
			case HockeyleaguePackage.GOALIE_STATS__TIES:
				setTies(((Integer)newValue).intValue());
				return;
			case HockeyleaguePackage.GOALIE_STATS__EMPTY_NET_GOALS:
				setEmptyNetGoals(((Integer)newValue).intValue());
				return;
			case HockeyleaguePackage.GOALIE_STATS__SHUTOUTS:
				setShutouts(((Integer)newValue).intValue());
				return;
			case HockeyleaguePackage.GOALIE_STATS__GOALS_AGAINST:
				setGoalsAgainst(((Integer)newValue).intValue());
				return;
			case HockeyleaguePackage.GOALIE_STATS__SAVES:
				setSaves(((Integer)newValue).intValue());
				return;
			case HockeyleaguePackage.GOALIE_STATS__PENALTY_MINUTES:
				setPenaltyMinutes(((Integer)newValue).intValue());
				return;
			case HockeyleaguePackage.GOALIE_STATS__GOALS:
				setGoals(((Integer)newValue).intValue());
				return;
			case HockeyleaguePackage.GOALIE_STATS__ASSISTS:
				setAssists(((Integer)newValue).intValue());
				return;
			case HockeyleaguePackage.GOALIE_STATS__POINTS:
				setPoints(((Integer)newValue).intValue());
				return;
		}
		super.eSet(featureID, newValue);
	}

	public void eUnset(int featureID) {
		switch (featureID) {
			case HockeyleaguePackage.GOALIE_STATS__YEAR:
				setYear(YEAR_EDEFAULT);
				return;
			case HockeyleaguePackage.GOALIE_STATS__TEAM:
				setTeam((Team)null);
				return;
			case HockeyleaguePackage.GOALIE_STATS__GAMES_PLAYED_IN:
				setGamesPlayedIn(GAMES_PLAYED_IN_EDEFAULT);
				return;
			case HockeyleaguePackage.GOALIE_STATS__MINUTES_PLAYED_IN:
				setMinutesPlayedIn(MINUTES_PLAYED_IN_EDEFAULT);
				return;
			case HockeyleaguePackage.GOALIE_STATS__GOALS_AGAINST_AVERAGE:
				setGoalsAgainstAverage(GOALS_AGAINST_AVERAGE_EDEFAULT);
				return;
			case HockeyleaguePackage.GOALIE_STATS__WINS:
				setWins(WINS_EDEFAULT);
				return;
			case HockeyleaguePackage.GOALIE_STATS__LOSSES:
				setLosses(LOSSES_EDEFAULT);
				return;
			case HockeyleaguePackage.GOALIE_STATS__TIES:
				setTies(TIES_EDEFAULT);
				return;
			case HockeyleaguePackage.GOALIE_STATS__EMPTY_NET_GOALS:
				setEmptyNetGoals(EMPTY_NET_GOALS_EDEFAULT);
				return;
			case HockeyleaguePackage.GOALIE_STATS__SHUTOUTS:
				setShutouts(SHUTOUTS_EDEFAULT);
				return;
			case HockeyleaguePackage.GOALIE_STATS__GOALS_AGAINST:
				setGoalsAgainst(GOALS_AGAINST_EDEFAULT);
				return;
			case HockeyleaguePackage.GOALIE_STATS__SAVES:
				setSaves(SAVES_EDEFAULT);
				return;
			case HockeyleaguePackage.GOALIE_STATS__PENALTY_MINUTES:
				setPenaltyMinutes(PENALTY_MINUTES_EDEFAULT);
				return;
			case HockeyleaguePackage.GOALIE_STATS__GOALS:
				setGoals(GOALS_EDEFAULT);
				return;
			case HockeyleaguePackage.GOALIE_STATS__ASSISTS:
				setAssists(ASSISTS_EDEFAULT);
				return;
			case HockeyleaguePackage.GOALIE_STATS__POINTS:
				setPoints(POINTS_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case HockeyleaguePackage.GOALIE_STATS__YEAR:
				return YEAR_EDEFAULT == null ? year != null : !YEAR_EDEFAULT.equals(year);
			case HockeyleaguePackage.GOALIE_STATS__TEAM:
				return team != null;
			case HockeyleaguePackage.GOALIE_STATS__GAMES_PLAYED_IN:
				return gamesPlayedIn != GAMES_PLAYED_IN_EDEFAULT;
			case HockeyleaguePackage.GOALIE_STATS__MINUTES_PLAYED_IN:
				return minutesPlayedIn != MINUTES_PLAYED_IN_EDEFAULT;
			case HockeyleaguePackage.GOALIE_STATS__GOALS_AGAINST_AVERAGE:
				return goalsAgainstAverage != GOALS_AGAINST_AVERAGE_EDEFAULT;
			case HockeyleaguePackage.GOALIE_STATS__WINS:
				return wins != WINS_EDEFAULT;
			case HockeyleaguePackage.GOALIE_STATS__LOSSES:
				return losses != LOSSES_EDEFAULT;
			case HockeyleaguePackage.GOALIE_STATS__TIES:
				return ties != TIES_EDEFAULT;
			case HockeyleaguePackage.GOALIE_STATS__EMPTY_NET_GOALS:
				return emptyNetGoals != EMPTY_NET_GOALS_EDEFAULT;
			case HockeyleaguePackage.GOALIE_STATS__SHUTOUTS:
				return shutouts != SHUTOUTS_EDEFAULT;
			case HockeyleaguePackage.GOALIE_STATS__GOALS_AGAINST:
				return goalsAgainst != GOALS_AGAINST_EDEFAULT;
			case HockeyleaguePackage.GOALIE_STATS__SAVES:
				return saves != SAVES_EDEFAULT;
			case HockeyleaguePackage.GOALIE_STATS__PENALTY_MINUTES:
				return penaltyMinutes != PENALTY_MINUTES_EDEFAULT;
			case HockeyleaguePackage.GOALIE_STATS__GOALS:
				return goals != GOALS_EDEFAULT;
			case HockeyleaguePackage.GOALIE_STATS__ASSISTS:
				return assists != ASSISTS_EDEFAULT;
			case HockeyleaguePackage.GOALIE_STATS__POINTS:
				return points != POINTS_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (year: "); //$NON-NLS-1$
		result.append(year);
		result.append(", gamesPlayedIn: "); //$NON-NLS-1$
		result.append(gamesPlayedIn);
		result.append(", minutesPlayedIn: "); //$NON-NLS-1$
		result.append(minutesPlayedIn);
		result.append(", goalsAgainstAverage: "); //$NON-NLS-1$
		result.append(goalsAgainstAverage);
		result.append(", wins: "); //$NON-NLS-1$
		result.append(wins);
		result.append(", losses: "); //$NON-NLS-1$
		result.append(losses);
		result.append(", ties: "); //$NON-NLS-1$
		result.append(ties);
		result.append(", emptyNetGoals: "); //$NON-NLS-1$
		result.append(emptyNetGoals);
		result.append(", shutouts: "); //$NON-NLS-1$
		result.append(shutouts);
		result.append(", goalsAgainst: "); //$NON-NLS-1$
		result.append(goalsAgainst);
		result.append(", saves: "); //$NON-NLS-1$
		result.append(saves);
		result.append(", penaltyMinutes: "); //$NON-NLS-1$
		result.append(penaltyMinutes);
		result.append(", goals: "); //$NON-NLS-1$
		result.append(goals);
		result.append(", assists: "); //$NON-NLS-1$
		result.append(assists);
		result.append(", points: "); //$NON-NLS-1$
		result.append(points);
		result.append(')');
		return result.toString();
	}

} //GoalieStatsImpl
