package org.eclipse.ui.internal.progress;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.IWorkbenchHelpContextIds;
import org.eclipse.ui.internal.WorkbenchImages;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.preferences.ViewPreferencesAction;

public class ProgressView extends ViewPart {

	DetailedProgressViewer viewer;

	Action cancelAction;

	Action clearAllAction;


	@Override
	public void createPartControl(Composite parent) {
		viewer = new DetailedProgressViewer(parent, SWT.MULTI | SWT.H_SCROLL);
		viewer.setComparator(ProgressManagerUtil.getProgressViewerComparator());

		viewer.getControl().setLayoutData(
				new GridData(SWT.FILL, SWT.FILL, true, true));

		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent,
				IWorkbenchHelpContextIds.RESPONSIVE_UI);
		
		initContentProvider();
		createClearAllAction();
		createCancelAction();
		initContextMenu();
		initPulldownMenu();
		initToolBar();
		getSite().setSelectionProvider(viewer);
	}

	@Override
	public void setFocus() {
		if (viewer != null) {
			viewer.setFocus();
		}
	}

	protected void initContentProvider() {
		ProgressViewerContentProvider provider = new ProgressViewerContentProvider(viewer, true ,true);
		viewer.setContentProvider(provider);
		viewer.setInput(ProgressManager.getInstance());
	}

	private void initContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu"); //$NON-NLS-1$
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		menuMgr.add(cancelAction);
		menuMgr.addMenuListener(new IMenuListener() {
			@Override
			public void menuAboutToShow(IMenuManager manager) {
				JobInfo info = getSelectedInfo();
				if (info == null) {
					return;
				}
			}
		});
		menuMgr.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		getSite().registerContextMenu(menuMgr, viewer);
		viewer.getControl().setMenu(menu);
	}

	private void initPulldownMenu() {
		IMenuManager menuMgr = getViewSite().getActionBars().getMenuManager();
		menuMgr.add(clearAllAction);
		menuMgr.add(new ViewPreferencesAction() {
			@Override
			public void openViewPreferencesDialog() {
				new JobsViewPreferenceDialog(viewer.getControl().getShell())
						.open();

			}
		});

	}

	private void initToolBar() {
		IActionBars bars = getViewSite().getActionBars();
		IToolBarManager tm = bars.getToolBarManager();
		tm.add(clearAllAction);
	}

	private IStructuredSelection getSelection() {
		ISelectionProvider provider = getSite().getSelectionProvider();
		if (provider == null) {
			return null;
		}
		ISelection currentSelection = provider.getSelection();
		if (currentSelection instanceof IStructuredSelection) {
			return (IStructuredSelection) currentSelection;
		}
		return null;
	}

	JobInfo getSelectedInfo() {
		IStructuredSelection selection = getSelection();
		if (selection != null && selection.size() == 1) {
			JobTreeElement element = (JobTreeElement) selection
					.getFirstElement();
			if (element.isJobInfo()) {
				return (JobInfo) element;
			}
		}
		return null;
	}

	private void createCancelAction() {
		cancelAction = new Action(ProgressMessages.ProgressView_CancelAction) {
			@Override
			public void run() {
				viewer.cancelSelection();
			}
		};

	}

	private void createClearAllAction() {
		clearAllAction = new Action(
				ProgressMessages.ProgressView_ClearAllAction) {
			@Override
			public void run() {
				FinishedJobs.getInstance().clearAll();
			}
		};
		clearAllAction
				.setToolTipText(ProgressMessages.NewProgressView_RemoveAllJobsToolTip);
		ImageDescriptor id = WorkbenchImages
				.getWorkbenchImageDescriptor("/elcl16/progress_remall.png"); //$NON-NLS-1$
		if (id != null) {
			clearAllAction.setImageDescriptor(id);
		}
		id = WorkbenchImages
				.getWorkbenchImageDescriptor("/dlcl16/progress_remall.png"); //$NON-NLS-1$
		if (id != null) {
			clearAllAction.setDisabledImageDescriptor(id);
		}
	}

	public DetailedProgressViewer getViewer() {
		return viewer;
	}

}
