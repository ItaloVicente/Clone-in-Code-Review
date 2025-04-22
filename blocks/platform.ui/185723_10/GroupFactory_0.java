package org.eclipse.jface.widgets;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

public class GroupFactory extends AbstractCompositeFactory<GroupFactory, Group> {

	private GroupFactory(int style) {
		super(GroupFactory.class, (Composite parent) -> new Group(parent, style));
	}

	public static GroupFactory newGroup(int style) {
		return new GroupFactory(style);
	}

	public GroupFactory text(String text) {
		addProperty(g -> g.setText(text));
		return this;
	}
}
