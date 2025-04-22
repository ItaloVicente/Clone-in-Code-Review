
package org.eclipse.ui.internal.navigator.resources.actions;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IAggregateWorkingSet;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IWorkbenchPreferenceConstants;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.IWorkingSetManager;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ResourceWorkingSetFilter;
import org.eclipse.ui.actions.WorkingSetFilterActionGroup;
import org.eclipse.ui.internal.navigator.NavigatorFilterService;
import org.eclipse.ui.internal.navigator.resources.plugin.WorkbenchNavigatorMessages;
import org.eclipse.ui.internal.navigator.resources.plugin.WorkbenchNavigatorPlugin;
import org.eclipse.ui.internal.navigator.workingsets.WorkingSetsContentProvider;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.CommonViewer;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.IExtensionActivationListener;
import org.eclipse.ui.navigator.IExtensionStateModel;
import org.eclipse.ui.navigator.INavigatorContentService;

public class WorkingSetActionProvider extends CommonActionProvider {

	private static final String TAG_CURRENT_WORKING_SET_NAME = "currentWorkingSetName"; //$NON-NLS-1$

	private static final String WORKING_SET_FILTER_ID = "org.eclipse.ui.navigator.resources.filters.workingSet"; //$NON-NLS-1$
	
	private boolean contributedToViewMenu = false;

	private CommonViewer viewer;

	private INavigatorContentService contentService;

	private NavigatorFilterService filterService;
	
	private WorkingSetFilterActionGroup workingSetActionGroup;
	private WorkingSetRootModeActionGroup workingSetRootModeActionGroup;

	private Object originalViewerInput = ResourcesPlugin.getWorkspace().getRoot();

	private IExtensionStateModel extensionStateModel;

	private boolean emptyWorkingSet;
	private IWorkingSet workingSet;

	private IPropertyChangeListener topLevelModeListener;
	
	private boolean ignoreFilterChangeEvents;

	public class WorkingSetManagerListener implements IPropertyChangeListener {

		private boolean listening = false;

		@Override
		public void propertyChange(PropertyChangeEvent event) {
			String property = event.getProperty();
			Object newValue = event.getNewValue();
			Object oldValue = event.getOldValue();

			String newLabel = null;
			if (IWorkingSetManager.CHANGE_WORKING_SET_REMOVE.equals(property) && oldValue == workingSet) {
				newLabel = ""; //$NON-NLS-1$
				setWorkingSet(null);
			} else if (IWorkingSetManager.CHANGE_WORKING_SET_NAME_CHANGE.equals(property) && newValue == workingSet) {
				newLabel = workingSet.getLabel();
			} else if (IWorkingSetManager.CHANGE_WORKING_SET_CONTENT_CHANGE.equals(property) && newValue == workingSet) {
				if (workingSet.isAggregateWorkingSet() && workingSet.isEmpty()) {
					if (!emptyWorkingSet) {
						emptyWorkingSet = true;
						setWorkingSetFilter(null);
						newLabel = null;
					}
				} else {
					if (emptyWorkingSet) {
						emptyWorkingSet = false;
						setWorkingSetFilter(workingSet);
						newLabel = workingSet.getLabel();
					}
				}
			}
			if (viewer != null) {
				if (newLabel != null)
					viewer.getCommonNavigator().setWorkingSetLabel(newLabel);
				viewer.getFrameList().reset();
				viewer.refresh();
			}
		}

		public synchronized void listen() {
			if (!listening) {
				PlatformUI.getWorkbench().getWorkingSetManager().addPropertyChangeListener(managerChangeListener);
				listening = true;
			}
		}

		public synchronized void ignore() {
			if (listening) {
				PlatformUI.getWorkbench().getWorkingSetManager().removePropertyChangeListener(managerChangeListener);
				listening = false;
			}
		}
	}

	private IPropertyChangeListener filterChangeListener = new IPropertyChangeListener() {
		@Override
		public void propertyChange(PropertyChangeEvent event) {
			
			if (ignoreFilterChangeEvents)
				return;
			
			IWorkingSet newWorkingSet = (IWorkingSet) event.getNewValue();

			setWorkingSet(newWorkingSet);
			if (newWorkingSet != null) {
				if (!contentService.isActive(WorkingSetsContentProvider.EXTENSION_ID)) {
					contentService.getActivationService().activateExtensions(
							new String[] { WorkingSetsContentProvider.EXTENSION_ID }, false);
					contentService.getActivationService().persistExtensionActivations();
				}
				if (newWorkingSet.isAggregateWorkingSet()) {
					IAggregateWorkingSet agWs = (IAggregateWorkingSet) newWorkingSet;
					IWorkingSet[] comps = agWs.getComponents();
					if (comps.length > 1) {
						viewer.getCommonNavigator().setWorkingSetLabel(
								WorkbenchNavigatorMessages.WorkingSetActionProvider_multipleWorkingSets);
					} else if (comps.length > 0) {
						viewer.getCommonNavigator().setWorkingSetLabel(comps[0].getLabel());
					} else {
						viewer.getCommonNavigator().setWorkingSetLabel(null);
					}
				} else
					viewer.getCommonNavigator().setWorkingSetLabel(workingSet.getLabel());
			} else {
				viewer.getCommonNavigator().setWorkingSetLabel(null);
			}

			viewer.getFrameList().reset();
		}
	};

	private WorkingSetManagerListener managerChangeListener = new WorkingSetManagerListener();

	private IExtensionActivationListener activationListener = new IExtensionActivationListener() {

		private IWorkingSet savedWorkingSet;

		@Override
		public void onExtensionActivation(String aViewerId, String[] theNavigatorExtensionIds, boolean isActive) {

			for (int i = 0; i < theNavigatorExtensionIds.length; i++) {
				if (WorkingSetsContentProvider.EXTENSION_ID.equals(theNavigatorExtensionIds[i])) {
					if (isActive) {
						extensionStateModel = contentService.findStateModel(WorkingSetsContentProvider.EXTENSION_ID);
						workingSetRootModeActionGroup.setStateModel(extensionStateModel);
						extensionStateModel.addPropertyChangeListener(topLevelModeListener);

						if (savedWorkingSet != null) {
							setWorkingSet(savedWorkingSet);
						}
						managerChangeListener.listen();

					} else {
						savedWorkingSet = workingSet;
						setWorkingSet(null);
						viewer.getCommonNavigator().setWorkingSetLabel(null);
						managerChangeListener.ignore();
						workingSetRootModeActionGroup.setShowTopLevelWorkingSets(false);
						extensionStateModel.removePropertyChangeListener(topLevelModeListener);

					}
				}
			}
		}

	};

	@Override
	public void init(ICommonActionExtensionSite aSite) {
		viewer = (CommonViewer) aSite.getStructuredViewer();
		contentService = aSite.getContentService();
		filterService = (NavigatorFilterService) contentService.getFilterService();

		extensionStateModel = contentService.findStateModel(WorkingSetsContentProvider.EXTENSION_ID);

		workingSetActionGroup = new WorkingSetFilterActionGroup(aSite.getViewSite().getShell(), filterChangeListener);
		workingSetRootModeActionGroup = new WorkingSetRootModeActionGroup(viewer, extensionStateModel);

		topLevelModeListener = new IPropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				setWorkingSet(workingSet);
				viewer.getFrameList().reset();
			}
		};

		if (contentService.isActive(WorkingSetsContentProvider.EXTENSION_ID)) {
			managerChangeListener.listen();
			extensionStateModel.addPropertyChangeListener(topLevelModeListener);
		}

		contentService.getActivationService().addExtensionActivationListener(activationListener);
	}

	protected void initWorkingSetFilter(String workingSetName) {
		IWorkingSet workingSet = null;

		if (workingSetName != null && workingSetName.length() > 0) {
			IWorkingSetManager workingSetManager = PlatformUI.getWorkbench().getWorkingSetManager();
			workingSet = workingSetManager.getWorkingSet(workingSetName);
		} else if (PlatformUI.getPreferenceStore().getBoolean(
				IWorkbenchPreferenceConstants.USE_WINDOW_WORKING_SET_BY_DEFAULT)) {
			workingSet = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getAggregateWorkingSet();
		}

		if (workingSet != null) {
			setWorkingSet(workingSet);
		}
	}

	private void setWorkingSetFilter(IWorkingSet workingSet) {
		setWorkingSetFilter(workingSet, FIRST_TIME);
	}
	
	private static final boolean FIRST_TIME = true;
	
	private void setWorkingSetFilter(IWorkingSet workingSet, boolean firstTime) {
		ResourceWorkingSetFilter workingSetFilter = null;
		ViewerFilter[] filters = viewer.getFilters();
		for (int i = 0; i < filters.length; i++) {
			if (filters[i] instanceof ResourceWorkingSetFilter) {
				workingSetFilter = (ResourceWorkingSetFilter) filters[i];
				break;
			}
		}
		if (workingSetFilter == null) {
			if (firstTime) {
				filterService.addActiveFilterIds(new String[] { WORKING_SET_FILTER_ID });
				filterService.updateViewer();
				setWorkingSetFilter(workingSet, !FIRST_TIME);
				return;
			}
			WorkbenchNavigatorPlugin.log("Required filter " + WORKING_SET_FILTER_ID +  //$NON-NLS-1$
					" is not present. Working set support will not function correctly.",   //$NON-NLS-1$
				new Status(IStatus.ERROR, WorkbenchNavigatorPlugin.PLUGIN_ID, ""));  //$NON-NLS-1$
			return;
		}
		workingSetFilter.setWorkingSet(emptyWorkingSet ? null : workingSet);
	}

	protected void setWorkingSet(IWorkingSet workingSet) {
		this.workingSet = workingSet;
		emptyWorkingSet = workingSet != null && workingSet.isAggregateWorkingSet() && workingSet.isEmpty();

        ignoreFilterChangeEvents = true;
        try {
        	workingSetActionGroup.setWorkingSet(workingSet);
        } finally {
        	ignoreFilterChangeEvents = false;
       	}		
		
		if (viewer != null) {
			setWorkingSetFilter(workingSet);
			if (workingSet == null || emptyWorkingSet
					|| !extensionStateModel.getBooleanProperty(WorkingSetsContentProvider.SHOW_TOP_LEVEL_WORKING_SETS)) {
				if (viewer.getInput() != originalViewerInput) {
					viewer.setInput(originalViewerInput);
				} else {
					viewer.refresh();
				}
			} else {
				if (!workingSet.isAggregateWorkingSet()) {
					IWorkingSetManager workingSetManager = PlatformUI.getWorkbench().getWorkingSetManager();
					viewer.setInput(workingSetManager.createAggregateWorkingSet(
							"", "", new IWorkingSet[] { workingSet })); //$NON-NLS-1$ //$NON-NLS-2$
				} else {
					viewer.setInput(workingSet);
				}
			}
		}
	}

	@Override
	public void restoreState(final IMemento aMemento) {
		super.restoreState(aMemento);

		viewer.getControl().getShell().getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				boolean showWorkingSets = true;
				if (aMemento != null) {
					Integer showWorkingSetsInt = aMemento
							.getInteger(WorkingSetsContentProvider.SHOW_TOP_LEVEL_WORKING_SETS);
					showWorkingSets = showWorkingSetsInt == null || showWorkingSetsInt.intValue() == 1;
					extensionStateModel.setBooleanProperty(WorkingSetsContentProvider.SHOW_TOP_LEVEL_WORKING_SETS,
							showWorkingSets);
					workingSetRootModeActionGroup.setShowTopLevelWorkingSets(showWorkingSets);

					String lastWorkingSetName = aMemento.getString(TAG_CURRENT_WORKING_SET_NAME);
					initWorkingSetFilter(lastWorkingSetName);
				} else {
					showWorkingSets = false;

					extensionStateModel.setBooleanProperty(WorkingSetsContentProvider.SHOW_TOP_LEVEL_WORKING_SETS,
							showWorkingSets);
					workingSetRootModeActionGroup.setShowTopLevelWorkingSets(showWorkingSets);
				}
			}
		});
	}

	@Override
	public void saveState(IMemento aMemento) {
		super.saveState(aMemento);

		if (aMemento != null) {
			int showWorkingSets = extensionStateModel
					.getBooleanProperty(WorkingSetsContentProvider.SHOW_TOP_LEVEL_WORKING_SETS) ? 1 : 0;
			aMemento.putInteger(WorkingSetsContentProvider.SHOW_TOP_LEVEL_WORKING_SETS, showWorkingSets);

			if (workingSet != null) {
				aMemento.putString(TAG_CURRENT_WORKING_SET_NAME, workingSet.getName());
			}
		}

	}

	@Override
	public void fillActionBars(IActionBars actionBars) {
		if (!contributedToViewMenu) {
			try {
				super.fillActionBars(actionBars);
				workingSetActionGroup.fillActionBars(actionBars);
				if (workingSetRootModeActionGroup != null) {
					workingSetRootModeActionGroup.fillActionBars(actionBars);
				}
			} finally {
				contributedToViewMenu = true;
			}
		}
	}

	@Override
	public void dispose() {
		super.dispose();
		workingSetActionGroup.dispose();
		if (workingSetRootModeActionGroup != null) {
			workingSetRootModeActionGroup.dispose();
		}

		managerChangeListener.ignore();
		extensionStateModel.removePropertyChangeListener(topLevelModeListener);

		contentService.getActivationService().removeExtensionActivationListener(activationListener);
	}

	public IPropertyChangeListener getFilterChangeListener() {
		return filterChangeListener;
	}

}
