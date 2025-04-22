/*******************************************************************************
 * Copyright (c) 2008, 2015 Matthew Hall and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     Matthew Hall - initial API and implementation (bug 194734)
 *     Matthew Hall - bug 251611
 ******************************************************************************/

package org.eclipse.jface.internal.databinding.swt;

import org.eclipse.core.databinding.observable.list.ListDiff;
import org.eclipse.core.databinding.observable.list.ListDiffVisitor;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Control;

/**
 * @since 3.3
 *
 */
public class ComboItemsProperty extends ControlStringListProperty {
	@Override
	protected void doUpdateStringList(final Control control, ListDiff diff) {
		diff.accept(new ListDiffVisitor() {
			Combo combo = (Combo) control;

			@Override
			public void handleAdd(int index, Object element) {
				combo.add((String) element, index);
			}

			@Override
			public void handleRemove(int index, Object element) {
				combo.remove(index);
			}


			@Override
			public void handleReplace(int index, Object oldElement,
					Object newElement) {
				combo.setItem(index, (String) newElement);
			}
		});
	}

	@Override
	public String[] doGetStringList(Control control) {
		return ((Combo) control).getItems();
	}

	@Override
	public String toString() {
	}
}
