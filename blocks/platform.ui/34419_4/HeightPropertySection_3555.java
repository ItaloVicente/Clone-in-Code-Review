package org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.ui.properties.sections;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.HockeyleaguePackage;


public class HeadOfficePropertySection
	extends AbstractStringPropertySection {

	protected EAttribute getFeature() {
		return HockeyleaguePackage.eINSTANCE.getLeague_Headoffice();
	}

	protected String getLabelText() {
		return "Head Office:"; //$NON-NLS-1$
	}

}
