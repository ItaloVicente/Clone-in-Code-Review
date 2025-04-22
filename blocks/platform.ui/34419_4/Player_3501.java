package org.eclipse.ui.examples.views.properties.tabbed.hockeyleague;

import org.eclipse.emf.common.util.EList;

public interface League extends HockeyleagueObject {
	String getHeadoffice();

	void setHeadoffice(String value);

	EList getTeams();

} // League
