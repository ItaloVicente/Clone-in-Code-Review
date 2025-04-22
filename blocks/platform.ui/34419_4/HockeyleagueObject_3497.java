package org.eclipse.ui.examples.views.properties.tabbed.hockeyleague;

import org.eclipse.emf.ecore.EFactory;

public interface HockeyleagueFactory extends EFactory {
	HockeyleagueFactory eINSTANCE = org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.impl.HockeyleagueFactoryImpl.init();

	Arena createArena();

	Defence createDefence();

	Forward createForward();

	Goalie createGoalie();

	GoalieStats createGoalieStats();

	League createLeague();

	PlayerStats createPlayerStats();

	Team createTeam();

	HockeyleaguePackage getHockeyleaguePackage();

} //HockeyleagueFactory
