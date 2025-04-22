package org.eclipse.ui.internal.progress;

import org.eclipse.jface.viewers.StructuredViewer;

public abstract class AbstractProgressViewer extends StructuredViewer {

	public abstract void add(Object[] elements);

	public abstract void remove(Object[] elements);
}
