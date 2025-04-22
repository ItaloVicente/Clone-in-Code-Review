package org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.ui.properties.sections;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.HockeyleaguePackage;

public class BirthPlacePropertySection
	extends AbstractStringPropertySection {

	protected EAttribute getFeature() {
		return HockeyleaguePackage.eINSTANCE.getPlayer_Birthplace();
	}

	protected String getLabelText() {
		return "Birth Place:";//$NON-NLS-1$
	}

}
