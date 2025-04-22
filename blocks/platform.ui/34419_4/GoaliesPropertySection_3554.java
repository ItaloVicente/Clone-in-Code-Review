package org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.ui.properties.sections;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.HockeyleagueFactory;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.HockeyleaguePackage;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.Team;

public class GoaliesPropertySection
	extends AbstractPlayersPropertySection {

	protected String getButtonLabelText() {
		return "Goalie";//$NON-NLS-1$
	}

	protected List getOwnedRows() {
		ArrayList ret = new ArrayList();
		for (Iterator i = ((Team) eObject).getGoalies().iterator(); i.hasNext();) {
			ret.add(i.next());
		}
		return ret;
	}

	protected List getAdditionalColumnLabelText() {
		return new ArrayList();
	}

	protected List getAdditionalValuesForRow(Object object) {
		return new ArrayList();
	}

	protected EReference getFeature() {
		return HockeyleaguePackage.eINSTANCE.getTeam_Goalies();
	}

	protected Object getNewChild() {
		return HockeyleagueFactory.eINSTANCE.createGoalie();
	}
}
