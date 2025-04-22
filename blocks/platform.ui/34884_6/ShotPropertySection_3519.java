package org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.ui.properties.sections;

import java.util.List;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.HockeyleaguePackage;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.Player;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.ShotKind;

public class ShotPropertySection
	extends AbstractEnumerationPropertySection {

	protected EAttribute getFeature() {
		return HockeyleaguePackage.eINSTANCE.getPlayer_Shot();
	}

	protected String getFeatureAsText() {
		return ((Player) eObject).getShot().getName();
	}

	protected Object getFeatureValue(int index) {
		return ShotKind.VALUES.get(index);
	}

	protected String getLabelText() {
		return "Shot:";//$NON-NLS-1$
	}

	protected boolean isEqual(int index) {
		return ShotKind.VALUES.get(index).equals(((Player) eObject).getShot());
	}

	protected String[] getEnumerationFeatureValues() {
		List values = ShotKind.VALUES;
		String[] ret = new String[values.size()];
		for (int i = 0; i < values.size(); i++) {
			ret[i] = ((ShotKind) values.get(i)).getName();
		}
		return ret;
	}
}
