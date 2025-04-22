package org.eclipse.egit.ui.internal.components;

import org.eclipse.egit.ui.internal.UIText;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.lib.BranchConfig.BranchRebaseMode;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class BranchRebaseModeCombo {

	private final @NonNull Label label;

	private final @NonNull ComboViewer combo;

	public BranchRebaseModeCombo(Composite parent) {
		label = new Label(parent, SWT.NONE);
		label.setText(UIText.BranchRebaseModeCombo_RebaseModeLabel);
		combo = new ComboViewer(parent, SWT.READ_ONLY | SWT.DROP_DOWN);
		combo.setContentProvider(ArrayContentProvider.getInstance());
		combo.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				if (element == BranchRebaseMode.REBASE) {
					return UIText.BranchRebaseMode_Rebase;
				} else if (element == BranchRebaseMode.PRESERVE) {
					return UIText.BranchRebaseMode_Preserve;
				} else if (element == BranchRebaseMode.INTERACTIVE) {
					return UIText.BranchRebaseMode_Interactive;
				} else if (element == BranchRebaseMode.NONE) {
					return UIText.BranchRebaseMode_None;
				}
				return ""; //$NON-NLS-1$
			}
		});
		combo.setInput(BranchRebaseMode.values());
	}

	public BranchRebaseMode getRebaseMode() {
		IStructuredSelection selection = (IStructuredSelection) combo
				.getSelection();
		return selection.isEmpty() ? null
				: (BranchRebaseMode) selection.getFirstElement();

	}

	public void setRebaseMode(BranchRebaseMode mode) {
		if (mode == null) {
			combo.setSelection(StructuredSelection.EMPTY, false);
		} else {
			combo.setSelection(new StructuredSelection(mode), true);
		}
	}

	public @NonNull Label getLabel() {
		return label;
	}

	public @NonNull ComboViewer getViewer() {
		return combo;
	}

	public void setEnabled(boolean enabled) {
		label.setEnabled(enabled);
		combo.getCombo().setEnabled(enabled);
	}

	public boolean isEnabled() {
		return combo.getCombo().isEnabled();
	}

}
