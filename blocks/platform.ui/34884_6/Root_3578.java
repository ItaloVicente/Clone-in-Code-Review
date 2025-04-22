package org.eclipse.e4.emf.xpath.test.model.xpathtest;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

public interface Node extends MenuContainer {
	Node getParent();

	void setParent(Node value);

	EList<Node> getChildren();

	Root getRoot();

	void setRoot(Root value);

	String getCat();

	void setCat(String value);

	String getValue();

	void setValue(String value);

	String getId();

	void setId(String value);

	EList<Node> getInrefs();

	EList<Node> getOutrefs();

} // Node
