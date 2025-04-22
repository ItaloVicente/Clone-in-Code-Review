package org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.ui.properties.sections;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.HockeyleaguePackage;

public class AddressPropertySection
	extends AbstractStringPropertySection {

	protected EAttribute getFeature() {
		return HockeyleaguePackage.eINSTANCE.getArena_Address();
	}

	protected String getLabelText() {
		return "Address:"; //$NON-NLS-1$
	}

}
