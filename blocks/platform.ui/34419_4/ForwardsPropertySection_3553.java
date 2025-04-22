package org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.ui.properties.sections;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.Forward;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.HockeyleagueFactory;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.HockeyleaguePackage;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.Team;


public class ForwardsPropertySection
	extends AbstractPlayersPropertySection {

	protected String getButtonLabelText() {
		return "Forward";//$NON-NLS-1$
	}

	protected List getOwnedRows() {
		ArrayList ret = new ArrayList();
		for (Iterator i = ((Team) eObject).getForwards().iterator(); i
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
		ret.add(((Forward) object).getPosition().getName());
		return ret;
	}

	protected EReference getFeature() {
		return HockeyleaguePackage.eINSTANCE.getTeam_Forwards();
	}

	protected Object getNewChild() {
		return HockeyleagueFactory.eINSTANCE.createForward();
	}
}
