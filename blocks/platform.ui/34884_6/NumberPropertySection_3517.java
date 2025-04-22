package org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.ui.properties.sections;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.HockeyleaguePackage;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.Player;

public class NumberPropertySection
	extends AbstractIntegerPropertySection {

	protected EAttribute getFeature() {
		return HockeyleaguePackage.eINSTANCE.getPlayer_Number();
	}

	protected Integer getFeatureInteger() {
		return new Integer(((Player) eObject).getNumber());
	}

	protected String getLabelText() {
		return "Jersey Number:";//$NON-NLS-1$
	}

}
