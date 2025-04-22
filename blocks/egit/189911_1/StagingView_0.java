package org.eclipse.egit.ui.internal.staging;

import org.eclipse.egit.ui.internal.dialogs.SpellcheckableMessageArea;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class CommitMessagePreviewer {

	private SpellcheckableMessageArea viewer;

	public Control createControl(Composite parent) {
		viewer = new SpellcheckableMessageArea(parent, "", true, SWT.NONE) { //$NON-NLS-1$

			@Override
			protected int getViewerStyles() {
				return SWT.V_SCROLL | SWT.H_SCROLL;
			}

			@Override
			protected void configureHardWrap() {
			}

			@Override
			protected void createMarginPainter() {
			}
		};
		GridDataFactory.fillDefaults().grab(true, true).applyTo(viewer);
		return viewer;
	}

	public void setText(Repository repository, String text) {
		viewer.setText(text);
	}
}
