package org.eclipse.ui.internal.views.log;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.core.runtime.PlatformObject;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.model.IWorkbenchAdapter;

public abstract class AbstractEntry extends PlatformObject implements IWorkbenchAdapter {

	private List<AbstractEntry> children = new ArrayList<>();
	protected Object parent;

	public void addChild(AbstractEntry child) {
		if (child != null) {
			children.add(0, child);
			child.setParent(this);
		}
	}

	@Override
	public Object[] getChildren(Object parent) {
		return children.toArray();
	}

	public boolean hasChildren() {
		return children.size() > 0;
	}

	public int size() {
		return children.size();
	}

	@Override
	public ImageDescriptor getImageDescriptor(Object object) {
		return null;
	}

	@Override
	public String getLabel(Object o) {
		return null;
	}

	@Override
	public Object getParent(Object o) {
		return parent;
	}

	public void setParent(AbstractEntry parent) {
		this.parent = parent;
	}

	public void removeChildren(List<AbstractEntry> list) {
		children.removeAll(list);
	}

	public void removeAllChildren() {
		children.clear();
	}

	public abstract void write(PrintWriter writer);
}
