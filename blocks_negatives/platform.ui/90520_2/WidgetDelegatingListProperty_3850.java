/*******************************************************************************
 * Copyright (c) 2009, 2014 Matthew Hall and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     Matthew Hall - initial API and implementation (bug 264286)
 *******************************************************************************/

package org.eclipse.jface.internal.databinding.swt;

import org.eclipse.core.databinding.property.list.DelegatingListProperty;
import org.eclipse.jface.databinding.swt.DisplayRealm;
import org.eclipse.jface.databinding.swt.ISWTObservableList;
import org.eclipse.jface.databinding.swt.IWidgetListProperty;
import org.eclipse.swt.widgets.Widget;

abstract class WidgetDelegatingListProperty extends DelegatingListProperty
		implements IWidgetListProperty {
	RuntimeException notSupported(Object source) {
		return new IllegalArgumentException(
	}

	public WidgetDelegatingListProperty(Object elementType) {
		super(elementType);
	}

	@Override
	public ISWTObservableList observe(Widget widget) {
		return (ISWTObservableList) observe(DisplayRealm.getRealm(widget
				.getDisplay()), widget);
	}
}
