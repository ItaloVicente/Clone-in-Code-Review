package org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.ui.properties.sections;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.HockeyleaguePackage;

public class NamePropertySection
	extends AbstractStringPropertySection {

	protected String getLabelText() {
		return "Name:"; //$NON-NLS-1$
	}

	protected EAttribute getFeature() {
		return HockeyleaguePackage.eINSTANCE.getHockeyleagueObject_Name();
	}
}
