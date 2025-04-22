package org.eclipse.ui.examples.views.properties.tabbed.hockeyleague;

import org.eclipse.emf.common.util.EList;

public interface Team extends HockeyleagueObject {
	EList getForwards();

	EList getDefencemen();

	EList getGoalies();

	Arena getArena();

	void setArena(Arena value);

} // Team
