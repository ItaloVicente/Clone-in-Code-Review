package org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.ui.properties.sections;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.HockeyleaguePackage;

public class BirthDatePropertySection
	extends AbstractStringPropertySection {

	protected EAttribute getFeature() {
		return HockeyleaguePackage.eINSTANCE.getPlayer_Birthdate();
	}

	protected String getLabelText() {
		return "Birth Date:";//$NON-NLS-1$
	}

}
