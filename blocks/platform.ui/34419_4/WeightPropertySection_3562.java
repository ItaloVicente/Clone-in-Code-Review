package org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.ui.properties.sections;

import java.util.List;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.HockeyleaguePackage;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.Player;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.WeightKind;

public class WeightPropertySection
	extends AbstractMeasurementPropertySection {

	protected boolean isEnumerationEqual(int index) {
		return WeightKind.VALUES.get(index).equals(
			((Player) eObject).getWeightMesurement());
	}

	protected EAttribute getEnumerationFeature() {
		return HockeyleaguePackage.eINSTANCE.getPlayer_WeightMesurement();
	}

	protected String[] getEnumerationLabels() {
		List values = WeightKind.VALUES;
		String[] ret = new String[values.size()];
		for (int i = 0; i < values.size(); i++) {
			ret[i] = ((WeightKind) values.get(i)).getName();
		}
		return ret;
	}

	protected int getEnumerationIndex() {
		return ((Player) eObject).getWeightMesurement().getValue();
	}

	protected Object getEnumerationFeatureValue(int index) {
		return WeightKind.VALUES.get(index);
	}

	protected Integer getFeatureInteger() {
		return new Integer(((Player) eObject).getWeightValue());
	}

	protected EAttribute getFeature() {
		return HockeyleaguePackage.eINSTANCE.getPlayer_WeightValue();
	}

	protected String getLabelText() {
		return "Weight:";//$NON-NLS-1$
	}
}
