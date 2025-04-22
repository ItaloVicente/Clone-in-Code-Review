package org.eclipse.egit.ui.internal.blame;

import org.eclipse.jface.text.AbstractReusableInformationControlCreator;
import org.eclipse.jface.text.IInformationControl;
import org.eclipse.swt.widgets.Shell;

public class BlameInformationControlCreator extends
		AbstractReusableInformationControlCreator {

	private boolean resizable;

	public BlameInformationControlCreator(boolean resizable) {
		this.resizable = resizable;
	}

	@Override
	protected IInformationControl doCreateInformationControl(Shell parent) {
		return new BlameInformationControl(parent, resizable,
				new BlameInformationControlCreator(true));
	}

}
