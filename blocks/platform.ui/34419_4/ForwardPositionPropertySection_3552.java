package org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.ui.properties.sections;

import java.util.List;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.Forward;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.ForwardPositionKind;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.HockeyleaguePackage;

public class ForwardPositionPropertySection
	extends AbstractEnumerationPropertySection {

	protected EAttribute getFeature() {
		return HockeyleaguePackage.eINSTANCE.getForward_Position();
	}

	protected String getFeatureAsText() {
		return ((Forward) eObject).getPosition().getName();
	}

	protected Object getFeatureValue(int index) {
		return ForwardPositionKind.VALUES.get(index);
	}

	protected String getLabelText() {
		return "Position:";//$NON-NLS-1$
	}

	protected boolean isEqual(int index) {
		return ForwardPositionKind.VALUES.get(index).equals(
			((Forward) eObject).getPosition());
	}

	protected String[] getEnumerationFeatureValues() {
		List values = ForwardPositionKind.VALUES;
		String[] ret = new String[values.size()];
		for (int i = 0; i < values.size(); i++) {
			ret[i] = ((ForwardPositionKind) values.get(i)).getName();
		}
		return ret;
	}
}
