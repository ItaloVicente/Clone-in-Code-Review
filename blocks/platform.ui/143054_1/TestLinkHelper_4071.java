package org.eclipse.ui.tests.navigator.extension;

import org.eclipse.jface.viewers.IToolTipProvider;

public class TestLabelProviderTooltips extends TestLabelProvider implements IToolTipProvider {
	@Override
	public String getToolTipText(Object element) {
		return "ToolTip " + getText(element);
	}
}
