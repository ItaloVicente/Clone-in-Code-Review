package org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.ui.properties.sections;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.Defence;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.HockeyleagueFactory;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.HockeyleaguePackage;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.Team;

public class DefencemenPropertySection
	extends AbstractPlayersPropertySection {

	protected String getButtonLabelText() {
		return "Defenceman";//$NON-NLS-1$
	}

	protected List getOwnedRows() {
		ArrayList ret = new ArrayList();
		for (Iterator i = ((Team) eObject).getDefencemen().iterator(); i
			.hasNext();) {
			ret.add(i.next());
		}
		return ret;
	}

	protected List getAdditionalColumnLabelText() {
		ArrayList ret = new ArrayList();
		ret.add("Position");//$NON-NLS-1$
		return ret;
	}

	protected List getAdditionalValuesForRow(Object object) {
		ArrayList ret = new ArrayList();
		ret.add(((Defence) object).getPosition().getName());
		return ret;
	}

	protected EReference getFeature() {
		return HockeyleaguePackage.eINSTANCE.getTeam_Defencemen();
	}

	protected Object getNewChild() {
		return HockeyleagueFactory.eINSTANCE.createDefence();
	}
}
