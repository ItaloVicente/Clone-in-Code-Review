package org.eclipse.e4.ui.dialogs.filteredtree;

import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.e4.ui.dialogs.viewer.CachedCheckboxTreeViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;

public class FilteredCheckboxTree extends FilteredTree {
	private static final long FILTER_DELAY = 400;

	private CachedCheckboxTreeViewer checkboxViewer;

	public FilteredCheckboxTree(Composite parent) {
		this(parent, SWT.NONE);
	}

	public FilteredCheckboxTree(Composite parent, int treeStyle) {
		this(parent, treeStyle, new PatternFilter());
	}

	public FilteredCheckboxTree(Composite parent, int treeStyle, PatternFilter filter) {
		super(parent, treeStyle, filter);
	}

	@Override
	protected TreeViewer doCreateTreeViewer(Composite parent, int style) {
		int treeStyle = style | SWT.CHECK | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER;
		Tree tree = new Tree(parent, treeStyle);
		checkboxViewer = new CachedCheckboxTreeViewer(tree);
		return checkboxViewer;
	}

	@Override
	protected BasicUIJob doCreateRefreshJob() {
		BasicUIJob filterJob = super.doCreateRefreshJob();
		filterJob.addJobChangeListener(new JobChangeAdapter() {
			@Override
			public void done(IJobChangeEvent event) {
				if (event.getResult().isOK()) {
					getDisplay().asyncExec(new Runnable() {
						@Override
						public void run() {
							if (checkboxViewer.getTree().isDisposed()) {
								return;
							}
							checkboxViewer.restoreLeafCheckState();
						}
					});
				}
			}
		});
		return filterJob;
	}

	public CachedCheckboxTreeViewer getCheckboxTreeViewer() {
		return checkboxViewer;
	}

	@Override
	protected long getRefreshJobDelay() {
		return FILTER_DELAY;
	}

}
