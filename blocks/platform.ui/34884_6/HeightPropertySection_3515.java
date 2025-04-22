package org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.ui.properties.sections;

import java.util.List;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.HeightKind;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.HockeyleaguePackage;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.Player;

public class HeightPropertySection
	extends AbstractMeasurementPropertySection {

	protected boolean isEnumerationEqual(int index) {
		return HeightKind.VALUES.get(index).equals(
			((Player) eObject).getHeightMesurement());
	}

	protected EAttribute getEnumerationFeature() {
		return HockeyleaguePackage.eINSTANCE.getPlayer_HeightMesurement();
	}

	protected String[] getEnumerationLabels() {
		List values = HeightKind.VALUES;
		String[] ret = new String[values.size()];
		for (int i = 0; i < values.size(); i++) {
			ret[i] = ((HeightKind) values.get(i)).getName();
		}
		return ret;
	}

	protected int getEnumerationIndex() {
		return ((Player) eObject).getHeightMesurement().getValue();
	}

	protected Object getEnumerationFeatureValue(int index) {
		return HeightKind.VALUES.get(index);
	}

	protected Integer getFeatureInteger() {
		return new Integer(((Player) eObject).getHeightValue());
	}

	protected EAttribute getFeature() {
		return HockeyleaguePackage.eINSTANCE.getPlayer_HeightValue();
	}

	protected String getLabelText() {
		return "Height:";//$NON-NLS-1$
	}
}
