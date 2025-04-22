package org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.ui.properties.sections;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.Defence;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.Forward;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.HockeyleagueFactory;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.HockeyleaguePackage;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.PlayerStats;

public class PlayerStatsPropertySection
	extends AbstractTablePropertySection {

	protected List getColumnLabelText() {
		ArrayList ret = new ArrayList();
		ret.add("Year");//$NON-NLS-1$
		return ret;
	}

	protected String getKeyForRow(Object object) {
		return ((PlayerStats) object).getYear();
	}

	protected List getValuesForRow(Object object) {
		ArrayList ret = new ArrayList();
		ret.add(((PlayerStats) object).getYear());
		return ret;
	}

	protected String getButtonLabelText() {
		return "Player Stats";//$NON-NLS-1$
	}

	protected List getOwnedRows() {

		ArrayList ret = new ArrayList();
		for (Iterator i = (eObject instanceof Defence) ? ((Defence) eObject)
			.getPlayerStats().iterator()
			: ((Forward) eObject).getPlayerStats().iterator(); i.hasNext();) {
			ret.add(i.next());
		}
		return ret;
	}

	protected EReference getFeature() {
		return (eObject instanceof Defence) ? HockeyleaguePackage.eINSTANCE
			.getDefence_PlayerStats()
			: HockeyleaguePackage.eINSTANCE.getForward_PlayerStats();
	}

	protected Object getNewChild() {
		return HockeyleagueFactory.eINSTANCE.createPlayerStats();
	}
}
