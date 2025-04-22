
package org.eclipse.jface.tests.labelProviders;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;

public class TestColorAndFontLabelProvider extends LabelProvider implements
		IColorProvider, ILabelProvider {
	private final Display fDisplay;

	public TestColorAndFontLabelProvider(Display display) {
		fDisplay= display;
	}

	@Override
	public Color getBackground(Object element) {
		return fDisplay.getSystemColor(SWT.COLOR_RED);
	}

	@Override
	public Color getForeground(Object element) {
		return fDisplay.getSystemColor(SWT.COLOR_BLUE);
	}
}
