package org.eclipse.ui.dialogs;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.ProgressMonitorWrapper;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.LegacyActionTools;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.ContentViewer;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IFontProvider;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ILazyContentProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.accessibility.ACC;
import org.eclipse.swt.accessibility.AccessibleAdapter;
import org.eclipse.swt.accessibility.AccessibleEvent;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.ActiveShellExpression;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IWorkbenchCommandConstants;
import org.eclipse.ui.IWorkbenchPreferenceConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.XMLMemento;
import org.eclipse.ui.handlers.IHandlerActivation;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.internal.IWorkbenchGraphicConstants;
import org.eclipse.ui.internal.WorkbenchImages;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.progress.UIJob;
import org.eclipse.ui.statushandlers.StatusManager;

public abstract class FilteredItemsSelectionDialog extends
		SelectionStatusDialog {

	private static final String DIALOG_BOUNDS_SETTINGS = "DialogBoundsSettings"; //$NON-NLS-1$

	private static final String SHOW_STATUS_LINE = "ShowStatusLine"; //$NON-NLS-1$

	private static final String HISTORY_SETTINGS = "History"; //$NON-NLS-1$

	private static final String DIALOG_HEIGHT = "DIALOG_HEIGHT"; //$NON-NLS-1$

	private static final String DIALOG_WIDTH = "DIALOG_WIDTH"; //$NON-NLS-1$

	public static final int NONE = 0;

	public static final int CARET_BEGINNING = 1;

	public static final int FULL_SELECTION = 2;

	private Text pattern;

	private TableViewer list;

	private DetailsContentViewer details;

	private ILabelProvider detailsLabelProvider;

	private ItemsListLabelProvider itemsListLabelProvider;

	private MenuManager menuManager;

	private MenuManager contextMenuManager;

	private boolean multi;

	private ToolBar toolBar;

	private ToolItem toolItem;

	private Label progressLabel;

	private ToggleStatusLineAction toggleStatusLineAction;

	private RemoveHistoryItemAction removeHistoryItemAction;

	private ActionContributionItem removeHistoryActionContributionItem;

	private IStatus status;

	private RefreshCacheJob refreshCacheJob;

	private RefreshProgressMessageJob refreshProgressMessageJob = new RefreshProgressMessageJob();

	private Object[] currentSelection;

	private ContentProvider contentProvider;

	private FilterHistoryJob filterHistoryJob;

	private FilterJob filterJob;

	private ItemsFilter filter;

	private List lastCompletedResult;

	private ItemsFilter lastCompletedFilter;

	private String initialPatternText;

	private int selectionMode;

	private ItemsListSeparator itemsListSeparator;

	private static final String EMPTY_STRING = ""; //$NON-NLS-1$

	private boolean refreshWithLastSelection = false;

	private IHandlerActivation showViewHandler;

	public FilteredItemsSelectionDialog(Shell shell, boolean multi) {
		super(shell);
		this.multi = multi;
		filterHistoryJob = new FilterHistoryJob();
		filterJob = new FilterJob();
		contentProvider = new ContentProvider();
		refreshCacheJob = new RefreshCacheJob();
		itemsListSeparator = new ItemsListSeparator(
				WorkbenchMessages.FilteredItemsSelectionDialog_separatorLabel);
		selectionMode = NONE;
	}

	public FilteredItemsSelectionDialog(Shell shell) {
		this(shell, false);
	}

	protected void addListFilter(ViewerFilter filter) {
		contentProvider.addFilter(filter);
	}

	public void setListLabelProvider(ILabelProvider listLabelProvider) {
		getItemsListLabelProvider().setProvider(listLabelProvider);
	}

	private ILabelDecorator getListSelectionLabelDecorator() {
		return getItemsListLabelProvider().getSelectionDecorator();
	}

	public void setListSelectionLabelDecorator(
			ILabelDecorator listSelectionLabelDecorator) {
		getItemsListLabelProvider().setSelectionDecorator(
				listSelectionLabelDecorator);
	}

	private ItemsListLabelProvider getItemsListLabelProvider() {
		if (itemsListLabelProvider == null) {
			itemsListLabelProvider = new ItemsListLabelProvider(
					new LabelProvider(), null);
		}
		return itemsListLabelProvider;
	}

	public void setDetailsLabelProvider(ILabelProvider detailsLabelProvider) {
		this.detailsLabelProvider = detailsLabelProvider;
		if (details != null) {
			details.setLabelProvider(detailsLabelProvider);
		}
	}

	private ILabelProvider getDetailsLabelProvider() {
		if (detailsLabelProvider == null) {
			detailsLabelProvider = new LabelProvider();
		}
		return detailsLabelProvider;
	}

	@Override
	public void create() {
		super.create();
		pattern.setFocus();
	}

	protected void restoreDialog(IDialogSettings settings) {
		boolean toggleStatusLine = true;

		if (settings.get(SHOW_STATUS_LINE) != null) {
			toggleStatusLine = settings.getBoolean(SHOW_STATUS_LINE);
		}

		toggleStatusLineAction.setChecked(toggleStatusLine);

		details.setVisible(toggleStatusLine);

		String setting = settings.get(HISTORY_SETTINGS);
		if (setting != null) {
			try {
				IMemento memento = XMLMemento.createReadRoot(new StringReader(
						setting));
				this.contentProvider.loadHistory(memento);
			} catch (WorkbenchException e) {
				StatusManager
						.getManager()
						.handle(
								new Status(
										IStatus.ERROR,
										PlatformUI.PLUGIN_ID,
										IStatus.ERROR,
										WorkbenchMessages.FilteredItemsSelectionDialog_restoreError,
										e));
			}
		}
	}

	@Override
	public boolean close() {
		this.filterJob.cancel();
		this.refreshCacheJob.cancel();
		this.refreshProgressMessageJob.cancel();
		if (showViewHandler != null) {
			IHandlerService service = PlatformUI
					.getWorkbench().getService(IHandlerService.class);
			service.deactivateHandler(showViewHandler);
			showViewHandler.getHandler().dispose();
			showViewHandler = null;
		}
		if (menuManager != null)
			menuManager.dispose();
		if (contextMenuManager != null)
			contextMenuManager.dispose();
		storeDialog(getDialogSettings());
		return super.close();
	}

	protected void storeDialog(IDialogSettings settings) {
		settings.put(SHOW_STATUS_LINE, toggleStatusLineAction.isChecked());

		XMLMemento memento = XMLMemento.createWriteRoot(HISTORY_SETTINGS);
		this.contentProvider.saveHistory(memento);
		StringWriter writer = new StringWriter();
		try {
			memento.save(writer);
			settings.put(HISTORY_SETTINGS, writer.getBuffer().toString());
		} catch (IOException e) {
			StatusManager
					.getManager()
					.handle(
							new Status(
									IStatus.ERROR,
									PlatformUI.PLUGIN_ID,
									IStatus.ERROR,
									WorkbenchMessages.FilteredItemsSelectionDialog_storeError,
									e));
		}
	}

	private Label createHeader(Composite parent) {
		Composite header = new Composite(parent, SWT.NONE);

		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		header.setLayout(layout);

		Label headerLabel = new Label(header, SWT.NONE);
		headerLabel.setText((getMessage() != null && getMessage().trim()
				.length() > 0) ? getMessage()
				: WorkbenchMessages.FilteredItemsSelectionDialog_patternLabel);
		headerLabel.addTraverseListener(new TraverseListener() {
			@Override
			public void keyTraversed(TraverseEvent e) {
				if (e.detail == SWT.TRAVERSE_MNEMONIC && e.doit) {
					e.detail = SWT.TRAVERSE_NONE;
					pattern.setFocus();
				}
			}
		});

		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		headerLabel.setLayoutData(gd);

		createViewMenu(header);
		header.setLayoutData(gd);
		return headerLabel;
	}

	private Label createLabels(Composite parent) {
		Composite labels = new Composite(parent, SWT.NONE);

		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		labels.setLayout(layout);

		Label listLabel = new Label(labels, SWT.NONE);
		listLabel
				.setText(WorkbenchMessages.FilteredItemsSelectionDialog_listLabel);

		listLabel.addTraverseListener(new TraverseListener() {
			@Override
			public void keyTraversed(TraverseEvent e) {
				if (e.detail == SWT.TRAVERSE_MNEMONIC && e.doit) {
					e.detail = SWT.TRAVERSE_NONE;
					list.getTable().setFocus();
				}
			}
		});

		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		listLabel.setLayoutData(gd);

		progressLabel = new Label(labels, SWT.RIGHT);
		progressLabel.setLayoutData(gd);

		labels.setLayoutData(gd);
		return listLabel;
	}

	private void createViewMenu(Composite parent) {
		toolBar = new ToolBar(parent, SWT.FLAT);
		toolItem = new ToolItem(toolBar, SWT.PUSH, 0);

		GridData data = new GridData();
		data.horizontalAlignment = GridData.END;
		toolBar.setLayoutData(data);

		toolBar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				showViewMenu();
			}
		});

		toolItem.setImage(WorkbenchImages
				.getImage(IWorkbenchGraphicConstants.IMG_LCL_VIEW_MENU));
		toolItem
				.setToolTipText(WorkbenchMessages.FilteredItemsSelectionDialog_menu);
		toolItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				showViewMenu();
			}
		});

		menuManager = new MenuManager();

		fillViewMenu(menuManager);

		IHandlerService service = PlatformUI.getWorkbench()
				.getService(IHandlerService.class);
		IHandler handler = new AbstractHandler() {
			@Override
			public Object execute(ExecutionEvent event) {
				showViewMenu();
				return null;
			}
		};
		showViewHandler = service.activateHandler(
				IWorkbenchCommandConstants.WINDOW_SHOW_VIEW_MENU, handler,
				new ActiveShellExpression(getShell()));
	}

	protected void fillViewMenu(IMenuManager menuManager) {
		toggleStatusLineAction = new ToggleStatusLineAction();
		menuManager.add(toggleStatusLineAction);
	}

	private void showViewMenu() {
		Menu menu = menuManager.createContextMenu(getShell());
		Rectangle bounds = toolItem.getBounds();
		Point topLeft = new Point(bounds.x, bounds.y + bounds.height);
		topLeft = toolBar.toDisplay(topLeft);
		menu.setLocation(topLeft.x, topLeft.y);
		menu.setVisible(true);
	}

	protected void fillContextMenu(IMenuManager menuManager) {
		List selectedElements= ((StructuredSelection)list.getSelection()).toList();

		Object item= null;

		for (Iterator it= selectedElements.iterator(); it.hasNext();) {
			item= it.next();
			if (item instanceof ItemsListSeparator || !isHistoryElement(item)) {
				return;
			}
		}

		if (selectedElements.size() > 0) {
			removeHistoryItemAction.setText(WorkbenchMessages.FilteredItemsSelectionDialog_removeItemsFromHistoryAction);

			menuManager.add(removeHistoryActionContributionItem);

		}
	}

	private void createPopupMenu() {
		removeHistoryItemAction = new RemoveHistoryItemAction();
		removeHistoryActionContributionItem = new ActionContributionItem(
				removeHistoryItemAction);

		contextMenuManager = new MenuManager();
		contextMenuManager.setRemoveAllWhenShown(true);
		contextMenuManager.addMenuListener(new IMenuListener() {
			@Override
			public void menuAboutToShow(IMenuManager manager) {
				fillContextMenu(manager);
			}
		});

		final Table table = list.getTable();
		Menu menu= contextMenuManager.createContextMenu(table);
		table.setMenu(menu);
	}

	protected abstract Control createExtendedContentArea(Composite parent);

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite dialogArea = (Composite) super.createDialogArea(parent);

		Composite content = new Composite(dialogArea, SWT.NONE);
		GridData gd = new GridData(GridData.FILL_BOTH);
		content.setLayoutData(gd);

		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		content.setLayout(layout);

		final Label headerLabel = createHeader(content);

		pattern = new Text(content, SWT.SINGLE | SWT.BORDER | SWT.SEARCH | SWT.ICON_CANCEL);
		pattern.getAccessible().addAccessibleListener(new AccessibleAdapter() {
			@Override
			public void getName(AccessibleEvent e) {
				e.result = LegacyActionTools.removeMnemonics(headerLabel
						.getText());
			}
		});
		gd = new GridData(GridData.FILL_HORIZONTAL);
		pattern.setLayoutData(gd);

		final Label listLabel = createLabels(content);

		list = new TableViewer(content, (multi ? SWT.MULTI : SWT.SINGLE)
				| SWT.BORDER | SWT.V_SCROLL | SWT.VIRTUAL);
		list.getTable().getAccessible().addAccessibleListener(
				new AccessibleAdapter() {
					@Override
					public void getName(AccessibleEvent e) {
						if (e.childID == ACC.CHILDID_SELF) {
							e.result = LegacyActionTools
									.removeMnemonics(listLabel.getText());
						}
					}
				});
		list.setContentProvider(contentProvider);
		list.setLabelProvider(getItemsListLabelProvider());
		list.setInput(new Object[0]);
		list.setItemCount(contentProvider.getNumberOfElements());
		gd = new GridData(GridData.FILL_BOTH);
		applyDialogFont(list.getTable());
		gd.heightHint= list.getTable().getItemHeight() * 15;
		list.getTable().setLayoutData(gd);

		createPopupMenu();

		pattern.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				applyFilter();
			}
		});

		pattern.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.keyCode == SWT.ARROW_DOWN) {
					if (list.getTable().getItemCount() > 0) {
						list.getTable().setFocus();
					}
				}
			}
		});

		list.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				StructuredSelection selection = (StructuredSelection) event
						.getSelection();
				handleSelected(selection);
			}
		});

		list.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {
				handleDoubleClick();
			}
		});

		list.getTable().addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {

				if (e.keyCode == SWT.DEL) {

					List selectedElements = ((StructuredSelection) list
							.getSelection()).toList();

					Object item = null;
					boolean isSelectedHistory = true;

					for (Iterator it = selectedElements.iterator(); it
							.hasNext();) {
						item = it.next();
						if (item instanceof ItemsListSeparator
								|| !isHistoryElement(item)) {
							isSelectedHistory = false;
							break;
						}
					}
					if (isSelectedHistory)
						removeSelectedItems(selectedElements);

				}

				if (e.keyCode == SWT.ARROW_UP && (e.stateMask & SWT.SHIFT) != 0
						&& (e.stateMask & SWT.CTRL) != 0) {
					StructuredSelection selection = (StructuredSelection) list
							.getSelection();

					if (selection.size() == 1) {
						Object element = selection.getFirstElement();
						if (element.equals(list.getElementAt(0))) {
							pattern.setFocus();
						}
						if (list.getElementAt(list.getTable()
								.getSelectionIndex() - 1) instanceof ItemsListSeparator)
							list.getTable().setSelection(
									list.getTable().getSelectionIndex() - 1);
						list.getTable().notifyListeners(SWT.Selection,
								new Event());

					}
				}

				if (e.keyCode == SWT.ARROW_DOWN
						&& (e.stateMask & SWT.SHIFT) != 0
						&& (e.stateMask & SWT.CTRL) != 0) {

					if (list
							.getElementAt(list.getTable().getSelectionIndex() + 1) instanceof ItemsListSeparator)
						list.getTable().setSelection(
								list.getTable().getSelectionIndex() + 1);
					list.getTable().notifyListeners(SWT.Selection, new Event());
				}

			}
		});

		createExtendedContentArea(content);

		details = new DetailsContentViewer(content, SWT.BORDER | SWT.FLAT);
		details.setVisible(toggleStatusLineAction.isChecked());
		details.setContentProvider(new NullContentProvider());
		details.setLabelProvider(getDetailsLabelProvider());

		applyDialogFont(content);

		restoreDialog(getDialogSettings());

		if (initialPatternText != null) {
			pattern.setText(initialPatternText);
		}

		switch (selectionMode) {
		case CARET_BEGINNING:
			pattern.setSelection(0, 0);
			break;
		case FULL_SELECTION:
			pattern.setSelection(0, initialPatternText.length());
			break;
		}

		applyFilter();

		return dialogArea;
	}

	protected void handleDoubleClick() {
		okPressed();
	}

	private void refreshDetails() {
		StructuredSelection selection = getSelectedItems();

		switch (selection.size()) {
		case 0:
			details.setInput(null);
			break;
		case 1:
			details.setInput(selection.getFirstElement());
			break;
		default:
			details
					.setInput(NLS
							.bind(
									WorkbenchMessages.FilteredItemsSelectionDialog_nItemsSelected,
									new Integer(selection.size())));
			break;
		}

	}

	protected void handleSelected(StructuredSelection selection) {
		IStatus status = new Status(IStatus.OK, PlatformUI.PLUGIN_ID,
				IStatus.OK, EMPTY_STRING, null);

		Object[] lastSelection = currentSelection;

		currentSelection = selection.toArray();

		if (selection.size() == 0) {
			status = new Status(IStatus.ERROR, PlatformUI.PLUGIN_ID,
					IStatus.ERROR, EMPTY_STRING, null);

			if (lastSelection != null
					&& getListSelectionLabelDecorator() != null) {
				list.update(lastSelection, null);
			}

			currentSelection = null;

		} else {
			status = new Status(IStatus.ERROR, PlatformUI.PLUGIN_ID,
					IStatus.ERROR, EMPTY_STRING, null);

			List items = selection.toList();

			Object item = null;
			IStatus tempStatus = null;

			for (Iterator it = items.iterator(); it.hasNext();) {
				Object o = it.next();

				if (o instanceof ItemsListSeparator) {
					continue;
				}

				item = o;
				tempStatus = validateItem(item);

				if (tempStatus.isOK()) {
					status = new Status(IStatus.OK, PlatformUI.PLUGIN_ID,
							IStatus.OK, EMPTY_STRING, null);
				} else {
					status = tempStatus;
					break;
				}
			}

			if (lastSelection != null
					&& getListSelectionLabelDecorator() != null) {
				list.update(lastSelection, null);
			}

			if (getListSelectionLabelDecorator() != null) {
				list.update(currentSelection, null);
			}
		}

		refreshDetails();
		updateStatus(status);
	}

	@Override
	protected IDialogSettings getDialogBoundsSettings() {
		IDialogSettings settings = getDialogSettings();
		IDialogSettings section = settings.getSection(DIALOG_BOUNDS_SETTINGS);
		if (section == null) {
			section = settings.addNewSection(DIALOG_BOUNDS_SETTINGS);
			section.put(DIALOG_HEIGHT, 500);
			section.put(DIALOG_WIDTH, 600);
		}
		return section;
	}

	protected abstract IDialogSettings getDialogSettings();

	public void refresh() {
		if (list != null && !list.getTable().isDisposed()) {

			List lastRefreshSelection = ((StructuredSelection) list
					.getSelection()).toList();
			list.getTable().deselectAll();

			list.setItemCount(contentProvider.getNumberOfElements());
			list.refresh();

			if (list.getTable().getItemCount() > 0) {
				if (refreshWithLastSelection && lastRefreshSelection != null
						&& lastRefreshSelection.size() > 0) {
					list.setSelection(new StructuredSelection(
							lastRefreshSelection));
				} else {
					refreshWithLastSelection = true;
					list.getTable().setSelection(0);
					list.getTable().notifyListeners(SWT.Selection, new Event());
				}
			} else {
				list.setSelection(StructuredSelection.EMPTY);
			}

		}

		scheduleProgressMessageRefresh();
	}

	@Deprecated
	public void updateProgressLabel() {
		scheduleProgressMessageRefresh();
	}

	public void reloadCache(boolean checkDuplicates, IProgressMonitor monitor) {
		if (list != null && !list.getTable().isDisposed()
				&& contentProvider != null) {
			contentProvider.reloadCache(checkDuplicates, monitor);
		}
	}

	public void scheduleRefresh() {
		refreshCacheJob.cancelAll();
		refreshCacheJob.schedule();
	}

	public void scheduleProgressMessageRefresh() {
		if (filterJob.getState() != Job.RUNNING
				&& refreshProgressMessageJob.getState() != Job.RUNNING)
			refreshProgressMessageJob.scheduleProgressRefresh(null);
	}

	@Override
	protected void computeResult() {

		List selectedElements = ((StructuredSelection) list.getSelection())
				.toList();

		List objectsToReturn = new ArrayList();

		Object item = null;

		for (Iterator it = selectedElements.iterator(); it.hasNext();) {
			item = it.next();

			if (!(item instanceof ItemsListSeparator)) {
				accessedHistoryItem(item);
				objectsToReturn.add(item);
			}
		}

		setResult(objectsToReturn);
	}

	@Override
	protected void updateStatus(IStatus status) {
		this.status = status;
		super.updateStatus(status);
	}

	@Override
	protected void okPressed() {
		if (status != null
				&& (status.isOK() || status.getCode() == IStatus.INFO)) {
			super.okPressed();
		}
	}

	public void setInitialPattern(String text) {
		setInitialPattern(text, FULL_SELECTION);
	}

	public void setInitialPattern(String text, int selectionMode) {
		this.initialPatternText = text;
		this.selectionMode = selectionMode;
	}

	protected String getInitialPattern() {
		return this.initialPatternText;
	}

	protected StructuredSelection getSelectedItems() {

		StructuredSelection selection = (StructuredSelection) list
				.getSelection();

		List selectedItems = selection.toList();
		Object itemToRemove = null;

		for (Iterator it = selection.iterator(); it.hasNext();) {
			Object item = it.next();
			if (item instanceof ItemsListSeparator) {
				itemToRemove = item;
				break;
			}
		}

		if (itemToRemove == null)
			return new StructuredSelection(selectedItems);
		List newItems = new ArrayList(selectedItems);
		newItems.remove(itemToRemove);
		return new StructuredSelection(newItems);

	}

	protected abstract IStatus validateItem(Object item);

	protected abstract ItemsFilter createFilter();

	protected void applyFilter() {

		ItemsFilter newFilter = createFilter();

		if (filter != null && filter.equalsFilter(newFilter)) {
			return;
		}

		filterHistoryJob.cancel();
		filterJob.cancel();

		this.filter = newFilter;

		if (this.filter != null) {
			filterHistoryJob.schedule();
		}
	}

	protected abstract Comparator getItemsComparator();

	protected abstract void fillContentProvider(
			AbstractContentProvider contentProvider, ItemsFilter itemsFilter,
			IProgressMonitor progressMonitor) throws CoreException;

	private void removeSelectedItems(List items) {
		for (Iterator iter = items.iterator(); iter.hasNext();) {
			Object item = iter.next();
			removeHistoryItem(item);
		}
		refreshWithLastSelection = false;
		contentProvider.refresh();
	}

	protected Object removeHistoryItem(Object item) {
		return contentProvider.removeHistoryElement(item);
	}

	protected void accessedHistoryItem(Object item) {
		contentProvider.addHistoryElement(item);
	}

	private Comparator getHistoryComparator() {
		return new HistoryComparator();
	}

	protected SelectionHistory getSelectionHistory() {
		return this.contentProvider.getSelectionHistory();
	}

	protected void setSelectionHistory(SelectionHistory selectionHistory) {
		if (this.contentProvider != null)
			this.contentProvider.setSelectionHistory(selectionHistory);
	}

	public boolean isHistoryElement(Object item) {
		return this.contentProvider.isHistoryElement(item);
	}

	public boolean isDuplicateElement(Object item) {
		return this.contentProvider.isDuplicateElement(item);
	}

	public void setSeparatorLabel(String separatorLabel) {
		this.itemsListSeparator = new ItemsListSeparator(separatorLabel);
	}

	public abstract String getElementName(Object item);

	private class ToggleStatusLineAction extends Action {

		public ToggleStatusLineAction() {
			super(
					WorkbenchMessages.FilteredItemsSelectionDialog_toggleStatusAction,
					IAction.AS_CHECK_BOX);
		}

		@Override
		public void run() {
			details.setVisible(isChecked());
		}
	}

	private class RefreshJob extends UIJob {

		public RefreshJob() {
			super(FilteredItemsSelectionDialog.this.getParentShell()
					.getDisplay(),
					WorkbenchMessages.FilteredItemsSelectionDialog_refreshJob);
			setSystem(true);
		}

		@Override
		public IStatus runInUIThread(IProgressMonitor monitor) {
			if (monitor.isCanceled())
				return new Status(IStatus.OK, WorkbenchPlugin.PI_WORKBENCH,
						IStatus.OK, EMPTY_STRING, null);

			if (FilteredItemsSelectionDialog.this != null) {
				FilteredItemsSelectionDialog.this.refresh();
			}

			return new Status(IStatus.OK, PlatformUI.PLUGIN_ID, IStatus.OK,
					EMPTY_STRING, null);
		}

	}

	private class RefreshProgressMessageJob extends UIJob {

		private GranualProgressMonitor progressMonitor;

		public RefreshProgressMessageJob() {
			super(
					FilteredItemsSelectionDialog.this.getParentShell()
							.getDisplay(),
					WorkbenchMessages.FilteredItemsSelectionDialog_progressRefreshJob);
			setSystem(true);
		}

		@Override
		public IStatus runInUIThread(IProgressMonitor monitor) {

			if (!progressLabel.isDisposed())
				progressLabel.setText(progressMonitor != null ? progressMonitor
						.getMessage() : EMPTY_STRING);

			if (progressMonitor == null || progressMonitor.isDone()) {
				return new Status(IStatus.CANCEL, PlatformUI.PLUGIN_ID,
						IStatus.CANCEL, EMPTY_STRING, null);
			}

			schedule(500);

			return new Status(IStatus.OK, PlatformUI.PLUGIN_ID, IStatus.OK,
					EMPTY_STRING, null);
		}

		public void scheduleProgressRefresh(
				GranualProgressMonitor progressMonitor) {
			this.progressMonitor = progressMonitor;
			schedule(200);
		}

	}

	private class RefreshCacheJob extends Job {

		private RefreshJob refreshJob = new RefreshJob();

		public RefreshCacheJob() {
			super(
					WorkbenchMessages.FilteredItemsSelectionDialog_cacheRefreshJob);
			setSystem(true);
		}

		public void cancelAll() {
			cancel();
			refreshJob.cancel();
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {
			if (monitor.isCanceled()) {
				return new Status(IStatus.CANCEL, WorkbenchPlugin.PI_WORKBENCH,
						IStatus.CANCEL, EMPTY_STRING, null);
			}

			if (FilteredItemsSelectionDialog.this != null) {
				GranualProgressMonitor wrappedMonitor = new GranualProgressMonitor(
						monitor);
				FilteredItemsSelectionDialog.this.reloadCache(true,
						wrappedMonitor);
			}

			if (!monitor.isCanceled()) {
				refreshJob.schedule();
			}

			return new Status(IStatus.OK, PlatformUI.PLUGIN_ID, IStatus.OK,
					EMPTY_STRING, null);

		}

		@Override
		protected void canceling() {
			super.canceling();
			contentProvider.stopReloadingCache();
		}

	}

	private class RemoveHistoryItemAction extends Action {

		public RemoveHistoryItemAction() {
			super(
					WorkbenchMessages.FilteredItemsSelectionDialog_removeItemsFromHistoryAction);
		}

		@Override
		public void run() {
			List selectedElements = ((StructuredSelection) list.getSelection())
					.toList();
			removeSelectedItems(selectedElements);
		}
	}

	private static boolean showColoredLabels() {
		return PlatformUI.getPreferenceStore().getBoolean(IWorkbenchPreferenceConstants.USE_COLORED_LABELS);
	}

	private class ItemsListLabelProvider extends StyledCellLabelProvider
			implements ILabelProviderListener {
		private ILabelProvider provider;

		private ILabelDecorator selectionDecorator;

		private ListenerList listeners = new ListenerList();

		public ItemsListLabelProvider(ILabelProvider provider,
				ILabelDecorator selectionDecorator) {
			Assert.isNotNull(provider);
			this.provider = provider;
			this.selectionDecorator = selectionDecorator;

			setOwnerDrawEnabled(showColoredLabels() && provider instanceof IStyledLabelProvider);

			provider.addListener(this);

			if (selectionDecorator != null) {
				selectionDecorator.addListener(this);
			}
		}

		public void setSelectionDecorator(ILabelDecorator newSelectionDecorator) {
			if (selectionDecorator != null) {
				selectionDecorator.removeListener(this);
				selectionDecorator.dispose();
			}

			selectionDecorator = newSelectionDecorator;

			if (selectionDecorator != null) {
				selectionDecorator.addListener(this);
			}
		}

		public ILabelDecorator getSelectionDecorator() {
			return selectionDecorator;
		}

		public void setProvider(ILabelProvider newProvider) {
			Assert.isNotNull(newProvider);
			provider.removeListener(this);
			provider.dispose();

			provider = newProvider;

			if (provider != null) {
				provider.addListener(this);
			}

			setOwnerDrawEnabled(showColoredLabels() && provider instanceof IStyledLabelProvider);
		}

		private Image getImage(Object element) {
			if (element instanceof ItemsListSeparator) {
				return WorkbenchImages
						.getImage(IWorkbenchGraphicConstants.IMG_OBJ_SEPARATOR);
			}

			return provider.getImage(element);
		}

		private boolean isSelected(Object element) {
			if (element != null && currentSelection != null) {
				for (int i = 0; i < currentSelection.length; i++) {
					if (element.equals(currentSelection[i]))
						return true;
				}
			}
			return false;
		}

		private String getText(Object element) {
			if (element instanceof ItemsListSeparator) {
				return getSeparatorLabel(((ItemsListSeparator) element)
						.getName());
			}

			String str = provider.getText(element);
			if (selectionDecorator != null && isSelected(element)) {
				return selectionDecorator.decorateText(str.toString(), element);
			}

			return str;
		}

		private StyledString getStyledText(Object element,
				IStyledLabelProvider provider) {
			StyledString string = provider.getStyledText(element);

			if (selectionDecorator != null && isSelected(element)) {
				String decorated = selectionDecorator.decorateText(string
						.getString(), element);
				return StyledCellLabelProvider.styleDecoratedString(decorated, null, string);
			}
			return string;
		}

		@Override
		public void update(ViewerCell cell) {
			Object element = cell.getElement();

			if (!(element instanceof ItemsListSeparator)
					&& provider instanceof IStyledLabelProvider) {
				IStyledLabelProvider styledLabelProvider = (IStyledLabelProvider) provider;
				StyledString styledString = getStyledText(element,
						styledLabelProvider);

				cell.setText(styledString.getString());
				cell.setStyleRanges(styledString.getStyleRanges());
				cell.setImage(styledLabelProvider.getImage(element));
			} else {
				cell.setText(getText(element));
				cell.setImage(getImage(element));
			}
			cell.setFont(getFont(element));
			cell.setForeground(getForeground(element));
			cell.setBackground(getBackground(element));

			super.update(cell);
		}

		private String getSeparatorLabel(String separatorLabel) {
			Rectangle rect = list.getTable().getBounds();

			int borderWidth = list.getTable().computeTrim(0, 0, 0, 0).width;

			int imageWidth = WorkbenchImages.getImage(
					IWorkbenchGraphicConstants.IMG_OBJ_SEPARATOR).getBounds().width;

			int width = rect.width - borderWidth - imageWidth;

			GC gc = new GC(list.getTable());
			gc.setFont(list.getTable().getFont());

			int fSeparatorWidth = gc.getAdvanceWidth('-');
			int fMessageLength = gc.textExtent(separatorLabel).x;

			gc.dispose();

			StringBuffer dashes = new StringBuffer();
			int chars = (((width - fMessageLength) / fSeparatorWidth) / 2) - 2;
			for (int i = 0; i < chars; i++) {
				dashes.append('-');
			}

			StringBuffer result = new StringBuffer();
			result.append(dashes);
			result.append(" " + separatorLabel + " "); //$NON-NLS-1$//$NON-NLS-2$
			result.append(dashes);
			return result.toString().trim();
		}

		@Override
		public void addListener(ILabelProviderListener listener) {
			listeners.add(listener);
		}

		@Override
		public void dispose() {
			provider.removeListener(this);
			provider.dispose();

			if (selectionDecorator != null) {
				selectionDecorator.removeListener(this);
				selectionDecorator.dispose();
			}

			super.dispose();
		}

		@Override
		public boolean isLabelProperty(Object element, String property) {
			if (provider.isLabelProperty(element, property)) {
				return true;
			}
			if (selectionDecorator != null
					&& selectionDecorator.isLabelProperty(element, property)) {
				return true;
			}
			return false;
		}

		@Override
		public void removeListener(ILabelProviderListener listener) {
			listeners.remove(listener);
		}

		private Color getBackground(Object element) {
			if (element instanceof ItemsListSeparator) {
				return null;
			}
			if (provider instanceof IColorProvider) {
				return ((IColorProvider) provider).getBackground(element);
			}
			return null;
		}

		private Color getForeground(Object element) {
			if (element instanceof ItemsListSeparator) {
				return Display.getCurrent().getSystemColor(
						SWT.COLOR_WIDGET_NORMAL_SHADOW);
			}
			if (provider instanceof IColorProvider) {
				return ((IColorProvider) provider).getForeground(element);
			}
			return null;
		}

		private Font getFont(Object element) {
			if (element instanceof ItemsListSeparator) {
				return null;
			}
			if (provider instanceof IFontProvider) {
				return ((IFontProvider) provider).getFont(element);
			}
			return null;
		}

		@Override
		public void labelProviderChanged(LabelProviderChangedEvent event) {
			Object[] l = listeners.getListeners();
			for (int i = 0; i < listeners.size(); i++) {
				((ILabelProviderListener) l[i]).labelProviderChanged(event);
			}
		}
	}

	private class ItemsListSeparator {

		private String name;

		public ItemsListSeparator(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

	private class GranualProgressMonitor extends ProgressMonitorWrapper {

		private String name;

		private String subName;

		private int totalWork;

		private double worked;

		private boolean done;

		public GranualProgressMonitor(IProgressMonitor monitor) {
			super(monitor);
		}

		public boolean isDone() {
			return done;
		}

		@Override
		public void setTaskName(String name) {
			super.setTaskName(name);
			this.name = name;
			this.subName = null;
		}

		@Override
		public void subTask(String name) {
			super.subTask(name);
			this.subName = name;
		}

		@Override
		public void beginTask(String name, int totalWork) {
			super.beginTask(name, totalWork);
			if (this.name == null)
				this.name = name;
			this.totalWork = totalWork;
			refreshProgressMessageJob.scheduleProgressRefresh(this);
		}

		@Override
		public void worked(int work) {
			super.worked(work);
			internalWorked(work);
		}

		@Override
		public void done() {
			done = true;
			super.done();
		}

		@Override
		public void setCanceled(boolean b) {
			done = b;
			super.setCanceled(b);
		}

		@Override
		public void internalWorked(double work) {
			worked = worked + work;
		}

		private String getMessage() {
			if (done)
				return ""; //$NON-NLS-1$

			String message;

			if (name == null) {
				message = subName == null ? "" : subName; //$NON-NLS-1$
			} else {
				message = subName == null ? name
						: NLS
								.bind(
										WorkbenchMessages.FilteredItemsSelectionDialog_subtaskProgressMessage,
										new Object[] { name, subName });
			}
			if (totalWork == 0)
				return message;

			return NLS
					.bind(
							WorkbenchMessages.FilteredItemsSelectionDialog_taskProgressMessage,
							new Object[] {
									message,
									new Integer(
											(int) ((worked * 100) / totalWork)) });

		}

	}

	private class FilterHistoryJob extends Job {

		private ItemsFilter itemsFilter;

		public FilterHistoryJob() {
			super(WorkbenchMessages.FilteredItemsSelectionDialog_jobLabel);
			setSystem(true);
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {

			this.itemsFilter = filter;

			contentProvider.reset();

			refreshWithLastSelection = false;

			contentProvider.addHistoryItems(itemsFilter);

			if (!(lastCompletedFilter != null && lastCompletedFilter
					.isSubFilter(this.itemsFilter)))
				contentProvider.refresh();

			filterJob.schedule();

			return Status.OK_STATUS;
		}

	}

	private class FilterJob extends Job {

		protected ItemsFilter itemsFilter;

		public FilterJob() {
			super(WorkbenchMessages.FilteredItemsSelectionDialog_jobLabel);
			setSystem(true);
		}

		@Override
		protected final IStatus run(IProgressMonitor parent) {
			GranualProgressMonitor monitor = new GranualProgressMonitor(parent);
			return doRun(monitor);
		}

		protected IStatus doRun(GranualProgressMonitor monitor) {
			try {
				internalRun(monitor);
			} catch (CoreException e) {
				cancel();
				return new Status(
						IStatus.ERROR,
						PlatformUI.PLUGIN_ID,
						IStatus.ERROR,
						WorkbenchMessages.FilteredItemsSelectionDialog_jobError,
						e);
			}
			return Status.OK_STATUS;
		}

		private void internalRun(GranualProgressMonitor monitor)
				throws CoreException {
			try {
				if (monitor.isCanceled())
					return;

				this.itemsFilter = filter;

				if (filter.getPattern().length() != 0) {
					filterContent(monitor);
				}

				if (monitor.isCanceled())
					return;

				contentProvider.refresh();
			} finally {
				monitor.done();
			}
		}

		protected void filterContent(GranualProgressMonitor monitor)
				throws CoreException {

			if (lastCompletedFilter != null
					&& lastCompletedFilter.isSubFilter(this.itemsFilter)) {

				int length = lastCompletedResult.size() / 500;
				monitor
						.beginTask(
								WorkbenchMessages.FilteredItemsSelectionDialog_cacheSearchJob_taskName,
								length);

				for (int pos = 0; pos < lastCompletedResult.size(); pos++) {

					Object item = lastCompletedResult.get(pos);
					if (monitor.isCanceled())
						break;
					contentProvider.add(item, itemsFilter);

					if ((pos % 500) == 0) {
						monitor.worked(1);
					}
				}

			} else {

				lastCompletedFilter = null;
				lastCompletedResult = null;

				SubProgressMonitor subMonitor = null;
				if (monitor != null) {
					monitor
							.beginTask(
									WorkbenchMessages.FilteredItemsSelectionDialog_searchJob_taskName,
									100);
					subMonitor = new SubProgressMonitor(monitor, 95);

				}

				fillContentProvider(contentProvider, itemsFilter, subMonitor);

				if (monitor != null && !monitor.isCanceled()) {
					monitor.worked(2);
					contentProvider.rememberResult(itemsFilter);
					monitor.worked(3);
				}
			}

		}

	}

	protected static abstract class SelectionHistory {

		private static final String DEFAULT_ROOT_NODE_NAME = "historyRootNode"; //$NON-NLS-1$

		private static final String DEFAULT_INFO_NODE_NAME = "infoNode"; //$NON-NLS-1$

		private static final int MAX_HISTORY_SIZE = 60;

		private final Set historyList;

		private final String rootNodeName;

		private final String infoNodeName;

		private SelectionHistory(String rootNodeName, String infoNodeName) {

			historyList = Collections.synchronizedSet(new LinkedHashSet() {

				private static final long serialVersionUID = 0L;

				@Override
				public boolean add(Object arg0) {
					if (this.size() >= MAX_HISTORY_SIZE) {
						Iterator iterator = this.iterator();
						iterator.next();
						iterator.remove();
					}
					return super.add(arg0);
				}

			});

			this.rootNodeName = rootNodeName;
			this.infoNodeName = infoNodeName;
		}

		public SelectionHistory() {
			this(DEFAULT_ROOT_NODE_NAME, DEFAULT_INFO_NODE_NAME);
		}

		public synchronized void accessed(Object object) {
			historyList.remove(object);
			historyList.add(object);
		}

		public synchronized boolean contains(Object object) {
			return historyList.contains(object);
		}

		public synchronized boolean isEmpty() {
			return historyList.isEmpty();
		}

		public synchronized boolean remove(Object element) {
			return historyList.remove(element);
		}

		public void load(IMemento memento) {

			XMLMemento historyMemento = (XMLMemento) memento
					.getChild(rootNodeName);

			if (historyMemento == null) {
				return;
			}

			IMemento[] mementoElements = historyMemento
					.getChildren(infoNodeName);
			for (int i = 0; i < mementoElements.length; ++i) {
				IMemento mementoElement = mementoElements[i];
				Object object = restoreItemFromMemento(mementoElement);
				if (object != null) {
					historyList.add(object);
				}
			}
		}

		public void save(IMemento memento) {

			IMemento historyMemento = memento.createChild(rootNodeName);

			Object[] items = getHistoryItems();
			for (int i = 0; i < items.length; i++) {
				Object item = items[i];
				IMemento elementMemento = historyMemento
						.createChild(infoNodeName);
				storeItemToMemento(item, elementMemento);
			}

		}

		public synchronized Object[] getHistoryItems() {
			return historyList.toArray();
		}

		protected abstract Object restoreItemFromMemento(IMemento memento);

		protected abstract void storeItemToMemento(Object item, IMemento memento);

	}

	protected abstract class ItemsFilter {

		protected SearchPattern patternMatcher;

		public ItemsFilter() {
			this(new SearchPattern());
		}

		public ItemsFilter(SearchPattern searchPattern) {
			patternMatcher = searchPattern;
			String stringPattern = ""; //$NON-NLS-1$
			if (pattern != null && !pattern.getText().equals("*")) { //$NON-NLS-1$
				stringPattern = pattern.getText();
			}
			patternMatcher.setPattern(stringPattern);
		}

		public boolean isSubFilter(ItemsFilter filter) {
			if (filter != null) {
				return this.patternMatcher.isSubPattern(filter.patternMatcher);
			}
			return false;
		}

		public boolean equalsFilter(ItemsFilter filter) {
			if (filter != null
					&& filter.patternMatcher.equalsPattern(this.patternMatcher)) {
				return true;
			}
			return false;
		}

		public boolean isCamelCasePattern() {
			return patternMatcher.getMatchRule() == SearchPattern.RULE_CAMELCASE_MATCH;
		}

		public String getPattern() {
			return patternMatcher.getPattern();
		}

		public int getMatchRule() {
			return patternMatcher.getMatchRule();
		}

		protected boolean matches(String text) {
			return patternMatcher.matches(text);
		}

		public boolean matchesRawNamePattern(Object item) {
			String prefix = patternMatcher.getPattern();
			String text = getElementName(item);

			if (text == null)
				return false;

			int textLength = text.length();
			int prefixLength = prefix.length();
			if (textLength < prefixLength) {
				return false;
			}
			for (int i = prefixLength - 1; i >= 0; i--) {
				if (Character.toLowerCase(prefix.charAt(i)) != Character
						.toLowerCase(text.charAt(i)))
					return false;
			}
			return true;
		}

		public abstract boolean matchItem(Object item);

		public abstract boolean isConsistentItem(Object item);

	}

	protected abstract class AbstractContentProvider {
		public abstract void add(Object item, ItemsFilter itemsFilter);
	}

	private class ContentProvider extends AbstractContentProvider implements
			IStructuredContentProvider, ILazyContentProvider {

		private SelectionHistory selectionHistory;

		private Set items;

		private Set duplicates;

		private List filters;

		private List lastFilteredItems;

		private List lastSortedItems;

		private boolean reset;

		public ContentProvider() {
			this.items = Collections.synchronizedSet(new HashSet(2048));
			this.duplicates = Collections.synchronizedSet(new HashSet(256));
			this.lastFilteredItems = new ArrayList();
			this.lastSortedItems = Collections.synchronizedList(new ArrayList(
					2048));
		}

		public void setSelectionHistory(SelectionHistory selectionHistory) {
			this.selectionHistory = selectionHistory;
		}

		public SelectionHistory getSelectionHistory() {
			return selectionHistory;
		}

		public void reset() {
			reset = true;
			this.items.clear();
			this.duplicates.clear();
			this.lastSortedItems.clear();
		}

		public void stopReloadingCache() {
			reset = true;
		}

		@Override
		public void add(Object item, ItemsFilter itemsFilter) {
			if (itemsFilter == filter) {
				if (itemsFilter != null) {
					if (itemsFilter.matchItem(item)) {
						this.items.add(item);
					}
				} else {
					this.items.add(item);
				}
			}
		}

		public void addHistoryItems(ItemsFilter itemsFilter) {
			if (this.selectionHistory != null) {
				Object[] items = this.selectionHistory.getHistoryItems();
				for (int i = 0; i < items.length; i++) {
					Object item = items[i];
					if (itemsFilter == filter) {
						if (itemsFilter != null) {
							if (itemsFilter.matchItem(item)) {
								if (itemsFilter.isConsistentItem(item)) {
									this.items.add(item);
								} else {
									this.selectionHistory.remove(item);
								}
							}
						}
					}
				}
			}
		}

		public void refresh() {
			scheduleRefresh();
		}

		public Object removeHistoryElement(Object item) {
			if (this.selectionHistory != null)
				this.selectionHistory.remove(item);
			if (filter == null || filter.getPattern().length() == 0) {
				items.remove(item);
				duplicates.remove(item);
				this.lastSortedItems.remove(item);
			}

			synchronized (lastSortedItems) {
				Collections.sort(lastSortedItems, getHistoryComparator());
			}
			return item;
		}

		public void addHistoryElement(Object item) {
			if (this.selectionHistory != null)
				this.selectionHistory.accessed(item);
			if (filter == null || !filter.matchItem(item)) {
				this.items.remove(item);
				this.duplicates.remove(item);
				this.lastSortedItems.remove(item);
			}
			synchronized (lastSortedItems) {
				Collections.sort(lastSortedItems, getHistoryComparator());
			}
			this.refresh();
		}

		public boolean isHistoryElement(Object item) {
			if (this.selectionHistory != null) {
				return this.selectionHistory.contains(item);
			}
			return false;
		}

		public void setDuplicateElement(Object item, boolean isDuplicate) {
			if (this.items.contains(item)) {
				if (isDuplicate)
					this.duplicates.add(item);
				else
					this.duplicates.remove(item);
			}
		}

		public boolean isDuplicateElement(Object item) {
			return duplicates.contains(item);
		}

		public void loadHistory(IMemento memento) {
			if (this.selectionHistory != null)
				this.selectionHistory.load(memento);
		}

		public void saveHistory(IMemento memento) {
			if (this.selectionHistory != null)
				this.selectionHistory.save(memento);
		}

		private Object[] getSortedItems() {
			if (lastSortedItems.size() != items.size()) {
				synchronized (lastSortedItems) {
					lastSortedItems.clear();
					lastSortedItems.addAll(items);
					Collections.sort(lastSortedItems, getHistoryComparator());
				}
			}
			return lastSortedItems.toArray();
		}

		public void rememberResult(ItemsFilter itemsFilter) {
			List itemsList = Collections.synchronizedList(Arrays
					.asList(getSortedItems()));
			if (itemsFilter == filter) {
				lastCompletedFilter = itemsFilter;
				lastCompletedResult = itemsList;
			}

		}

		@Override
		public Object[] getElements(Object inputElement) {
			return lastFilteredItems.toArray();
		}

		public int getNumberOfElements() {
			return lastFilteredItems.size();
		}

		@Override
		public void dispose() {
		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

		@Override
		public void updateElement(int index) {

			FilteredItemsSelectionDialog.this.list.replace((lastFilteredItems
					.size() > index) ? lastFilteredItems.get(index) : null,
					index);

		}

		public void reloadCache(boolean checkDuplicates,
				IProgressMonitor monitor) {

			reset = false;

			if (monitor != null) {
				int totalWork = checkDuplicates ? 200 : 100;

				monitor
						.beginTask(
								WorkbenchMessages.FilteredItemsSelectionDialog_cacheRefreshJob,
								totalWork);
			}


			lastFilteredItems = Arrays.asList(getFilteredItems(list.getInput(),
					monitor != null ? new SubProgressMonitor(monitor, 100)
							: null));

			if (reset || (monitor != null && monitor.isCanceled())) {
				if (monitor != null)
					monitor.done();
				return;
			}

			if (checkDuplicates) {
				checkDuplicates(monitor);
			}
			if (monitor != null)
				monitor.done();
		}

		private void checkDuplicates(IProgressMonitor monitor) {
			synchronized (lastFilteredItems) {
				IProgressMonitor subMonitor = null;
				int reportEvery = lastFilteredItems.size() / 20;
				if (monitor != null) {
					subMonitor = new SubProgressMonitor(monitor, 100);
					subMonitor
							.beginTask(
									WorkbenchMessages.FilteredItemsSelectionDialog_cacheRefreshJob_checkDuplicates,
									5);
				}
				HashMap helperMap = new HashMap();
				for (int i = 0; i < lastFilteredItems.size(); i++) {
					if (reset
							|| (subMonitor != null && subMonitor.isCanceled()))
						return;
					Object item = lastFilteredItems.get(i);

					if (!(item instanceof ItemsListSeparator)) {
						Object previousItem = helperMap.put(
								getElementName(item), item);
						if (previousItem != null) {
							setDuplicateElement(previousItem, true);
							setDuplicateElement(item, true);
						} else {
							setDuplicateElement(item, false);
						}
					}

					if (subMonitor != null && reportEvery != 0
							&& (i + 1) % reportEvery == 0)
						subMonitor.worked(1);
				}
				helperMap.clear();
			}
		}

		protected Object[] getFilteredItems(Object parent,
				IProgressMonitor monitor) {
			int ticks = 100;
			if (monitor == null) {
				monitor = new NullProgressMonitor();
			}

			monitor
					.beginTask(
							WorkbenchMessages.FilteredItemsSelectionDialog_cacheRefreshJob_getFilteredElements,
							ticks);
			if (filters != null) {
				ticks /= (filters.size() + 2);
			} else {
				ticks /= 2;
			}

			Object[] filteredElements = getSortedItems();

			monitor.worked(ticks);

			if (filters != null && filteredElements != null) {
				for (Iterator iter = filters.iterator(); iter.hasNext();) {
					ViewerFilter f = (ViewerFilter) iter.next();
					filteredElements = f.filter(list, parent, filteredElements);
					monitor.worked(ticks);
				}
			}

			if (filteredElements == null || monitor.isCanceled()) {
				monitor.done();
				return new Object[0];
			}

			ArrayList preparedElements = new ArrayList();
			boolean hasHistory = false;

			if (filteredElements.length > 0) {
				if (isHistoryElement(filteredElements[0])) {
					hasHistory = true;
				}
			}

			int reportEvery = filteredElements.length / ticks;

			for (int i = 0; i < filteredElements.length; i++) {
				Object item = filteredElements[i];

				if (hasHistory && !isHistoryElement(item)) {
					preparedElements.add(itemsListSeparator);
					hasHistory = false;
				}

				preparedElements.add(item);

				if (reportEvery != 0 && ((i + 1) % reportEvery == 0)) {
					monitor.worked(1);
				}
			}

			monitor.done();

			return preparedElements.toArray();
		}

		public void addFilter(ViewerFilter filter) {
			if (filters == null) {
				filters = new ArrayList();
			}
			filters.add(filter);
		}

	}

	private class NullContentProvider implements IContentProvider {

		@Override
		public void dispose() {
		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

	}

	private class DetailsContentViewer extends ContentViewer {

		private CLabel label;

		private ViewForm viewForm;

		public DetailsContentViewer(Composite parent, int style) {
			viewForm = new ViewForm(parent, style);
			GridData gd = new GridData(GridData.FILL_HORIZONTAL);
			gd.horizontalSpan = 2;
			viewForm.setLayoutData(gd);
			label = new CLabel(viewForm, SWT.FLAT);
			label.setFont(parent.getFont());
			viewForm.setContent(label);
			hookControl(label);
		}

		public void setVisible(boolean visible) {
			viewForm.setVisible(visible);
			GridData gd = (GridData) viewForm.getLayoutData();
			gd.exclude = !visible;
			viewForm.getParent().layout();
		}

		@Override
		protected void inputChanged(Object input, Object oldInput) {
			if (oldInput == null) {
				if (input == null) {
					return;
				}
				refresh();
				return;
			}

			refresh();

		}

		@Override
		protected void handleLabelProviderChanged(
				LabelProviderChangedEvent event) {
			if (event != null) {
				refresh(event.getElements());
			}
		}

		@Override
		public Control getControl() {
			return label;
		}

		@Override
		public ISelection getSelection() {
			return null;
		}

		@Override
		public void refresh() {
			Object input = this.getInput();
			if (input != null) {
				ILabelProvider labelProvider = (ILabelProvider) getLabelProvider();
				doRefresh(labelProvider.getText(input), labelProvider
						.getImage(input));
			} else {
				doRefresh(null, null);
			}
		}

		private void doRefresh(String text, Image image) {
			if ( text != null ) {
				text = LegacyActionTools.escapeMnemonics(text);
			}
			label.setText(text);
			label.setImage(image);
		}

		@Override
		public void setSelection(ISelection selection, boolean reveal) {
		}

		private void refresh(Object[] objs) {
			if (objs == null || getInput() == null) {
				return;
			}
			Object input = getInput();
			for (int i = 0; i < objs.length; i++) {
				if (objs[i].equals(input)) {
					refresh();
					break;
				}
			}
		}
	}

	private class HistoryComparator implements Comparator {

		@Override
		public int compare(Object o1, Object o2) {
			boolean h1 = isHistoryElement(o1);
			boolean h2 = isHistoryElement(o2);
			if (h1 == h2)
				return getItemsComparator().compare(o1, o2);

			if (h1)
				return -2;
			if (h2)
				return +2;

			return 0;
		}

	}
	

	public Control getPatternControl() {
		return pattern;
	}

}
