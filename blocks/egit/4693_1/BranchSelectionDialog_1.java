package org.eclipse.egit.ui.internal.history.command;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.egit.ui.internal.CachedCheckboxTreeViewer;
import org.eclipse.egit.ui.internal.FilteredCheckboxTree;
import org.eclipse.egit.ui.internal.GitLabelProvider;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.PatternFilter;
import org.eclipse.ui.progress.WorkbenchJob;

public class BranchSelectionDialog<T> extends MessageDialog {

	private final List<T> nodes;

	private TableViewer branchesList;

	private FilteredCheckboxTree fTree;

	private List<T> selected = new ArrayList<T>();

	private final int style;

	private final boolean multiMode;

	BranchSelectionDialog(Shell parentShell, List<T> nodes, String title,
			String message, int style) {
		super(parentShell, title, null, message, MessageDialog.QUESTION,
				new String[] { IDialogConstants.OK_LABEL,
						IDialogConstants.CANCEL_LABEL }, 0);
		this.nodes = nodes;
		this.style = style;
		this.multiMode = (this.style & SWT.MULTI) > 0;
	}

	@Override
	protected Control createCustomArea(Composite parent) {
		Composite area = new Composite(parent, SWT.NONE);
		GridDataFactory.fillDefaults().grab(true, true).span(2, 1)
				.applyTo(area);
		area.setLayout(new GridLayout(1, false));
		if (multiMode) {
			fTree = new FilteredCheckboxTree(area, null, SWT.NONE,
					new PatternFilter()) {
				protected WorkbenchJob doCreateRefreshJob() {
					WorkbenchJob refreshJob = super.doCreateRefreshJob();
					refreshJob.addJobChangeListener(new JobChangeAdapter() {
						public void done(IJobChangeEvent event) {
							if (event.getResult().isOK()) {
								getDisplay().asyncExec(new Runnable() {
									public void run() {
										checkPage();
									}
								});
							}
						}
					});
					return refreshJob;
				}
			};

			CachedCheckboxTreeViewer viewer = fTree.getCheckboxTreeViewer();
			GridDataFactory.fillDefaults().grab(true, true).applyTo(fTree);
			viewer.setContentProvider(new ITreeContentProvider() {
				public void inputChanged(Viewer actViewer, Object oldInput,
						Object newInput) {
				}

				public void dispose() {
				}

				public boolean hasChildren(Object element) {
					return false;
				}

				public Object getParent(Object element) {
					return null;
				}

				public Object[] getElements(Object inputElement) {
					return ((List) inputElement).toArray();
				}

				public Object[] getChildren(Object parentElement) {
					return null;
				}
			});

			viewer.addCheckStateListener(new ICheckStateListener() {
				public void checkStateChanged(CheckStateChangedEvent event) {
					checkPage();
				}
			});

			viewer.setLabelProvider(new GitLabelProvider());
			viewer.setInput(nodes);
		} else {
			branchesList = new TableViewer(area, this.style | SWT.H_SCROLL
					| SWT.V_SCROLL | SWT.BORDER);
			GridDataFactory.fillDefaults().grab(true, true)
					.applyTo(branchesList.getControl());
			branchesList.setContentProvider(ArrayContentProvider.getInstance());
			branchesList.setLabelProvider(new GitLabelProvider());
			branchesList.setInput(nodes);
			branchesList
					.addSelectionChangedListener(new ISelectionChangedListener() {
						public void selectionChanged(SelectionChangedEvent event) {
							checkPage();
						}
					});
			branchesList.addDoubleClickListener(new IDoubleClickListener() {
				public void doubleClick(DoubleClickEvent event) {
					buttonPressed(OK);
				}
			});
		}
		return area;
	}

	private void checkPage() {
		if (multiMode)
			getButton(OK).setEnabled(
					fTree.getCheckboxTreeViewer().getCheckedLeafCount() > 0);
		else
			getButton(OK).setEnabled(!branchesList.getSelection().isEmpty());

	}

	@SuppressWarnings("unchecked")
	@Override
	protected void buttonPressed(int buttonId) {
		if (buttonId == OK) {
			if (multiMode) {
				selected.clear();
				Object[] checked = fTree.getCheckboxTreeViewer()
						.getCheckedElements();
				for (Object o : checked) {
					selected.add((T) o);
				}
			} else {
				selected = ((IStructuredSelection) branchesList.getSelection())
						.toList();
			}
		}
		super.buttonPressed(buttonId);
	}

	@Override
	public void create() {
		super.create();
		getButton(OK).setEnabled(false);
	}

	public T getSelectedNode() {
		if (selected.isEmpty())
			return null;
		return selected.get(0);
	}

	public List<T> getSelectedNodes() {
		return selected;
	}
}
