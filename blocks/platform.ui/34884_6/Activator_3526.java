package org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.util;


import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.*;

public class HockeyleagueSwitch {
	protected static HockeyleaguePackage modelPackage;

	public HockeyleagueSwitch() {
		if (modelPackage == null) {
			modelPackage = HockeyleaguePackage.eINSTANCE;
		}
	}

	public Object doSwitch(EObject theEObject) {
		return doSwitch(theEObject.eClass(), theEObject);
	}

	protected Object doSwitch(EClass theEClass, EObject theEObject) {
		if (theEClass.eContainer() == modelPackage) {
			return doSwitch(theEClass.getClassifierID(), theEObject);
		}
		else {
			List eSuperTypes = theEClass.getESuperTypes();
			return
				eSuperTypes.isEmpty() ?
					defaultCase(theEObject) :
					doSwitch((EClass)eSuperTypes.get(0), theEObject);
		}
	}

	protected Object doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case HockeyleaguePackage.ARENA: {
				Arena arena = (Arena)theEObject;
				Object result = caseArena(arena);
				if (result == null) result = caseHockeyleagueObject(arena);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case HockeyleaguePackage.DEFENCE: {
				Defence defence = (Defence)theEObject;
				Object result = caseDefence(defence);
				if (result == null) result = casePlayer(defence);
				if (result == null) result = caseHockeyleagueObject(defence);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case HockeyleaguePackage.FORWARD: {
				Forward forward = (Forward)theEObject;
				Object result = caseForward(forward);
				if (result == null) result = casePlayer(forward);
				if (result == null) result = caseHockeyleagueObject(forward);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case HockeyleaguePackage.GOALIE: {
				Goalie goalie = (Goalie)theEObject;
				Object result = caseGoalie(goalie);
				if (result == null) result = casePlayer(goalie);
				if (result == null) result = caseHockeyleagueObject(goalie);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case HockeyleaguePackage.GOALIE_STATS: {
				GoalieStats goalieStats = (GoalieStats)theEObject;
				Object result = caseGoalieStats(goalieStats);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case HockeyleaguePackage.HOCKEYLEAGUE_OBJECT: {
				HockeyleagueObject hockeyleagueObject = (HockeyleagueObject)theEObject;
				Object result = caseHockeyleagueObject(hockeyleagueObject);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case HockeyleaguePackage.LEAGUE: {
				League league = (League)theEObject;
				Object result = caseLeague(league);
				if (result == null) result = caseHockeyleagueObject(league);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case HockeyleaguePackage.PLAYER: {
				Player player = (Player)theEObject;
				Object result = casePlayer(player);
				if (result == null) result = caseHockeyleagueObject(player);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case HockeyleaguePackage.PLAYER_STATS: {
				PlayerStats playerStats = (PlayerStats)theEObject;
				Object result = casePlayerStats(playerStats);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case HockeyleaguePackage.TEAM: {
				Team team = (Team)theEObject;
				Object result = caseTeam(team);
				if (result == null) result = caseHockeyleagueObject(team);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	public Object caseArena(Arena object) {
		return null;
	}

	public Object caseDefence(Defence object) {
		return null;
	}

	public Object caseForward(Forward object) {
		return null;
	}

	public Object caseGoalie(Goalie object) {
		return null;
	}

	public Object caseGoalieStats(GoalieStats object) {
		return null;
	}

	public Object caseHockeyleagueObject(HockeyleagueObject object) {
		return null;
	}

	public Object caseLeague(League object) {
		return null;
	}

	public Object casePlayer(Player object) {
		return null;
	}

	public Object casePlayerStats(PlayerStats object) {
		return null;
	}

	public Object caseTeam(Team object) {
		return null;
	}

	public Object defaultCase(EObject object) {
		return null;
	}

} //HockeyleagueSwitch
