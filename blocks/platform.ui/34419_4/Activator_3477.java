package org.eclipse.ui.examples.undo.views;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.IOperationHistory;
import org.eclipse.core.commands.operations.IOperationHistoryListener;
import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.core.commands.operations.OperationHistoryEvent;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.eclipse.ui.examples.undo.UndoExampleMessages;
import org.eclipse.ui.examples.undo.UndoPlugin;
import org.eclipse.ui.examples.undo.preferences.PreferenceConstants;
import org.eclipse.ui.operations.RedoActionHandler;
import org.eclipse.ui.operations.UndoActionHandler;
import org.eclipse.ui.part.ViewPart;


public class UndoHistoryView extends ViewPart implements
		ISelectionChangedListener {
	private TableViewer viewer;

	private Action filterAction;

	private Action doubleClickAction;

	private Action selectiveUndoAction;

	private Action refreshListAction;

	private IOperationHistory history = OperationHistoryFactory
			.getOperationHistory();

	private IUndoContext fContext = IOperationHistory.GLOBAL_UNDO_CONTEXT;

	private UndoActionHandler undoAction;

	private RedoActionHandler redoAction;

	private boolean showDebug = UndoPlugin.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.PREF_SHOWDEBUG);
	private IPropertyChangeListener propertyChangeListener;


	class ViewContentProvider implements IStructuredContentProvider,
			IOperationHistoryListener {

		@Override
		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
			history.addOperationHistoryListener(this);
		}

		@Override
		public void dispose() {
			history.removeOperationHistoryListener(this);
		}

		@Override
		public Object[] getElements(Object input) {
			return history.getUndoHistory(fContext);
		}

		@Override
		public void historyNotification(OperationHistoryEvent event) {
			if (viewer.getTable().isDisposed()) {
				return;
			}
			Display display = viewer.getTable().getDisplay();
			switch (event.getEventType()) {
			case OperationHistoryEvent.OPERATION_ADDED:
			case OperationHistoryEvent.OPERATION_REMOVED:
			case OperationHistoryEvent.UNDONE:
			case OperationHistoryEvent.REDONE:
				if (event.getOperation().hasContext(fContext)
						&& display != null) {
					display.syncExec(new Runnable() {
						@Override
						public void run() {
							if (!viewer.getTable().isDisposed()) {
								viewer.refresh(true);
							}
						}
					});
				}
				break;
			}
		}
	}

	class ViewLabelProvider extends LabelProvider implements
			ITableLabelProvider {
		@Override
		public String getColumnText(Object obj, int index) {
			return getText(obj);
		}

		@Override
		public Image getColumnImage(Object obj, int index) {
			return getImage(obj);
		}

		@Override
		public String getText(Object obj) {
			if (!showDebug && obj instanceof IUndoableOperation) {
				return ((IUndoableOperation)obj).getLabel();
			}
			return obj.toString();
		}
	}

	@Override
	public void createPartControl(Composite parent) {
		viewer = new TableViewer(parent, SWT.SINGLE | SWT.H_SCROLL
				| SWT.V_SCROLL);
		viewer.setContentProvider(new ViewContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider());
		viewer.setInput(getViewSite());
		makeActions();
		hookContextMenu();
		hookDoubleClickAction();
		addListeners();
		createGlobalActionHandlers();
	}

	private void addListeners() {
		propertyChangeListener = new IPropertyChangeListener(){
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				if (event.getProperty() == PreferenceConstants.PREF_SHOWDEBUG) {
					showDebug = UndoPlugin.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.PREF_SHOWDEBUG);
					viewer.refresh();
				}
			}
		};
		UndoPlugin.getDefault().getPreferenceStore().addPropertyChangeListener(propertyChangeListener);
		viewer.getControl().addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent event) {
				removeListeners();
			}
		});
	}

	private void removeListeners() {
		UndoPlugin.getDefault().getPreferenceStore().removePropertyChangeListener(propertyChangeListener);
	}


	private void createGlobalActionHandlers() {
		undoAction = new UndoActionHandler(this.getSite(), fContext);
		redoAction = new RedoActionHandler(this.getSite(), fContext);
		IActionBars actionBars = getViewSite().getActionBars();
		actionBars.setGlobalActionHandler(ActionFactory.UNDO.getId(),
				undoAction);
		actionBars.setGlobalActionHandler(ActionFactory.REDO.getId(),
				redoAction);
	}

	private IUndoContext selectContext() {
		List<IUndoContext> input = new ArrayList<IUndoContext>();
		IUndoableOperation[] operations = history
				.getUndoHistory(IOperationHistory.GLOBAL_UNDO_CONTEXT);
		for (IUndoableOperation operation : operations) {
			IUndoContext[] contexts = operation.getContexts();
			for (int j = 0; j < contexts.length; j++) {
				if (!input.contains(contexts[j])) {
					input.add(contexts[j]);
				}
			}
		}
		input.add(IOperationHistory.GLOBAL_UNDO_CONTEXT);

		ILabelProvider labelProvider = new LabelProvider() {
			@Override
			public String getText(Object element) {
				return ((IUndoContext) element).getLabel();
			}
		};

		ElementListSelectionDialog dialog = new ElementListSelectionDialog(
				getSite().getShell(), labelProvider);
		dialog.setMultipleSelection(false);
		dialog.setTitle(UndoExampleMessages.UndoHistoryView_ContextFilterDialog);
		dialog.setMessage(UndoExampleMessages.UndoHistoryView_ChooseContextMessage);
		dialog.setElements(input.toArray());
		dialog.setInitialSelections(new Object[] { fContext });
		if (dialog.open() == Window.OK) {
			Object[] contexts = dialog.getResult();
			if (contexts[0] instanceof IUndoContext) {
				return (IUndoContext) contexts[0];
			}
			return null;
		}
		return null;
	}

	public void setContext(IUndoContext context) {
		fContext = context;
		redoAction.setContext(context);
		undoAction.setContext(context);
		viewer.refresh(false);
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			@Override
			public void menuAboutToShow(IMenuManager manager) {
				UndoHistoryView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	private void fillContextMenu(IMenuManager manager) {
		undoAction.update();
		redoAction.update();
		manager.add(undoAction);
		manager.add(redoAction);
		manager.add(new Separator());

		manager.add(selectiveUndoAction);
		manager.add(filterAction);
		manager.add(refreshListAction);

		ISelection selection = viewer.getSelection();
		if (!selection.isEmpty()) {
			IUndoableOperation operation = (IUndoableOperation) ((IStructuredSelection) selection)
					.getFirstElement();
			selectiveUndoAction.setEnabled(operation.canUndo());
		} else {
			selectiveUndoAction.setEnabled(false);
		}

		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}

	private void makeActions() {
		filterAction = new Action() {
			@Override
			public void run() {
				IUndoContext context = selectContext();
				if (fContext != context && context != null) {
					setContext(context);
				}
			}
		};
		filterAction.setText(UndoExampleMessages.UndoHistoryView_FilterText);
		filterAction.setToolTipText(UndoExampleMessages.UndoHistoryView_FilterToolTipText);
		filterAction.setImageDescriptor(PlatformUI.getWorkbench()
				.getSharedImages().getImageDescriptor(
						ISharedImages.IMG_OBJS_INFO_TSK));

		selectiveUndoAction = new Action() {
			@Override
			public void run() {
				ISelection selection = viewer.getSelection();
				IUndoableOperation operation = (IUndoableOperation) ((IStructuredSelection) selection)
						.getFirstElement();
				if (operation.canUndo()) {
					try {
						history.undoOperation(operation, null, undoAction);
					} catch (ExecutionException e) {
						showMessage(UndoExampleMessages.UndoHistoryView_OperationException);
					}
				} else {
					showMessage(UndoExampleMessages.UndoHistoryView_OperationInvalid);
				}
			}
		};
		selectiveUndoAction.setText(UndoExampleMessages.UndoHistoryView_UndoSelected);
		selectiveUndoAction.setToolTipText(UndoExampleMessages.UndoHistoryView_UndoSelectedToolTipText);
		selectiveUndoAction.setImageDescriptor(PlatformUI.getWorkbench()
				.getSharedImages().getImageDescriptor(
						ISharedImages.IMG_TOOL_UNDO));

		refreshListAction = new Action() {
			@Override
			public void run() {
				if (!viewer.getTable().isDisposed()) {
					viewer.refresh(true);
				}
			}
		};
		refreshListAction.setText(UndoExampleMessages.UndoHistoryView_RefreshList);
		refreshListAction.setToolTipText(UndoExampleMessages.UndoHistoryView_RefreshListToolTipText);

		doubleClickAction = new Action() {
			@Override
			public void run() {
				ISelection selection = viewer.getSelection();
				IUndoableOperation operation = (IUndoableOperation) ((IStructuredSelection) selection)
						.getFirstElement();
				StringBuffer buf = new StringBuffer(operation.getLabel());
				buf.append("\n");
				buf.append("Enabled=");	//$NON-NLS-1$
				buf.append(new Boolean(operation.canUndo()).toString());
				buf.append("\n");
				buf.append(operation.getClass().toString());
				showMessage(buf.toString());

			}
		};
	}

	private void hookDoubleClickAction() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {
				doubleClickAction.run();
			}
		});
	}

	private void showMessage(String message) {
		MessageDialog.openInformation(viewer.getControl().getShell(),
				UndoExampleMessages.UndoHistoryView_InfoDialogTitle, message);
	}

	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		ISelection selection = viewer.getSelection();
		boolean enabled = !selection.isEmpty();
		selectiveUndoAction.setEnabled(enabled);
	}

	@Override
	public void setFocus() {
		viewer.getControl().setFocus();

	}
}
