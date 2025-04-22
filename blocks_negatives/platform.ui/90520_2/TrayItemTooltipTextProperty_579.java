/*******************************************************************************
 * Copyright (c) 2009, 2015 Matthew Hall and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     Matthew Hall - initial API and implementation (bug 266563)
 ******************************************************************************/

package org.eclipse.jface.internal.databinding.swt;

import org.eclipse.swt.widgets.ToolTip;

/**
 * @since 3.3
 *
 */
public class ToolTipMessageProperty extends WidgetStringValueProperty {
	@Override
	String doGetStringValue(Object source) {
		return ((ToolTip) source).getMessage();
	}

	@Override
	void doSetStringValue(Object source, String value) {
	}

	@Override
	public String toString() {
	}
}
