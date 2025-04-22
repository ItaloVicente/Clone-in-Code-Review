package org.eclipse.e4.ui.progress.internal;

import org.eclipse.jface.viewers.StructuredViewer;

public abstract class AbstractProgressViewer extends StructuredViewer {

	public abstract void add(Object[] elements);

	public abstract void remove(Object[] elements);
}
