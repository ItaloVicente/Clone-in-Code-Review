/*******************************************************************************
 * Copyright (c) 2008, 2015 Matthew Hall and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     Matthew Hall - initial API and implementation (bug 194734)
 ******************************************************************************/

package org.eclipse.jface.internal.databinding.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;

/**
 * @since 3.3
 *
 */
public class CComboTextProperty extends WidgetStringValueProperty {
	/**
	 *
	 */
	public CComboTextProperty() {
		super(SWT.Modify);
	}

	@Override
	String doGetStringValue(Object source) {
		return ((CCombo) source).getText();
	}

	@Override
	void doSetStringValue(Object source, String value) {
	}

	@Override
	public String toString() {
	}
}
