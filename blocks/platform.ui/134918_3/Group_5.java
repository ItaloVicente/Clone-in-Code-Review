package org.eclipse.ui.internal.views.log;

import java.io.PrintWriter;

public class Group extends AbstractEntry {

	private String name;

	public Group(String name) {
		this.name = name;
	}

	@Override
	public void write(PrintWriter writer) {
		Object[] children = getChildren(null);
		for (Object element : children) {
			AbstractEntry entry = (AbstractEntry) element;
			entry.write(writer);
			writer.println();
		}
	}

	@Override
	public String toString() {
		return name;
	}

}
