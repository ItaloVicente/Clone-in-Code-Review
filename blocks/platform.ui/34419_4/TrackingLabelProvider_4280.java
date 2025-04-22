package org.eclipse.ui.tests.navigator.extension;

import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;

public abstract class TestStyledLabelProvider extends TestLabelProvider implements
		IStyledLabelProvider {

	@Override
	public StyledString getStyledText(Object element) {
		if (_blank || _null)
			return new StyledString("");
		return new StyledString(getText(element));
	}

}
