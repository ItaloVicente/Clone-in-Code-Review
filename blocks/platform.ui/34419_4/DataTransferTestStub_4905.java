package org.eclipse.ui.tests.decorators;

import java.util.ArrayList;
import java.util.Collection;

public class TreeElement extends TestElement {
	int level;

	TreeElement parent;

	Collection children = new ArrayList(10);

	TreeElement(TreeElement parent, int index) {
		if (parent == null) {
			name = "Root";
			level = 0;
		} else {
			level = parent.level + 1;
			name = "Level" + String.valueOf(level) + " - " + String.valueOf(index);
			parent.children.add(this);
		}
	}
}
