package org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.ui.properties.sections;

import java.util.List;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.Defence;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.DefencePositionKind;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.HockeyleaguePackage;

public class DefencePositionPropertySection
	extends AbstractEnumerationPropertySection {

	protected EAttribute getFeature() {
		return HockeyleaguePackage.eINSTANCE.getDefence_Position();
	}

	protected String getFeatureAsText() {
		return ((Defence) eObject).getPosition().getName();
	}

	protected Object getFeatureValue(int index) {
		return DefencePositionKind.VALUES.get(index);
	}

	protected String getLabelText() {
		return "Position:";//$NON-NLS-1$
	}

	protected boolean isEqual(int index) {
		return DefencePositionKind.VALUES.get(index).equals(
			((Defence) eObject).getPosition());
	}

	protected String[] getEnumerationFeatureValues() {
		List values = DefencePositionKind.VALUES;
		String[] ret = new String[values.size()];
		for (int i = 0; i < values.size(); i++) {
			ret[i] = ((DefencePositionKind) values.get(i)).getName();
		}
		return ret;
	}
}
