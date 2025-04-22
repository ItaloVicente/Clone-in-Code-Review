package org.eclipse.ui.internal.persistence;

import org.eclipse.emf.ecore.EObject;

public interface PartMemento extends EObject {

	String getPartId();

	void setPartId(String value);

	String getMemento();

	void setMemento(String value);

} // PartMemento
