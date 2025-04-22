package org.eclipse.e4.ui.model.application.ui.basic;

import org.eclipse.e4.ui.model.application.commands.MBindings;
import org.eclipse.e4.ui.model.application.commands.MHandlerContainer;

import org.eclipse.e4.ui.model.application.ui.MContext;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUILabel;

public interface MFrame extends MElementContainer<MFrameElement>, MUILabel, MContext, MHandlerContainer, MBindings {
	int getX();

	void setX(int value);

	int getY();

	void setY(int value);

	int getWidth();

	void setWidth(int value);

	int getHeight();

	void setHeight(int value);

} // MFrame
