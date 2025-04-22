package org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.ui.properties.sections;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.HockeyleagueFactory;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.HockeyleaguePackage;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.League;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.Team;

public class TeamsPropertySection
	extends AbstractTablePropertySection {

	protected List getColumnLabelText() {
		ArrayList ret = new ArrayList();
		ret.add("Name");//$NON-NLS-1$
		return ret;
	}

	protected String getKeyForRow(Object object) {
		return ((Team) object).getName();
	}

	protected List getValuesForRow(Object object) {
		ArrayList ret = new ArrayList();
		ret.add(((Team) object).getName());
		return ret;
	}

	protected String getButtonLabelText() {
		return "Team";//$NON-NLS-1$
	}

	protected List getOwnedRows() {
		ArrayList ret = new ArrayList();
		for (Iterator i = ((League) eObject).getTeams().iterator(); i.hasNext();) {
			ret.add(i.next());
		}
		return ret;
	}

	protected EReference getFeature() {
		return HockeyleaguePackage.eINSTANCE.getLeague_Teams();
	}

	protected Object getNewChild() {
		return HockeyleagueFactory.eINSTANCE.createTeam();
	}
}
