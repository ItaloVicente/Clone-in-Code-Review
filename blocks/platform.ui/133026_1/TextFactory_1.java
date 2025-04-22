package org.eclipse.jface.widgets;

import org.eclipse.swt.widgets.Control;

@FunctionalInterface
public interface Property<T extends Control> {
	void apply(T control);
}
