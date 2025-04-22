package org.eclipse.e4.ui.workbench.internal.persistence;

import org.eclipse.emf.ecore.EObject;

public interface IPartMemento extends EObject {
	String getPartId();

	void setPartId(String value);

	String getMemento();

	void setMemento(String value);

} // IPartMemento
