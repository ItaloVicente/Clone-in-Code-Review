
package org.eclipse.ui.internal.e4.migration;

import java.io.IOException;
import java.io.StringWriter;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.XMLMemento;
import org.eclipse.ui.internal.WorkbenchPlugin;

public class MementoSerializer {

	private IMemento memento;

	public MementoSerializer(IMemento memento) {
		this.memento = memento;
	}

	public String serialize() {
		if (!(memento instanceof XMLMemento)) {
			return null;
		}
		StringWriter writer = new StringWriter();
		try {
			((XMLMemento) memento).save(writer);
		} catch (IOException e) {
			WorkbenchPlugin.log(e);
		}
		return writer.toString();
	}
}
