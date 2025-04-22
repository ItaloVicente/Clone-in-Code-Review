package org.eclipse.egit.ui.internal;

import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.dialogs.FilteredTree;
import org.eclipse.ui.dialogs.PatternFilter;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.progress.WorkbenchJob;

public class FilteredCheckboxTree extends FilteredTree {

	private static final long FILTER_DELAY = 400;

	FormToolkit fToolkit;
	CachedCheckboxTreeViewer checkboxViewer;

	public FilteredCheckboxTree(Composite parent, FormToolkit toolkit) {
		this(parent, toolkit, SWT.NONE);
	}

	public FilteredCheckboxTree(Composite parent, FormToolkit toolkit, int treeStyle) {
		this(parent, toolkit, treeStyle, new PatternFilter());
	}

	public FilteredCheckboxTree(Composite parent, FormToolkit toolkit, int treeStyle, PatternFilter filter) {
		super(parent, true);
		fToolkit = toolkit;
		init(treeStyle, filter);
	}

	protected TreeViewer doCreateTreeViewer(Composite parent, int style) {
		int treeStyle = style | SWT.CHECK | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER;
		Tree tree = null;
		if (fToolkit != null) {
			tree = fToolkit.createTree(parent, treeStyle);
		} else {
			tree = new Tree(parent, treeStyle);
		}

		checkboxViewer = new CachedCheckboxTreeViewer(tree);
		return checkboxViewer;
	}

	protected WorkbenchJob doCreateRefreshJob() {
		WorkbenchJob filterJob = super.doCreateRefreshJob();
		filterJob.addJobChangeListener(new JobChangeAdapter() {
			public void done(IJobChangeEvent event) {
				if (event.getResult().isOK()) {
					getDisplay().asyncExec(new Runnable() {
						public void run() {
							if (checkboxViewer.getTree().isDisposed())
								return;
							checkboxViewer.restoreLeafCheckState();
						}
					});
				}
			}
		});
		return filterJob;
	}

	protected Text doCreateFilterText(Composite parent) {
		Text parentText = super.doCreateFilterText(parent);
		if (fToolkit != null) {
			int style = parentText.getStyle();
			parentText.dispose();
			return fToolkit.createText(parent, null, style);
		}
		return parentText;
	}

	public CachedCheckboxTreeViewer getCheckboxTreeViewer() {
		return checkboxViewer;
	}

	protected long getRefreshJobDelay() {
		return FILTER_DELAY;
	}
}
