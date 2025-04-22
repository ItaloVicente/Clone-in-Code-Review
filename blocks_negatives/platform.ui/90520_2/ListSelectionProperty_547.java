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
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.List;

/**
 * @since 3.3
 *
 */
public class ListItemsProperty extends ControlStringListProperty {
	@Override
	protected void doUpdateStringList(final Control control, ListDiff diff) {
		diff.accept(new ListDiffVisitor() {
			List list = (List) control;

			@Override
			public void handleAdd(int index, Object element) {
				list.add((String) element, index);
			}

			@Override
			public void handleRemove(int index, Object element) {
				list.remove(index);
			}


			@Override
			public void handleReplace(int index, Object oldElement,
					Object newElement) {
				list.setItem(index, (String) newElement);
			}
		});
	}

	@Override
	public String[] doGetStringList(Control control) {
		return ((List) control).getItems();
	}

	@Override
	public String toString() {
	}
}
