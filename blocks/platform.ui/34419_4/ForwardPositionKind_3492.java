package org.eclipse.ui.examples.views.properties.tabbed.hockeyleague;

import org.eclipse.emf.common.util.EList;

public interface Forward extends Player {
	ForwardPositionKind getPosition();

	void setPosition(ForwardPositionKind value);

	EList getPlayerStats();

} // Forward
