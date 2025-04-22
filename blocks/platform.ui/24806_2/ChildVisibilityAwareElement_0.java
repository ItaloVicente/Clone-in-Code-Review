package org.eclipse.e4.ui.css.core.dom;

import java.util.List;
import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ArrayNodeList implements NodeList {
	private Object[] elements;
	private CSSEngine engine;

	public ArrayNodeList(List<?> elements, CSSEngine engine) {
		this(elements.toArray(), engine);
	}

	public ArrayNodeList(Object[] elements, CSSEngine engine) {
		this.elements = elements;
		this.engine = engine;
	}

	@Override
	public int getLength() {
		return elements.length;
	}

	@Override
	public Node item(int index) {
		return engine.getElement(elements[index]);
	}
}
