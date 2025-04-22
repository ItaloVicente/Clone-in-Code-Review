package org.eclipse.ui.examples.views.properties.tabbed.hockeyleague;

import org.eclipse.emf.common.util.EList;

public interface Defence extends Player {
	DefencePositionKind getPosition();

	void setPosition(DefencePositionKind value);

	EList getPlayerStats();

} // Defence
