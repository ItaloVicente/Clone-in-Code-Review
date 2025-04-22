package org.eclipse.ui.navigator;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Menu;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.core.runtime.Status;

import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.util.OpenStrategy;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;

import org.eclipse.ui.IMemento;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.OpenAndLinkWithEditorHelper;
import org.eclipse.ui.actions.ActionContext;
import org.eclipse.ui.actions.RetargetAction;
import org.eclipse.ui.internal.navigator.CommonNavigatorMessages;
import org.eclipse.ui.internal.navigator.NavigatorPlugin;
import org.eclipse.ui.internal.navigator.NavigatorSafeRunnable;
import org.eclipse.ui.progress.UIJob;

public final class CommonNavigatorManager implements ISelectionChangedListener {

	private final CommonNavigator commonNavigator;

	private final INavigatorContentService contentService;

	private NavigatorActionService actionService;

	private final IDescriptionProvider commonDescriptionProvider;

	private final IStatusLineManager statusLineManager;

	private final ILabelProvider labelProvider;

	private UpdateActionBarsJob updateActionBars;
	
	private ISelectionChangedListener statusBarListener = new ISelectionChangedListener() {

		@Override
		public void selectionChanged(SelectionChangedEvent anEvent) {
			updateStatusBar(anEvent.getSelection());
		}
		
	};
	

	
	private class UpdateActionBarsJob extends UIJob {
		public UpdateActionBarsJob(String label) {
			super(label);
		}
		  
		@Override
		public IStatus runInUIThread(IProgressMonitor monitor) {
			SafeRunner.run(new NavigatorSafeRunnable() {
				@Override
				public void run() throws Exception {
					if(commonNavigator.getCommonViewer().getInput() != null) {
						IStructuredSelection selection = new StructuredSelection(commonNavigator.getCommonViewer().getInput());
						actionService.setContext(new ActionContext(selection));
						actionService.fillActionBars(commonNavigator.getViewSite().getActionBars());
					}
				}
			});
			return Status.OK_STATUS;
		}
	}

	public CommonNavigatorManager(CommonNavigator aNavigator) {
		this(aNavigator, null);
	}
	
	public CommonNavigatorManager(CommonNavigator aNavigator, IMemento aMemento) {
		super();
		commonNavigator = aNavigator;
		contentService = commonNavigator.getNavigatorContentService();
		statusLineManager = commonNavigator.getViewSite().getActionBars()
				.getStatusLineManager();
		commonDescriptionProvider = contentService
				.createCommonDescriptionProvider();
		labelProvider = (ILabelProvider) commonNavigator.getCommonViewer()
				.getLabelProvider();
	
		init(aMemento);
	}


	private void init(IMemento memento) {
		
		updateActionBars = new UpdateActionBarsJob(commonNavigator.getTitle());
		
		CommonViewer commonViewer = commonNavigator.getCommonViewer();
		commonViewer.addSelectionChangedListener(this);
		commonViewer.addPostSelectionChangedListener(statusBarListener);
		updateStatusBar(commonViewer.getSelection());

		ICommonViewerSite commonViewerSite = CommonViewerSiteFactory
				.createCommonViewerSite(commonNavigator.getViewSite());
		actionService = new NavigatorActionService(commonViewerSite,
				commonViewer, commonViewer.getNavigatorContentService());

		final RetargetAction openAction = new RetargetAction(
				ICommonActionConstants.OPEN,
				CommonNavigatorMessages.Open_action_label);
		commonNavigator.getViewSite().getPage().addPartListener(openAction);
		openAction.setActionDefinitionId(ICommonActionConstants.OPEN);

		new OpenAndLinkWithEditorHelper(commonNavigator.getCommonViewer()) {
			@Override
			protected void activate(ISelection selection) {
				final int currentMode = OpenStrategy.getOpenMethod();
				try {
					OpenStrategy.setOpenMethod(OpenStrategy.DOUBLE_CLICK);
					actionService.setContext(new ActionContext(commonNavigator.getCommonViewer().getSelection()));
					actionService.fillActionBars(commonNavigator.getViewSite().getActionBars());
					openAction.run();
				} finally {
					OpenStrategy.setOpenMethod(currentMode);
				}
			}

			@Override
			protected void linkToEditor(ISelection selection) {
			}

			@Override
			protected void open(ISelection selection, boolean activate) {
				actionService.setContext(new ActionContext(commonNavigator.getCommonViewer().getSelection()));
				actionService.fillActionBars(commonNavigator.getViewSite().getActionBars());
				openAction.run();
			}
			
		};

		if(memento != null)
			restoreState(memento);
		
		initContextMenu();
		initViewMenu();

	}

	public void dispose() {
		commonNavigator.getCommonViewer().removeSelectionChangedListener(this);
		commonNavigator.getCommonViewer().removeSelectionChangedListener(statusBarListener);
		actionService.dispose();
	}

	@Override
	public void selectionChanged(SelectionChangedEvent anEvent) {
		if (anEvent.getSelection() instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) anEvent
					.getSelection();
			actionService.setContext(new ActionContext(structuredSelection));
			actionService.fillActionBars(commonNavigator.getViewSite()
					.getActionBars());
		}
	}

	public void restoreState(IMemento aMemento) {
		actionService.restoreState(aMemento);
		 
	}

	public void saveState(IMemento aMemento) {
		actionService.saveState(aMemento);
	}

	protected void fillContextMenu(IMenuManager aMenuManager) {
		ISelection selection = commonNavigator.getCommonViewer().getSelection();
		actionService.setContext(new ActionContext(selection));
		actionService.fillContextMenu(aMenuManager);
	}

	protected void initContextMenu() {
		MenuManager menuMgr = new MenuManager(contentService
				.getViewerDescriptor().getPopupMenuId());
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {

			@Override
			public void menuAboutToShow(IMenuManager manager) {
				fillContextMenu(manager);
			}
		});
		TreeViewer commonViewer = commonNavigator.getCommonViewer();
		Menu menu = menuMgr.createContextMenu(commonViewer.getTree());

		commonViewer.getTree().setMenu(menu);

		actionService.prepareMenuForPlatformContributions(menuMgr,
				commonViewer, false);

	}

	protected void initViewMenu() {
		IMenuManager viewMenu = commonNavigator.getViewSite().getActionBars()
				.getMenuManager();
		viewMenu.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		viewMenu.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS
				+ "-end"));//$NON-NLS-1$
		
		updateActionBars.schedule(NavigatorPlugin.ACTION_BAR_DELAY);
		
	}

	protected void updateStatusBar(ISelection aSelection) {

		Image img = null;
		if (aSelection != null && !aSelection.isEmpty()
				&& aSelection instanceof IStructuredSelection) {
			img = labelProvider.getImage(((IStructuredSelection) aSelection)
					.getFirstElement());
		}

		statusLineManager.setMessage(img, commonDescriptionProvider
				.getDescription(aSelection));
	}

	public NavigatorActionService getNavigatorActionService() {
		return actionService;
	}

}
