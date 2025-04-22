package org.eclipse.egit.ui.internal.dialogs;

import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.egit.ui.UIText;
import org.eclipse.egit.ui.internal.GitLabelProvider;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class UnmergedBranchDialog<T> extends MessageDialog {
	private final List<T> nodes;

	public UnmergedBranchDialog(Shell parentShell, List<T> nodes) {
		super(parentShell, UIText.UnmergedBranchDialog_Title, null,
				UIText.UnmergedBranchDialog_Message, MessageDialog.QUESTION,
				new String[] { IDialogConstants.OK_LABEL,
						IDialogConstants.CANCEL_LABEL }, 0);
		this.nodes = nodes;
	}

	@Override
	protected Control createCustomArea(Composite parent) {
		Composite area = new Composite(parent, SWT.NONE);
		area.setLayoutData(new GridData(GridData.FILL_BOTH));
		area.setLayout(new FillLayout());

		TableViewer branchesList = new TableViewer(area);
		branchesList.setContentProvider(ArrayContentProvider.getInstance());
		branchesList.setLabelProvider(new GitLabelProvider());
		branchesList.setInput(nodes);
		return area;
	}

}
