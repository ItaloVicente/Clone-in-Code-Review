package org.eclipse.ui.navigator;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.PerformanceStats;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.ISaveablePart;
import org.eclipse.ui.ISaveablesLifecycleListener;
import org.eclipse.ui.ISaveablesSource;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.Saveable;
import org.eclipse.ui.SaveablesLifecycleEvent;
import org.eclipse.ui.actions.ActionGroup;
import org.eclipse.ui.internal.navigator.CommonNavigatorActionGroup;
import org.eclipse.ui.internal.navigator.NavigatorContentService;
import org.eclipse.ui.internal.navigator.NavigatorPlugin;
import org.eclipse.ui.internal.navigator.NavigatorSafeRunnable;
import org.eclipse.ui.part.ISetSelectionTarget;
import org.eclipse.ui.part.IShowInSource;
import org.eclipse.ui.part.IShowInTarget;
import org.eclipse.ui.part.ShowInContext;
import org.eclipse.ui.part.ViewPart;

public class CommonNavigator extends ViewPart implements ISetSelectionTarget, ISaveablePart, ISaveablesSource, IShowInTarget {
 
	private static final String PERF_CREATE_PART_CONTROL= "org.eclipse.ui.navigator/perf/explorer/createPartControl"; //$NON-NLS-1$

	
	private static final Class INAVIGATOR_CONTENT_SERVICE = INavigatorContentService.class;
	private static final Class COMMON_VIEWER_CLASS = CommonViewer.class;
	private static final Class ISHOW_IN_SOURCE_CLASS = IShowInSource.class;
	private static final Class ISHOW_IN_TARGET_CLASS = IShowInTarget.class;
	
	private static final String HELP_CONTEXT =  NavigatorPlugin.PLUGIN_ID + ".common_navigator"; //$NON-NLS-1$

	public static final int IS_LINKING_ENABLED_PROPERTY = 0x10000;

	private CommonViewer commonViewer;

	private CommonNavigatorManager commonManager;

	private ActionGroup commonActionGroup;

	protected IMemento memento;

	private boolean isLinkingEnabled = false;

	private String LINKING_ENABLED = "CommonNavigator.LINKING_ENABLED"; //$NON-NLS-1$ 

	private LinkHelperService linkService;

	public CommonNavigator() {
		super();
	}

	@Override
	public void createPartControl(Composite aParent) {

		final PerformanceStats stats= PerformanceStats.getStats(PERF_CREATE_PART_CONTROL, this);
		stats.startRun();

		commonViewer = createCommonViewer(aParent);	
		commonViewer.setCommonNavigator(this);
		
		try {
			commonViewer.getControl().setRedraw(false);
			
	        INavigatorFilterService filterService = commonViewer
					.getNavigatorContentService().getFilterService();
			ViewerFilter[] visibleFilters = filterService.getVisibleFilters(true);
			for (int i = 0; i < visibleFilters.length; i++) {
				commonViewer.addFilter(visibleFilters[i]);
			}
	
			commonViewer.setSorter(new CommonViewerSorter());
	
			commonViewer.setInput(getInitialInput()); 
	
			getSite().setSelectionProvider(commonViewer);
	
			setPartName(getConfigurationElement().getAttribute("name")); //$NON-NLS-1$
		} finally { 
			commonViewer.getControl().setRedraw(true);
		}

        commonViewer.createFrameList();

		commonManager = createCommonManager();
		if (memento != null) {			
			commonViewer.getNavigatorContentService().restoreState(memento);
		}

		commonActionGroup = createCommonActionGroup();
		commonActionGroup.fillActionBars(getViewSite().getActionBars());
		
		ISaveablesLifecycleListener saveablesLifecycleListener = new ISaveablesLifecycleListener() {
			ISaveablesLifecycleListener siteSaveablesLifecycleListener = (ISaveablesLifecycleListener) getSite()
					.getService(ISaveablesLifecycleListener.class);

			@Override
			public void handleLifecycleEvent(SaveablesLifecycleEvent event) {
				if (event.getEventType() == SaveablesLifecycleEvent.DIRTY_CHANGED) {
					firePropertyChange(PROP_DIRTY);
				}
				siteSaveablesLifecycleListener.handleLifecycleEvent(event);
			}
		};
		commonViewer.getNavigatorContentService()
				.getSaveablesService().init(this, getCommonViewer(),
						saveablesLifecycleListener);
		
		commonViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				firePropertyChange(PROP_DIRTY);
			}});
		
		String helpContext = commonViewer.getNavigatorContentService().getViewerDescriptor().getHelpContext();
		if (helpContext == null)
			helpContext = HELP_CONTEXT;
		PlatformUI.getWorkbench().getHelpSystem().setHelp(commonViewer.getControl(), helpContext);
		
		stats.endRun();
	}

	public void updateTitle() {
		Object input = commonViewer.getInput();
		if (input == null) {
			setTitleToolTip(""); //$NON-NLS-1$
		} else {
			String inputToolTip = getFrameToolTipText(input);
			setTitleToolTip(inputToolTip);
		}
	}
	
	public String getFrameToolTipText(Object anElement) {
		if (commonViewer == null)
			return ""; //$NON-NLS-1$
		return ((ILabelProvider) commonViewer.getLabelProvider())
					.getText(anElement);
	}

	
	@Override
	public void dispose() {
		if (commonManager != null) {
			commonManager.dispose();
		}
		if (commonActionGroup != null) {
			commonActionGroup.dispose();
		}
		super.dispose();
	}

	@Override
	public void init(IViewSite aSite, IMemento aMemento)
			throws PartInitException {
		super.init(aSite, aMemento);
		memento = aMemento;
		if (memento != null) {
			Integer linkingEnabledInteger = memento.getInteger(LINKING_ENABLED);
			setLinkingEnabled(((linkingEnabledInteger != null) ? linkingEnabledInteger
					.intValue() == 1
					: false));
		}

	}

	@Override
	public void saveState(IMemento aMemento) {
		aMemento.putInteger(LINKING_ENABLED, (isLinkingEnabled) ? 1 : 0);
		super.saveState(aMemento);
		commonManager.saveState(aMemento);
		commonViewer.getNavigatorContentService().saveState(aMemento);
	}

	@Override
	public void setFocus() {
		if (commonViewer != null) {
			commonViewer.getTree().setFocus();
		}
	}

	@Override
	public void selectReveal(ISelection selection) {
		if (commonViewer != null) {
			commonViewer.setSelection(selection, true);
		}
	}

	public final void setLinkingEnabled(boolean toEnableLinking) {
		isLinkingEnabled = toEnableLinking;
		firePropertyChange(IS_LINKING_ENABLED_PROPERTY);
	}

	public final boolean isLinkingEnabled() {
		return isLinkingEnabled;
	}

	public CommonViewer getCommonViewer() {
		return commonViewer;
	}

	public INavigatorContentService getNavigatorContentService() {
		return getCommonViewer().getNavigatorContentService();
	}

	@Override
	public Object getAdapter(Class adapter) {
		if (adapter == COMMON_VIEWER_CLASS) {
			return getCommonViewer();
		} else if (adapter == INAVIGATOR_CONTENT_SERVICE) {
			return getCommonViewer().getNavigatorContentService();
		} else if (adapter == ISHOW_IN_TARGET_CLASS) {
			return this;
		} else if (adapter == ISHOW_IN_SOURCE_CLASS) {
            return getShowInSource();
        }
		return super.getAdapter(adapter);
	}

    private IShowInSource getShowInSource() {
        return new IShowInSource() {
            @Override
			public ShowInContext getShowInContext() {
                return new ShowInContext(getCommonViewer().getInput(), getCommonViewer().getSelection());
            }
        };
    }

	public NavigatorActionService getNavigatorActionService() {
		return commonManager.getNavigatorActionService();
	}

	protected CommonViewer createCommonViewer(Composite aParent) {
		CommonViewer aViewer = createCommonViewerObject(aParent);
		initListeners(aViewer);
		aViewer.getNavigatorContentService().restoreState(memento);
		return aViewer;
	}

	protected CommonViewer createCommonViewerObject(Composite aParent) {
		return new CommonViewer(getViewSite().getId(), aParent,
				SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
	}

	protected void initListeners(TreeViewer viewer) {

		viewer.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(final DoubleClickEvent event) {
				SafeRunner.run(new NavigatorSafeRunnable() {
					@Override
					public void run() throws Exception {
						handleDoubleClick(event);
					}
				});
			}
		});
	}

	protected void handleDoubleClick(DoubleClickEvent anEvent) {

		IAction openHandler = getViewSite().getActionBars().getGlobalActionHandler(ICommonActionConstants.OPEN);
		
		if(openHandler == null) {
			IStructuredSelection selection = (IStructuredSelection) anEvent
					.getSelection();
			Object element = selection.getFirstElement();
	
			TreeViewer viewer = getCommonViewer();
			if (viewer.isExpandable(element)) {
				viewer.setExpandedState(element, !viewer.getExpandedState(element));
			}
		}
	}

	protected CommonNavigatorManager createCommonManager() {
		return new CommonNavigatorManager(this, memento);
	}

	protected ActionGroup createCommonActionGroup() {
		return new CommonNavigatorActionGroup(this, commonViewer, getLinkHelperService());
	}

	protected Object getInitialInput() {
		return getSite().getPage().getInput();
	}

	@Override
	public Saveable[] getSaveables() {
		return getNavigatorContentService().getSaveablesService().getSaveables();
	}

	@Override
	public Saveable[] getActiveSaveables() {
		return getNavigatorContentService().getSaveablesService().getActiveSaveables();
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
	}

	@Override
	public void doSaveAs() {
	}

	@Override
	public boolean isDirty() {
		Saveable[] saveables = getSaveables();
		for (int i = 0; i < saveables.length; i++) {
			if(saveables[i].isDirty()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public boolean isSaveOnCloseNeeded() {
		return isDirty();
	}

	@Override
	public boolean show(ShowInContext context) {
		IStructuredSelection selection = getSelection(context);
		if (selection != null && !selection.isEmpty()) {
			selectReveal(selection);
			return true;
		} 
		return false;
	}

	private IStructuredSelection getSelection(ShowInContext context) {
		if (context == null)
			return StructuredSelection.EMPTY;
		ISelection selection = context.getSelection();
		if (selection != null && !selection.isEmpty() && selection instanceof IStructuredSelection)
			return (IStructuredSelection)selection;
		Object input = context.getInput();
		if (input instanceof IEditorInput) {
			LinkHelperService lhs = getLinkHelperService();
			return lhs.getSelectionFor((IEditorInput) input);
		}
		if (input != null) {
			return new StructuredSelection(input);
		}
		return StructuredSelection.EMPTY;
	}

	protected synchronized LinkHelperService getLinkHelperService() {
		if (linkService == null)
			linkService = new LinkHelperService((NavigatorContentService)getCommonViewer().getNavigatorContentService());
		return linkService;
	}
	
	protected IMemento getMemento() {
		return memento;
	}

	
	public void setRootMode(int mode) {
	}

	public int getRootMode() {
		return 0;
	}

	public void setWorkingSetLabel(String label) {
	}

	public String getWorkingSetLabel() {
		return null;
	}
	
	
}
