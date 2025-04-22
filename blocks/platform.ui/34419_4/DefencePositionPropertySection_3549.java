package org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.ui.properties.sections;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.Arena;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.HockeyleaguePackage;


public class CapacityPropertySection
	extends AbstractIntegerPropertySection {

	protected EAttribute getFeature() {
		return HockeyleaguePackage.eINSTANCE.getArena_Capacity();
	}

	protected Integer getFeatureInteger() {
		Arena arena = (Arena) eObject;
		return new Integer(arena.getCapacity());
	}

	protected String getLabelText() {
		return "Capacity:";//$NON-NLS-1$
	}

}
