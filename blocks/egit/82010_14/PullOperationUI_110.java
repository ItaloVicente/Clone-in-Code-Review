package org.eclipse.egit.ui.internal.pull;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.egit.core.RepositoryUtil;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.internal.UIText;
import org.eclipse.egit.ui.internal.merge.MergeResultDialog;
import org.eclipse.egit.ui.internal.rebase.RebaseResultDialog;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.IOpenListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.RebaseResult;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

public class MultiPullResultDialog extends Dialog {
	private static final int DETAIL_BUTTON = 99;

	private final Map<Repository, Object> results = new LinkedHashMap<>();

	private TableViewer tv;

	private final RepositoryUtil utils = Activator.getDefault()
			.getRepositoryUtil();

	protected MultiPullResultDialog(Shell parentShell,
			Map<Repository, Object> results) {
		super(parentShell);
		setShellStyle(getShellStyle() & ~SWT.APPLICATION_MODAL | SWT.SHELL_TRIM);
		setBlockOnOpen(false);
		this.results.putAll(results);
	}

	@Override
	public void create() {
		super.create();
		getButton(DETAIL_BUTTON).setEnabled(false);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite main = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().applyTo(main);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(main);
		tv = new TableViewer(main, SWT.MULTI | SWT.FULL_SELECTION | SWT.BORDER);
		tv.setContentProvider(ArrayContentProvider.getInstance());
		TableColumnLayout layout = new TableColumnLayout();
		main.setLayout(layout);
		tv.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection sel = (IStructuredSelection) event
						.getSelection();
				boolean enabled = false;
				for (Object obj : sel.toList()) {
					@SuppressWarnings("unchecked")
					Entry<Repository, Object> entry = (Entry<Repository, Object>) obj;
					enabled |= entry.getValue() instanceof PullResult;
				}
				getButton(DETAIL_BUTTON).setEnabled(enabled);
			}
		});

		tv.addOpenListener(new IOpenListener() {
			@Override
			public void open(OpenEvent event) {
				buttonPressed(DETAIL_BUTTON);
			}
		});
		Table table = tv.getTable();
		int linesToShow = Math.min(Math.max(results.size(), 5), 15);
		int heightHint = table.getItemHeight() * linesToShow;
		GridDataFactory.fillDefaults().grab(true, true).hint(800, heightHint)
				.applyTo(table);
		TableViewerColumn tc = new TableViewerColumn(tv, SWT.NONE);
		TableColumn col = tc.getColumn();
		tc.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				@SuppressWarnings("unchecked")
				Entry<Repository, Object> item = (Entry<Repository, Object>) element;
				return utils.getRepositoryName(item.getKey());
			}
		});
		col.setText(UIText.MultiPullResultDialog_RepositoryColumnHeader);
		layout.setColumnData(col, new ColumnWeightData(200, 200));
		createComparator(col, 0);
		tc = new TableViewerColumn(tv, SWT.NONE);
		col = tc.getColumn();
		tc.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				@SuppressWarnings("unchecked")
				Entry<Repository, Object> item = (Entry<Repository, Object>) element;
				if (item.getValue() instanceof IStatus)
					return UIText.MultiPullResultDialog_UnknownStatus;
				PullResult pullRes = (PullResult) item.getValue();
				if (pullRes.getFetchResult() == null) {
					return UIText.MultiPullResultDialog_NothingFetchedStatus;
				} else if (pullRes.getFetchResult().getTrackingRefUpdates()
						.isEmpty()) {
					return UIText.MultiPullResultDialog_NothingUpdatedStatus;
				} else {
					int updated = pullRes.getFetchResult()
							.getTrackingRefUpdates().size();
					if (updated == 1) {
						return UIText.MultiPullResultDialog_UpdatedOneMessage;
					}
					return NLS.bind(UIText.MultiPullResultDialog_UpdatedMessage,
							Integer.valueOf(updated));
				}
			}
		});
		col.setText(UIText.MultiPullResultDialog_FetchStatusColumnHeader);
		layout.setColumnData(col, new ColumnWeightData(200, 200));
		createComparator(col, 1);
		tc = new TableViewerColumn(tv, SWT.NONE);
		col = tc.getColumn();
		tc.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				@SuppressWarnings("unchecked")
				Entry<Repository, Object> item = (Entry<Repository, Object>) element;
				if (item.getValue() instanceof IStatus) {
					return UIText.MultiPullResultDialog_UnknownStatus;
				}
				PullResult pullRes = (PullResult) item.getValue();
				if (pullRes.getMergeResult() != null) {
					return NLS.bind(
							UIText.MultiPullResultDialog_MergeResultMessage,
							MergeResultDialog.getStatusText(
									pullRes.getMergeResult().getMergeStatus()));
				} else if (pullRes.getRebaseResult() != null) {
					RebaseResult res = pullRes.getRebaseResult();
					return NLS.bind(
							UIText.MultiPullResultDialog_RebaseResultMessage,
							RebaseResultDialog.getStatusText(res.getStatus()));
				} else {
					return UIText.MultiPullResultDialog_NothingUpdatedStatus;
				}
			}
		});
		col.setText(UIText.MultiPullResultDialog_UpdateStatusColumnHeader);
		layout.setColumnData(col, new ColumnWeightData(200, 200));
		createComparator(col, 2);
		tc = new TableViewerColumn(tv, SWT.NONE);
		col = tc.getColumn();
		tc.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public Image getImage(Object element) {
				@SuppressWarnings("unchecked")
				Entry<Repository, Object> item = (Entry<Repository, Object>) element;
				Object resultOrError = item.getValue();
				if (resultOrError instanceof IStatus) {
					return PlatformUI.getWorkbench().getSharedImages()
							.getImage(ISharedImages.IMG_ELCL_STOP);
				}
				PullResult res = (PullResult) item.getValue();
				boolean success = res.isSuccessful();
				if (!success) {
					return PlatformUI.getWorkbench().getSharedImages()
							.getImage(ISharedImages.IMG_ELCL_STOP);
				}
				return null;
			}

			@Override
			public String getText(Object element) {
				@SuppressWarnings("unchecked")
				Entry<Repository, Object> item = (Entry<Repository, Object>) element;
				if (item.getValue() instanceof IStatus) {
					IStatus status = (IStatus) item.getValue();
					return status.getMessage();
				}
				PullResult res = (PullResult) item.getValue();
				if (res.isSuccessful()) {
					return UIText.MultiPullResultDialog_OkStatus;
				} else {
					return UIText.MultiPullResultDialog_FailedStatus;
				}
			}
		});
		col.setText(UIText.MultiPullResultDialog_OverallStatusColumnHeader);
		layout.setColumnData(col, new ColumnWeightData(200, 200));
		createComparator(col, 3);

		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		tv.setInput(results.entrySet());
		return main;
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, DETAIL_BUTTON,
				UIText.MultiPullResultDialog_DetailsButton, false);
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
	}

	@Override
	protected void buttonPressed(int buttonId) {
		if (buttonId == DETAIL_BUTTON) {
			final Shell shell = getShell();
			Rectangle trim = shell.computeTrim(0, 0, 0, 0);
			int xOffset = 0;
			int xDelta = -trim.x + 3;
			int yOffset = 0;
			int yDelta = -trim.y - 3;

			final LinkedList<PullResultDialog> dialogs= new LinkedList<>();
			IStructuredSelection sel = (IStructuredSelection) tv.getSelection();
			for (Object obj : sel.toList()) {
				@SuppressWarnings("unchecked")
				Entry<Repository, Object> item = (Entry<Repository, Object>) obj;
				if (item.getValue() instanceof PullResult) {
					final int x = xOffset;
					final int y = yOffset;
					xOffset += xDelta;
					yOffset += yDelta;

					final PullResultDialog dialog = new PullResultDialog(shell,
							item.getKey(), (PullResult) item.getValue()) {
						private Point initialLocation;

						@Override
						protected Point getInitialLocation(Point initialSize) {
							initialLocation = super
									.getInitialLocation(initialSize);
							initialLocation.x += x;
							initialLocation.y += y;
							return initialLocation;
						}

						@Override
						public boolean close() {
							Shell resultShell = getShell();
							if (resultShell != null
									&& !resultShell.isDisposed()) {
								Point location = resultShell.getLocation();
								if (location.equals(initialLocation)) {
									resultShell.setVisible(false);
									resultShell.setLocation(location.x - x,
											location.y - y);
								}
							}
							boolean result = super.close();



							dialogs.remove(this);
							if (dialogs.size() > 0)
								dialogs.getLast().getShell().setActive();

							return result;
						}
					};
					dialog.create();
					dialog.getShell().addShellListener(new ShellAdapter() {
						@Override
						public void shellActivated(org.eclipse.swt.events.ShellEvent e) {
							dialogs.remove(dialog);
							dialogs.add(dialog);
						}
					});
					dialog.open();
				}
			}
		}
		super.buttonPressed(buttonId);
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(UIText.MultiPullResultDialog_WindowTitle);
	}

	private ColumnComparator createComparator(TableColumn column,
			int columnIndex) {
		return new ColumnComparator(column, columnIndex);
	}

	private class ColumnComparator extends ViewerComparator {

		private static final int ASCENDING = SWT.DOWN;

		private static final int NONE = SWT.NONE;

		private static final int DESCENDING = SWT.UP;

		private final TableColumn column;

		private final int columnIndex;

		private int direction;

		public ColumnComparator(TableColumn column, int columnIndex) {
			super(null);
			this.column = column;
			this.columnIndex = columnIndex;
			column.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					if (tv.getComparator() == ColumnComparator.this) {
						if (direction == ASCENDING) {
							setDirection(DESCENDING);
						} else {
							setDirection(NONE);
						}
					} else {
						setDirection(ASCENDING);
					}
				}
			});
		}

		private void setDirection(int newDirection) {
			direction = newDirection;
			Table table = column.getParent();
			table.setSortDirection(direction);
			if (direction == NONE) {
				table.setSortColumn(null);
				tv.setComparator(null);
			} else {
				table.setSortColumn(column);
				if (tv.getComparator() == this) {
					tv.refresh();
				} else {
					tv.setComparator(this);
				}
			}
		}

		@Override
		public int compare(Viewer viewer, Object e1, Object e2) {
			ColumnLabelProvider labelProvider = (ColumnLabelProvider) tv
					.getLabelProvider(columnIndex);
			String label1 = labelProvider.getText(e1);
			String label2 = labelProvider.getText(e2);
			if (direction == ASCENDING) {
				return label1.compareTo(label2);
			} else {
				return label2.compareTo(label1);
			}
		}
	}
}
