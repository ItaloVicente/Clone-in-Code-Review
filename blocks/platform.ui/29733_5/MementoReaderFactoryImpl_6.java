
package org.eclipse.ui.internal.e4.migration;

import java.util.Arrays;
import javax.inject.Inject;
import org.eclipse.ui.IMemento;

public class MementoReader {

	@Inject
	protected IMemento memento;

	protected String getString(String attribute) {
		return memento.getString(attribute);
	}

	protected boolean getBoolean(String attribute) {
		return Boolean.TRUE.equals(memento.getBoolean(attribute));
	}

	protected Integer getInteger(String attribute) {
		return memento.getInteger(attribute);
	}

	protected Float getFloat(String attribute) {
		return memento.getFloat(attribute);
	}

	protected boolean contains(String attribute) {
		return Arrays.asList(memento.getAttributeKeys()).contains(attribute);
	}

	protected IMemento[] getChildren(String tagName) {
		return memento.getChildren(tagName);
	}

	protected IMemento getChild(String tagName) {
		return memento.getChild(tagName);
	}

	public IMemento getMemento() {
		return memento;
	}

}
