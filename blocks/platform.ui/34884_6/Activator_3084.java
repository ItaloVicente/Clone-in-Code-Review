package org.eclipse.e4.demo.cssbridge.model;

import java.util.ArrayList;
import java.util.List;

public class TreeItem {
	private TreeItem parent;

	private List<TreeItem> children;

	private Object value;

	public TreeItem(TreeItem parent, Object value) {
		this.parent = parent;
		this.value = value;
	}

	public Object getValue() {
		return value;
	}

	public TreeItem getParent() {
		return parent;
	}

	public void addChild(TreeItem child) {
		if (children == null) {
			children = new ArrayList<TreeItem>();
		}
		children.add(child);
	}

	public List<TreeItem> getChildren() {
		return children;
	}

	@Override
	public String toString() {
		return value != null ? value.toString() : "";
	}
}
