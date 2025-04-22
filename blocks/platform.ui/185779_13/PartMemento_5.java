package org.eclipse.e4.ui.workbench.internal.persistence;

import org.eclipse.e4.ui.model.application.ui.advanced.MArea;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspective;

import org.eclipse.e4.ui.model.application.ui.basic.MTrimBar;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

public interface IWorkbenchState extends EObject {
	MPerspective getPerspective();

	void setPerspective(MPerspective value);

	EList<IPartMemento> getViewSettings();

	MArea getEditorArea();

	void setEditorArea(MArea value);

	EList<MTrimBar> getTrimBars();

} // IWorkbenchState
