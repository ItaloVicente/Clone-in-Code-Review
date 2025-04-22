
package org.eclipse.ui.views.markers.internal;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.mapping.ResourceMapping;
import org.eclipse.core.resources.mapping.ResourceMappingContext;
import org.eclipse.core.resources.mapping.ResourceTraversal;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.help.HelpSystem;
import org.eclipse.help.IContext;
import org.eclipse.help.IContextProvider;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.HelpEvent;
import org.eclipse.swt.events.HelpListener;
import org.eclipse.swt.events.TreeAdapter;
import org.eclipse.swt.events.TreeEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchCommandConstants;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.XMLMemento;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ContributionItemFactory;
import org.eclipse.ui.actions.SelectionProviderAction;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.ide.ResourceUtil;
import org.eclipse.ui.internal.ide.IDEWorkbenchMessages;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;
import org.eclipse.ui.internal.ide.Policy;
import org.eclipse.ui.keys.IBindingService;
import org.eclipse.ui.operations.RedoActionHandler;
import org.eclipse.ui.operations.UndoActionHandler;
import org.eclipse.ui.part.IShowInSource;
import org.eclipse.ui.part.MarkerTransfer;
import org.eclipse.ui.part.ShowInContext;
import org.eclipse.ui.preferences.ViewPreferencesAction;
import org.eclipse.ui.progress.IWorkbenchSiteProgressService;
import org.eclipse.ui.progress.WorkbenchJob;
import org.eclipse.ui.views.markers.internal.MarkerAdapter.MarkerCategory;
import org.eclipse.ui.views.tasklist.ITaskListResourceAdapter;

public abstract class MarkerView extends TableView {

	private static final String TAG_SELECTION = "selection"; //$NON-NLS-1$

	private static final String TAG_MARKER = "marker"; //$NON-NLS-1$

	private static final String TAG_RESOURCE = "resource"; //$NON-NLS-1$

	private static final String TAG_ID = "id"; //$NON-NLS-1$

	private static final String TAG_FILTERS_SECTION = "filters"; //$NON-NLS-1$

	private static final String TAG_FILTER_ENTRY = "filter"; //$NON-NLS-1$

	private static final String MENU_FILTERS_GROUP = "group.filter";//$NON-NLS-1$

	private static final String MENU_SHOW_IN_GROUP = "group.showIn";//$NON-NLS-1$

	private static final String OLD_FILTER_SECTION = "filter"; //$NON-NLS-1$

	static final Object MARKER_UPDATE_FAMILY = new Object();

	class MarkerProcessJob extends Job {

		MarkerProcessJob() {
			super(MarkerMessages.MarkerView_processUpdates);
			setSystem(true);
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {
			updateForContentsRefresh(monitor);
			return Status.OK_STATUS;

		}

		@Override
		public boolean shouldRun() {
			return PlatformUI.isWorkbenchRunning();
		}

		@Override
		public boolean belongsTo(Object family) {
			return MARKER_UPDATE_FAMILY == family;
		}

	}

	MarkerProcessJob markerProcessJob = new MarkerProcessJob();

	private class UpdateJob extends WorkbenchJob {

		private class MarkerDescriptor {
			String description;

			String folder;

			String resource;

			int line;

			MarkerDescriptor(ConcreteMarker marker) {
				description = marker.getDescription();
				folder = marker.getFolder();
				resource = marker.getResourceName();
				line = marker.getLine();
			}

			boolean isEquivalentTo(ConcreteMarker marker) {
				return marker.getDescription().equals(description)
						&& marker.getFolder().equals(folder)
						&& marker.getResourceName().equals(resource)
						&& marker.getLine() == line;
			}

		}

		private Collection categoriesToExpand = new HashSet();

		private Collection preservedSelection = new ArrayList();

		UpdateJob() {
			super(MarkerMessages.MarkerView_queueing_updates);
			setSystem(true);
		}

		@Override
		public IStatus runInUIThread(IProgressMonitor monitor) {

			if (getViewer().getControl().isDisposed()) {
				return Status.CANCEL_STATUS;
			}

			if (monitor.isCanceled())
				return Status.CANCEL_STATUS;

			getViewer().refresh(true);

			Tree tree = getTree();

			if (tree != null && !tree.isDisposed()) {
				updateStatusMessage();
				updateTitle();
				if (getCurrentMarkers().getSize() < 20) {
					getViewer().expandAll();
				} else {// Reexpand the old categories
					MarkerCategory[] categories = getMarkerAdapter()
							.getCategories();
					if (categories == null)
						categoriesToExpand.clear();
					else {
						if (categories.length == 1) {// Expand if there is
							getViewer().expandAll();
							categoriesToExpand.clear();
							if (monitor.isCanceled())
								return Status.CANCEL_STATUS;
							categoriesToExpand.add(categories[0].getName());
						} else {
							Collection newCategories = new HashSet();
							for (int i = 0; i < categories.length; i++) {
								if (monitor.isCanceled())
									return Status.CANCEL_STATUS;
								MarkerCategory category = categories[i];
								if (categoriesToExpand.contains(category
										.getName())) {
									getViewer().expandToLevel(category,
											AbstractTreeViewer.ALL_LEVELS);
									newCategories.add(category.getName());
								}

							}
							categoriesToExpand = newCategories;
						}
					}

				}
			}

			if (preservedSelection.size() > 0) {

				Collection newSelection = new ArrayList();
				ConcreteMarker[] markers = getCurrentMarkers().toArray();

				for (int i = 0; i < markers.length; i++) {
					Iterator preserved = preservedSelection.iterator();
					while (preserved.hasNext()) {
						MarkerDescriptor next = (MarkerDescriptor) preserved
								.next();
						if (next.isEquivalentTo(markers[i])) {
							newSelection.add(markers[i]);
							continue;
						}
					}
				}

				getViewer().setSelection(
						new StructuredSelection(newSelection.toArray()), true);
				preservedSelection.clear();
			}
			if (getViewer().getTree().getItemCount() > 0)
				getViewer().getTree().setTopItem(
						getViewer().getTree().getItem(0));

			return Status.OK_STATUS;
		}

		public void addExpandedCategory(MarkerCategory category) {
			categoriesToExpand.add(category.getName());

		}

		public void removeExpandedCategory(MarkerCategory category) {
			categoriesToExpand.remove(category.getName());

		}

		@Override
		public boolean belongsTo(Object family) {
			return family == MARKER_UPDATE_FAMILY;
		}

		public void saveSelection(ISelection selection) {
			preservedSelection.clear();
			if (selection instanceof IStructuredSelection) {
				IStructuredSelection structured = (IStructuredSelection) selection;
				Iterator iterator = structured.iterator();
				while (iterator.hasNext()) {
					MarkerNode next = (MarkerNode) iterator.next();
					if (next.isConcrete()) {
						preservedSelection.add(new MarkerDescriptor(next
								.getConcreteRepresentative()));
					}
				}
			}

		}

		@Override
		public boolean shouldRun() {
			return !getMarkerAdapter().isBuilding();
		}

	}

	private UpdateJob updateJob = new UpdateJob();

	private int preBusyMarkers = 0;

	protected Object[] focusElements;

	private Clipboard clipboard;

	IResourceChangeListener markerUpdateListener = new IResourceChangeListener() {

		@Override
		public void resourceChanged(IResourceChangeEvent event) {
			if (!hasMarkerDelta(event))
				return;

			if (event.getType() == IResourceChangeEvent.POST_BUILD) {
				scheduleMarkerUpdate(Util.SHORT_DELAY);
				return;
			}


			IWorkbenchSiteProgressService progressService = getProgressService();
			if (progressService == null)
				markerProcessJob.schedule(Util.LONG_DELAY);
			else
				getProgressService()
						.schedule(markerProcessJob, Util.LONG_DELAY);

		}

		private boolean hasMarkerDelta(IResourceChangeEvent event) {
			String[] markerTypes = getMarkerTypes();
			for (int i = 0; i < markerTypes.length; i++) {
				if (event.findMarkerDeltas(markerTypes[i], true).length > 0) {
					return true;
				}
			}
			return false;
		}

	};

	private class ContextProvider implements IContextProvider {
		@Override
		public int getContextChangeMask() {
			return SELECTION;
		}

		@Override
		public IContext getContext(Object target) {
			String contextId = null;
			ConcreteMarker marker = getSelectedConcreteMarker();
			if (marker != null) {
				contextId = IDE.getMarkerHelpRegistry().getHelp(
						marker.getMarker());
			}

			if (contextId == null) {
				contextId = getStaticContextId();
			}
			return HelpSystem.getContext(contextId);
		}

		private ConcreteMarker getSelectedConcreteMarker() {

			IStructuredSelection selection = (IStructuredSelection) getViewer()
					.getSelection();
			if (selection.isEmpty())
				return null;

			if (selection.getFirstElement() instanceof ConcreteMarker)
				return (ConcreteMarker) selection.getFirstElement();
			return null;
		}

		@Override
		public String getSearchExpression(Object target) {
			return null;
		}
	}

	private ContextProvider contextProvider = new ContextProvider();

	protected ActionCopyMarker copyAction;

	protected ActionPasteMarker pasteAction;

	protected SelectionProviderAction revealAction;

	protected SelectionProviderAction openAction;

	protected SelectionProviderAction deleteAction;

	protected SelectionProviderAction selectAllAction;

	protected SelectionProviderAction propertiesAction;

	protected UndoActionHandler undoAction;

	protected RedoActionHandler redoAction;

	private ISelectionListener focusListener = new ISelectionListener() {
		@Override
		public void selectionChanged(IWorkbenchPart part, ISelection selection) {
			MarkerView.this.focusSelectionChanged(part, selection);
		}
	};

	private int totalMarkers = 0;

	private MarkerFilter[] markerFilters = new MarkerFilter[0];

	private MarkerFilter[] enabledFilters = null;

	private MenuManager filtersMenu;

	private MenuManager showInMenu;

	private IPropertyChangeListener workingSetListener;

	private MarkerAdapter adapter;

	private IPropertyChangeListener preferenceListener;

	public MarkerView() {
		super();
		preferenceListener = new IPropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				if (event.getProperty().equals(getFiltersPreferenceName())) {
					loadFiltersPreferences();
					clearEnabledFilters();
					refreshForFocusUpdate();
				}
			}
		};
		IDEWorkbenchPlugin.getDefault().getPreferenceStore()
				.addPropertyChangeListener(preferenceListener);
	}

	public MarkerList getCurrentMarkers() {
		return getMarkerAdapter().getCurrentMarkers();
	}

	protected MarkerAdapter getMarkerAdapter() {
		return adapter;
	}

	public void updateForContentsRefresh(IProgressMonitor monitor) {
		updateJob.cancel();
		getMarkerAdapter().buildAllMarkers(monitor);
		getProgressService().schedule(updateJob);
	}

	@Override
	public void init(IViewSite site, IMemento memento) throws PartInitException {
		super.init(site, memento);
		IWorkbenchSiteProgressService progressService = getProgressService();
		if (progressService != null) {
			getProgressService().showBusyForFamily(
					ResourcesPlugin.FAMILY_MANUAL_BUILD);
			getProgressService().showBusyForFamily(
					ResourcesPlugin.FAMILY_AUTO_BUILD);
			getProgressService().showBusyForFamily(MARKER_UPDATE_FAMILY);
		}
		loadFiltersPreferences();

	}

	private void loadFiltersPreferences() {

		String preference = IDEWorkbenchPlugin.getDefault()
				.getPreferenceStore().getString(getFiltersPreferenceName());

		if (preference.equals(IPreferenceStore.STRING_DEFAULT_DEFAULT)) {
			createDefaultFilter();
			return;
		}

		StringReader reader = new StringReader(preference);
		try {
			restoreFilters(XMLMemento.createReadRoot(reader));
		} catch (WorkbenchException e) {
			IDEWorkbenchPlugin.log(e.getLocalizedMessage(), e);
		}

	}

	void updateForFilterChanges() {

		XMLMemento memento = XMLMemento.createWriteRoot(TAG_FILTERS_SECTION);

		writeFiltersSettings(memento);

		StringWriter writer = new StringWriter();
		try {
			memento.save(writer);
		} catch (IOException e) {
			IDEWorkbenchPlugin.getDefault().getLog().log(Util.errorStatus(e));
		}

		IDEWorkbenchPlugin.getDefault().getPreferenceStore().putValue(
				getFiltersPreferenceName(), writer.toString());
		IDEWorkbenchPlugin.getDefault().savePluginPreferences();

		clearEnabledFilters();
		refreshFilterMenu();
		refreshViewer();
	}

	protected void writeFiltersSettings(XMLMemento memento) {
		MarkerFilter[] filters = getUserFilters();
		for (int i = 0; i < filters.length; i++) {
			IMemento child = memento.createChild(TAG_FILTER_ENTRY, filters[i]
					.getName());
			filters[i].saveFilterSettings(child);
		}
	}

	abstract String getFiltersPreferenceName();

	void restoreFilters(IMemento memento) {

		IMemento[] sections = null;
		if (memento != null) {
			sections = memento.getChildren(TAG_FILTER_ENTRY);
		}

		if (sections == null) {
			IDialogSettings mainSettings = getDialogSettings();
			IDialogSettings filtersSection = mainSettings
					.getSection(OLD_FILTER_SECTION);
			if (filtersSection != null) {
				MarkerFilter markerFilter = createFilter(MarkerMessages.MarkerFilter_defaultFilterName);
				markerFilter.restoreFilterSettings(filtersSection);
				setFilters(new MarkerFilter[] { markerFilter });
			}

		} else {
			MarkerFilter[] newFilters = new MarkerFilter[sections.length];

			for (int i = 0; i < sections.length; i++) {
				newFilters[i] = createFilter(sections[i].getID());
				newFilters[i].restoreState(sections[i]);
			}
			setFilters(newFilters);
		}

		if (markerFilters.length == 0) {// Make sure there is at least a default
			createDefaultFilter();
		}

	}

	private void createDefaultFilter() {
		MarkerFilter filter = createFilter(MarkerMessages.MarkerFilter_defaultFilterName);
		setFilters(new MarkerFilter[] { filter });
	}

	protected abstract MarkerFilter createFilter(String name);

	protected abstract String getSectionTag();

	@Override
	public void createPartControl(Composite parent) {

		clipboard = new Clipboard(parent.getDisplay());
		super.createPartControl(parent);

		initDragAndDrop();

		getSite().getPage().addSelectionListener(focusListener);
		focusSelectionChanged(getSite().getPage().getActivePart(), getSite()
				.getPage().getSelection());
		PlatformUI.getWorkbench().getWorkingSetManager()
				.addPropertyChangeListener(getWorkingSetListener());

		getViewer().getControl().addHelpListener(new HelpListener() {
			@Override
			public void helpRequested(HelpEvent e) {
				IContext context = contextProvider.getContext(getViewer()
						.getControl());
				PlatformUI.getWorkbench().getHelpSystem().displayHelp(context);
			}
		});

		ResourcesPlugin.getWorkspace().addResourceChangeListener(
				markerUpdateListener,
				IResourceChangeEvent.POST_CHANGE
						| IResourceChangeEvent.PRE_BUILD
						| IResourceChangeEvent.POST_BUILD);
	}

	@Override
	public Object getAdapter(Class adaptable) {
		if (adaptable.equals(IContextProvider.class)) {
			return contextProvider;
		}
		if (adaptable.equals(IShowInSource.class)) {
			return new IShowInSource() {
				@Override
				public ShowInContext getShowInContext() {
					ISelection selection = getViewer().getSelection();
					if (!(selection instanceof IStructuredSelection)) {
						return null;
					}
					IStructuredSelection structured = (IStructuredSelection) selection;
					Iterator markerIterator = structured.iterator();
					List newSelection = new ArrayList();
					while (markerIterator.hasNext()) {
						ConcreteMarker element = (ConcreteMarker) markerIterator
								.next();
						newSelection.add(element.getResource());
					}
					return new ShowInContext(getViewer().getInput(),
							new StructuredSelection(newSelection));
				}

			};
		}
		return super.getAdapter(adaptable);
	}

	@Override
	protected void viewerSelectionChanged(IStructuredSelection selection) {

		Object[] rawSelection = selection.toArray();

		List markers = new ArrayList();

		for (int idx = 0; idx < rawSelection.length; idx++) {

			if (rawSelection[idx] instanceof ConcreteMarker)
				markers.add(((ConcreteMarker) rawSelection[idx]).getMarker());
		}

		setSelection(new StructuredSelection(markers));

		updateStatusMessage(selection);
	}

	@Override
	public void dispose() {
		super.dispose();
		cancelJobs();

		ResourcesPlugin.getWorkspace().removeResourceChangeListener(
				markerUpdateListener);
		PlatformUI.getWorkbench().getWorkingSetManager()
				.removePropertyChangeListener(workingSetListener);
		IDEWorkbenchPlugin.getDefault().getPreferenceStore()
				.removePropertyChangeListener(preferenceListener);
		getSite().getPage().removeSelectionListener(focusListener);

		if (openAction != null) {
			openAction.dispose();
			copyAction.dispose();
			selectAllAction.dispose();
			deleteAction.dispose();
			revealAction.dispose();
			propertiesAction.dispose();
			undoAction.dispose();
			redoAction.dispose();
			clipboard.dispose();
		}
		if (showInMenu != null) {
			showInMenu.dispose();
		}
	}

	@Override
	protected void createActions() {
		revealAction = new ActionRevealMarker(this, getViewer());
		openAction = new ActionOpenMarker(this, getViewer());
		copyAction = new ActionCopyMarker(this, getViewer());
		copyAction.setClipboard(clipboard);
		copyAction.setProperties(getSortingFields());
		pasteAction = new ActionPasteMarker(this, getViewer(), getMarkerName());
		pasteAction.setClipboard(clipboard);
		pasteAction.setPastableTypes(getMarkerTypes());
		deleteAction = new ActionRemoveMarker(this, getViewer(),
				getMarkerName());
		selectAllAction = new ActionSelectAll(this);
		propertiesAction = new ActionMarkerProperties(this, getViewer(),
				getMarkerName());

		IUndoContext undoContext = getUndoContext();
		undoAction = new UndoActionHandler(getSite(), undoContext);
		redoAction = new RedoActionHandler(getSite(), undoContext);

		super.createActions();

		setFilterAction(new FiltersAction(this));

		setPreferencesAction(new ViewPreferencesAction() {
			@Override
			public void openViewPreferencesDialog() {
				openPreferencesDialog(getMarkerEnablementPreferenceName(),
						getMarkerLimitPreferenceName());

			}

		});
	}

	private void openPreferencesDialog(String markerEnablementPreferenceName,
			String markerLimitPreferenceName) {

		Dialog dialog = new MarkerViewPreferenceDialog(getSite()
				.getWorkbenchWindow().getShell(),
				markerEnablementPreferenceName, markerLimitPreferenceName,
				MarkerMessages.MarkerPreferences_DialogTitle);
		if (dialog.open() == Window.OK) {
			refreshViewer();
		}

	}

	abstract String getMarkerLimitPreferenceName();

	abstract String[] getMarkerTypes();

	@Override
	protected void initToolBar(IToolBarManager tbm) {
		tbm.add(deleteAction);
		tbm.add(getFilterAction());
		tbm.update(false);
	}

	@Override
	protected void registerGlobalActions(IActionBars actionBars) {
		copyAction.setActionDefinitionId(IWorkbenchCommandConstants.EDIT_COPY);
		pasteAction.setActionDefinitionId(IWorkbenchCommandConstants.EDIT_PASTE);
		deleteAction.setActionDefinitionId(IWorkbenchCommandConstants.EDIT_DELETE);
		selectAllAction.setActionDefinitionId(IWorkbenchCommandConstants.EDIT_SELECT_ALL);
		propertiesAction.setActionDefinitionId(IWorkbenchCommandConstants.FILE_PROPERTIES);
		undoAction.setActionDefinitionId(IWorkbenchCommandConstants.EDIT_UNDO);
		redoAction.setActionDefinitionId(IWorkbenchCommandConstants.EDIT_REDO);
		
		actionBars.setGlobalActionHandler(ActionFactory.COPY.getId(),
				copyAction);
		actionBars.setGlobalActionHandler(ActionFactory.PASTE.getId(),
				pasteAction);
		actionBars.setGlobalActionHandler(ActionFactory.DELETE.getId(),
				deleteAction);
		actionBars.setGlobalActionHandler(ActionFactory.SELECT_ALL.getId(),
				selectAllAction);
		actionBars.setGlobalActionHandler(ActionFactory.PROPERTIES.getId(),
				propertiesAction);
		actionBars.setGlobalActionHandler(ActionFactory.UNDO.getId(),
				undoAction);
		actionBars.setGlobalActionHandler(ActionFactory.REDO.getId(),
				redoAction);
	}

	protected void initDragAndDrop() {
		int operations = DND.DROP_COPY;
		Transfer[] transferTypes = new Transfer[] {
				MarkerTransfer.getInstance(), TextTransfer.getInstance() };
		DragSourceListener listener = new DragSourceAdapter() {
			@Override
			public void dragSetData(DragSourceEvent event) {
				performDragSetData(event);
			}

			@Override
			public void dragFinished(DragSourceEvent event) {
			}
		};

		getViewer().addDragSupport(operations, transferTypes, listener);
	}

	private void performDragSetData(DragSourceEvent event) {
		if (MarkerTransfer.getInstance().isSupportedType(event.dataType)) {

			event.data = getSelectedMarkers();
			return;
		}
		if (TextTransfer.getInstance().isSupportedType(event.dataType)) {
			List selection = ((IStructuredSelection) getViewer().getSelection())
					.toList();
			try {
				IMarker[] markers = new IMarker[selection.size()];
				selection.toArray(markers);
				if (markers != null) {
					event.data = copyAction.createMarkerReport(markers);
				}
			} catch (ArrayStoreException e) {
			}
		}
	}

	private IMarker[] getSelectedMarkers() {
		Object[] selection = ((IStructuredSelection) getViewer().getSelection())
				.toArray();
		ArrayList markers = new ArrayList();
		for (int i = 0; i < selection.length; i++) {
			if (selection[i] instanceof ConcreteMarker) {
				markers.add(((ConcreteMarker) selection[i]).getMarker());
			}
		}
		return (IMarker[]) markers.toArray(new IMarker[markers.size()]);
	}

	@Override
	protected void fillContextMenu(IMenuManager manager) {
		if (manager == null) {
			return;
		}
		manager.add(openAction);
		createShowInMenu(manager);
		manager.add(new Separator());
		manager.add(copyAction);
		pasteAction.updateEnablement();
		manager.add(pasteAction);

		if (canBeEditable()) {
			manager.add(deleteAction);
		}
		manager.add(selectAllAction);
		fillContextMenuAdditions(manager);
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		manager.add(new Separator());
		manager.add(propertiesAction);
	}

	boolean canBeEditable() {
		return true;
	}

	abstract void fillContextMenuAdditions(IMenuManager manager);

	protected final MarkerFilter[] getUserFilters() {
		return markerFilters;
	}



	@Override
	protected void handleOpenEvent(OpenEvent event) {
		if (openAction.isEnabled()) {
			openAction.run();
		}
	}

	protected void saveSelection(IMemento memento) {
		IStructuredSelection selection = (IStructuredSelection) getViewer()
				.getSelection();
		IMemento selectionMem = memento.createChild(TAG_SELECTION);
		for (Iterator iterator = selection.iterator(); iterator.hasNext();) {
			Object next = iterator.next();
			if (!(next instanceof ConcreteMarker)) {
				continue;
			}
			ConcreteMarker marker = (ConcreteMarker) next;
			IMemento elementMem = selectionMem.createChild(TAG_MARKER);
			elementMem.putString(TAG_RESOURCE, marker.getMarker().getResource()
					.getFullPath().toString());
			elementMem.putString(TAG_ID, String.valueOf(marker.getMarker()
					.getId()));
		}
	}

	protected abstract String[] getRootTypes();

	protected void focusSelectionChanged(IWorkbenchPart part,
			ISelection selection) {

		List selectedElements = new ArrayList();
		if (part instanceof IEditorPart) {
			IEditorPart editor = (IEditorPart) part;
			IFile file = ResourceUtil.getFile(editor.getEditorInput());
			if (file == null) {
				IEditorInput editorInput = editor.getEditorInput();
				if (editorInput != null) {
					Object mapping = editorInput
							.getAdapter(ResourceMapping.class);
					if (mapping != null) {
						selectedElements.add(mapping);
					}
				}
			} else {
				selectedElements.add(file);
			}
		} else {
			if (selection instanceof IStructuredSelection) {
				for (Iterator iterator = ((IStructuredSelection) selection)
						.iterator(); iterator.hasNext();) {
					Object object = iterator.next();
						ITaskListResourceAdapter taskListResourceAdapter;
						Object adapter = org.eclipse.ui.internal.util.Util.
								getAdapter(object, ITaskListResourceAdapter.class);
						if (adapter != null
								&& adapter instanceof ITaskListResourceAdapter) {
							taskListResourceAdapter = (ITaskListResourceAdapter) adapter;
						} else {
							taskListResourceAdapter = DefaultMarkerResourceAdapter
									.getDefault();
						}

					if (object instanceof IAdaptable) {
						IResource resource = taskListResourceAdapter
								.getAffectedResource((IAdaptable) object);
						if (resource == null) {
							Object mapping = org.eclipse.ui.internal.util.Util.
									getAdapter(object, ResourceMapping.class);
							if (mapping != null) {
								selectedElements.add(mapping);
							}
						} else {
							selectedElements.add(resource);
						}
					}
				}
			}
		}
		updateFocusMarkers(selectedElements.toArray());
	}

	protected final void updateFilterSelection(Object[] elements) {

		Collection resourceCollection = new ArrayList();
		for (int i = 0; i < elements.length; i++) {
			if (elements[i] instanceof IResource) {
				resourceCollection.add(elements[i]);
			} else {
				addResources(resourceCollection,
						((ResourceMapping) elements[i]));
			}
		}

		IResource[] resources = new IResource[resourceCollection.size()];
		resourceCollection.toArray(resources);

		for (int i = 0; i < markerFilters.length; i++) {
			markerFilters[i].setFocusResource(resources);
		}

		Iterator systemFilters = MarkerSupportRegistry.getInstance()
				.getRegisteredFilters().iterator();

		while (systemFilters.hasNext()) {
			MarkerFilter filter = (MarkerFilter) systemFilters.next();
			filter.setFocusResource(resources);

		}

	}

	private void addResources(Collection resources, ResourceMapping mapping) {
		try {
			ResourceTraversal[] traversals = mapping.getTraversals(
					ResourceMappingContext.LOCAL_CONTEXT,
					new NullProgressMonitor());
			for (int i = 0; i < traversals.length; i++) {
				ResourceTraversal traversal = traversals[i];
				IResource[] result = traversal.getResources();
				for (int j = 0; j < result.length; j++) {
					resources.add(result[j]);
				}
			}
		} catch (CoreException e) {
			Policy.handle(e);
			return;
		}

	}

	protected abstract String getStaticContextId();

	void updateFocusMarkers(Object[] elements) {
		boolean updateNeeded = updateNeeded(focusElements, elements);
		if (updateNeeded) {
			focusElements = elements;
			refreshForFocusUpdate();
		}
	}

	private boolean updateNeeded(Object[] oldElements, Object[] newElements) {
		MarkerFilter[] filters = getEnabledFilters();
		boolean updateNeeded = false;

		for (int i = 0; i < filters.length; i++) {
			MarkerFilter filter = filters[i];
			if (!filter.isEnabled()) {
				continue;
			}

			int onResource = filter.getOnResource();
			if (onResource == MarkerFilter.ON_ANY
					|| onResource == MarkerFilter.ON_WORKING_SET) {
				continue;
			}
			if (newElements == null || newElements.length < 1) {
				continue;
			}
			if (oldElements == null || oldElements.length < 1) {
				return true;
			}
			if (Arrays.equals(oldElements, newElements)) {
				continue;
			}
			if (onResource == MarkerFilter.ON_ANY_IN_SAME_CONTAINER) {
				Collection oldProjects = MarkerFilter
						.getProjectsAsCollection(oldElements);
				Collection newProjects = MarkerFilter
						.getProjectsAsCollection(newElements);

				if (oldProjects.size() == newProjects.size()) {
					if (newProjects.containsAll(oldProjects)) {
						continue;
					}
				}

				return true;
			}
			updateNeeded = true;// We are updating as there is nothing to stop
		}

		return updateNeeded;
	}

	void updateTitle() {
		String status = Util.EMPTY_STRING;
		int filteredCount = getCurrentMarkers().getItemCount();
		int totalCount = getTotalMarkers();
		if (filteredCount == totalCount) {
			status = NLS.bind(MarkerMessages.filter_itemsMessage, new Integer(
					totalCount));
		} else {
			status = NLS.bind(MarkerMessages.filter_matchedMessage,
					new Integer(filteredCount), new Integer(totalCount));
		}
		setContentDescription(status);
	}

	protected void updateStatusMessage() {
		ISelection selection = getViewer().getSelection();

		if (selection instanceof IStructuredSelection) {
			updateStatusMessage((IStructuredSelection) selection);
		} else {
			updateStatusMessage(null);
		}
	}

	protected void updateStatusMessage(IStructuredSelection selection) {
		String message = ""; //$NON-NLS-1$

		if (selection == null || selection.size() == 0) {
			message = updateSummaryVisible();
		} else if (selection.size() == 1) {
			Object first = selection.getFirstElement();
			if (first instanceof ConcreteMarker) {
				message = ((ConcreteMarker) first).getDescription();
			}
		} else if (selection.size() > 1) {
			message = updateSummarySelected(selection);
		}
		getViewSite().getActionBars().getStatusLineManager()
				.setMessage(message);
	}

	protected String updateSummarySelected(IStructuredSelection selection) {
		return MessageFormat.format(
				MarkerMessages.marker_statusSummarySelected,
				new Object[] { new Integer(selection.size()) });
	}

	protected String updateSummaryVisible() {
		return ""; //$NON-NLS-1$
	}

	public final void openFiltersDialog() {

		DialogMarkerFilter dialog = createFiltersDialog();

		if (dialog.open() == Window.OK) {

			MarkerFilter[] result = dialog.getFilters();
			if (result == null) {
				return;
			}
			if (result.length == 0) {
				setFilters(new MarkerFilter[] { createFilter(MarkerMessages.MarkerFilter_defaultFilterName) });
			} else {
				setFilters(result);
			}

			updateForFilterChanges();
		}
	}

	public void refreshViewer() {
		scheduleMarkerUpdate(Util.SHORT_DELAY);
	}

	void setFilters(MarkerFilter[] newFilters) {
		markerFilters = newFilters;
	}

	void clearEnabledFilters() {
		enabledFilters = null;
	}

	private void refreshFilterMenu() {
		if (filtersMenu == null) {
			return;
		}
		filtersMenu.removeAll();
		MarkerFilter[] filters = getAllFilters();
		for (int i = 0; i < filters.length; i++) {
			filtersMenu.add(new FilterEnablementAction(filters[i], this));
		}

	}

	protected abstract DialogMarkerFilter createFiltersDialog();

	public void setSelection(IStructuredSelection structuredSelection,
			boolean reveal) {
		TreeViewer viewer = getViewer();

		List newSelection = new ArrayList(structuredSelection.size());

		for (Iterator i = structuredSelection.iterator(); i.hasNext();) {
			Object next = i.next();
			if (next instanceof IMarker) {
				ConcreteMarker marker = getCurrentMarkers().getMarker(
						(IMarker) next);
				if (marker != null) {
					newSelection.add(marker);
				}
			}
		}

		if (viewer != null) {
			viewer.setSelection(new StructuredSelection(newSelection), reveal);
		}
	}

	protected MarkerList getVisibleMarkers() {
		return getCurrentMarkers();
	}

	int getTotalMarkers() {

		return totalMarkers;
	}

	@Override
	public void showBusy(boolean busy) {
		super.showBusy(busy);

		if (busy) {
			preBusyMarkers = totalMarkers;
		} else {// Only bold if there has been a change in count
			if (totalMarkers != preBusyMarkers) {
				getProgressService().warnOfContentChange();
			}
		}

	}

	MarkerFilter[] getEnabledFilters() {

		if (enabledFilters == null) {
			Collection filters = findEnabledFilters();

			enabledFilters = new MarkerFilter[filters.size()];
			filters.toArray(enabledFilters);
		}
		return enabledFilters;

	}

	protected Collection findEnabledFilters() {
		MarkerFilter[] allFilters = getAllFilters();
		ArrayList filters = new ArrayList(0);
		for (int i = 0; i < allFilters.length; i++) {
			if (allFilters[i].isEnabled()) {
				filters.add(allFilters[i]);
			}
		}
		return filters;
	}

	MarkerFilter[] getAllFilters() {
		return getUserFilters();
	}

	@Override
	void addDropDownContributions(IMenuManager menu) {
		super.addDropDownContributions(menu);

		menu.add(new Separator(MENU_FILTERS_GROUP));
		filtersMenu = new MenuManager(MarkerMessages.filtersSubMenu_title);
		refreshFilterMenu();
		menu.appendToGroup(MENU_FILTERS_GROUP, filtersMenu);
	}

	void createShowInMenu(IMenuManager menu) {
		ISelection selection = getViewer().getSelection();
		if (!(selection instanceof IStructuredSelection)) {
			return;
		}

		IStructuredSelection structured = (IStructuredSelection) selection;
		if (!Util.isSingleConcreteSelection(structured)) {
			return;
		}

		menu.add(new Separator(MENU_SHOW_IN_GROUP));

		String showInLabel = IDEWorkbenchMessages.Workbench_showIn;
		IBindingService bindingService = (IBindingService) PlatformUI
				.getWorkbench().getAdapter(IBindingService.class);
		if (bindingService != null) {
			String keyBinding = bindingService
					.getBestActiveBindingFormattedFor(IWorkbenchCommandConstants.NAVIGATE_SHOW_IN_QUICK_MENU);
			if (keyBinding != null) {
				showInLabel += '\t' + keyBinding;
			}
		}
		showInMenu = new MenuManager(showInLabel);
		showInMenu.add(ContributionItemFactory.VIEWS_SHOW_IN
				.create(getViewSite().getWorkbenchWindow()));

		menu.appendToGroup(MENU_SHOW_IN_GROUP, showInMenu);

	}

	void refreshMarkerCounts(IProgressMonitor monitor) {
		monitor.subTask(MarkerMessages.MarkerView_refreshing_counts);
		try {
			totalMarkers = MarkerList.compute(getMarkerTypes()).length;
		} catch (CoreException e) {
			Policy.handle(e);
			return;
		}

	}

	int getMarkerLimit() {

		if (IDEWorkbenchPlugin.getDefault().getPreferenceStore().getBoolean(
				getMarkerEnablementPreferenceName())) {
			return IDEWorkbenchPlugin.getDefault().getPreferenceStore().getInt(
					getMarkerLimitPreferenceName());

		}
		return -1;

	}

	abstract String getMarkerEnablementPreferenceName();

	@Override
	Object createViewerInput() {
		adapter = new MarkerAdapter(this);
		return adapter;
	}

	public void addUpdateFinishListener(IJobChangeListener listener) {
		updateJob.addJobChangeListener(listener);

	}

	public void removeUpdateFinishListener(IJobChangeListener listener) {
		updateJob.removeJobChangeListener(listener);

	}

	private IPropertyChangeListener getWorkingSetListener() {
		workingSetListener = new IPropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				clearEnabledFilters();
				refreshViewer();

			}
		};
		return workingSetListener;
	}

	void scheduleMarkerUpdate(int time) {
		cancelJobs();
		getProgressService().schedule(markerProcessJob, time);
	}

	private void cancelJobs() {
		markerProcessJob.cancel();
		updateJob.cancel();
	}

	@Override
	protected Tree createTree(Composite parent) {
		Tree tree = super.createTree(parent);
		tree.addTreeListener(new TreeAdapter() {
			@Override
			public void treeCollapsed(TreeEvent e) {
				updateJob.removeExpandedCategory((MarkerCategory) e.item
						.getData());
			}

			@Override
			public void treeExpanded(TreeEvent e) {
				updateJob
						.addExpandedCategory((MarkerCategory) e.item.getData());
			}
		});

		return tree;
	}

	private void refreshForFocusUpdate() {
		if (focusElements != null) {
			updateFilterSelection(focusElements);
			refreshViewer();
		}
	}

	protected void preserveSelection() {
		updateJob.saveSelection(getViewer().getSelection());

	}

	protected abstract String getMarkerName();

	protected IUndoContext getUndoContext() {
		return (IUndoContext) ResourcesPlugin.getWorkspace().getAdapter(
				IUndoContext.class);
	}

}
