package org.eclipse.egit.ui.internal.blame;

import org.eclipse.jface.text.AbstractReusableInformationControlCreator;
import org.eclipse.jface.text.IInformationControl;
import org.eclipse.swt.widgets.Shell;

public class AnnotationInformationControlCreator extends
		AbstractReusableInformationControlCreator {

	private boolean resizable;

	public AnnotationInformationControlCreator(boolean resizable) {
		this.resizable = resizable;
	}

	protected IInformationControl doCreateInformationControl(Shell parent) {
		return new AnnotationInformationControl(parent, resizable,
				new AnnotationInformationControlCreator(true));
	}

}
