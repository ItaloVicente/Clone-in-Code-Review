package org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.util;


import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.*;

public class HockeyleagueAdapterFactory extends AdapterFactoryImpl {
	protected static HockeyleaguePackage modelPackage;

	public HockeyleagueAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = HockeyleaguePackage.eINSTANCE;
		}
	}

	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	protected HockeyleagueSwitch modelSwitch =
		new HockeyleagueSwitch() {
			public Object caseArena(Arena object) {
				return createArenaAdapter();
			}
			public Object caseDefence(Defence object) {
				return createDefenceAdapter();
			}
			public Object caseForward(Forward object) {
				return createForwardAdapter();
			}
			public Object caseGoalie(Goalie object) {
				return createGoalieAdapter();
			}
			public Object caseGoalieStats(GoalieStats object) {
				return createGoalieStatsAdapter();
			}
			public Object caseHockeyleagueObject(HockeyleagueObject object) {
				return createHockeyleagueObjectAdapter();
			}
			public Object caseLeague(League object) {
				return createLeagueAdapter();
			}
			public Object casePlayer(Player object) {
				return createPlayerAdapter();
			}
			public Object casePlayerStats(PlayerStats object) {
				return createPlayerStatsAdapter();
			}
			public Object caseTeam(Team object) {
				return createTeamAdapter();
			}
			public Object defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	public Adapter createAdapter(Notifier target) {
		return (Adapter)modelSwitch.doSwitch((EObject)target);
	}


	public Adapter createArenaAdapter() {
		return null;
	}

	public Adapter createDefenceAdapter() {
		return null;
	}

	public Adapter createForwardAdapter() {
		return null;
	}

	public Adapter createGoalieAdapter() {
		return null;
	}

	public Adapter createGoalieStatsAdapter() {
		return null;
	}

	public Adapter createHockeyleagueObjectAdapter() {
		return null;
	}

	public Adapter createLeagueAdapter() {
		return null;
	}

	public Adapter createPlayerAdapter() {
		return null;
	}

	public Adapter createPlayerStatsAdapter() {
		return null;
	}

	public Adapter createTeamAdapter() {
		return null;
	}

	public Adapter createEObjectAdapter() {
		return null;
	}

} //HockeyleagueAdapterFactory
