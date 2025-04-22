package org.eclipse.ui.internal.dialogs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.commands.ActionHandler;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.preference.IPreferenceNode;
import org.eclipse.jface.preference.IPreferencePage;
import org.eclipse.jface.preference.PreferenceContentProvider;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceLabelProvider;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Sash;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ActiveShellExpression;
import org.eclipse.ui.IWorkbenchCommandConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.activities.WorkbenchActivityHelper;
import org.eclipse.ui.dialogs.FilteredTree;
import org.eclipse.ui.dialogs.PatternFilter;
import org.eclipse.ui.handlers.IHandlerActivation;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.internal.IWorkbenchGraphicConstants;
import org.eclipse.ui.internal.WorkbenchImages;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.internal.misc.StatusUtil;
import org.eclipse.ui.model.IContributionService;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;
import org.eclipse.ui.preferences.IWorkingCopyManager;
import org.eclipse.ui.preferences.WorkingCopyManager;
import org.eclipse.ui.statushandlers.StatusManager;
import org.osgi.service.prefs.BackingStoreException;


public abstract class FilteredPreferenceDialog extends PreferenceDialog
		implements IWorkbenchPreferenceContainer {

	private static final int PAGE_MULTIPLIER = 9;

	private static final int INCREMENT = 10;

	protected class PreferenceFilteredTree extends FilteredTree {
		private ViewerFilter viewerFilter;

		private String cachedTitle;

		PreferenceFilteredTree(Composite parent, int treeStyle,
				PatternFilter filter) {
			super(parent, treeStyle, filter, true);
		}

		protected void addFilter(ViewerFilter filter) {
			viewerFilter = filter;
			getViewer().addFilter(filter);

			if (filterText != null) {
				setFilterText(WorkbenchMessages.FilteredTree_FilterMessage);
				textChanged();
			}

			cachedTitle = getShell().getText();
			getShell().setText(
					NLS.bind(
							WorkbenchMessages.FilteredTree_FilteredDialogTitle,
							cachedTitle));
		}

		@Override
		protected void updateToolbar(boolean visible) {
			super.updateToolbar(viewerFilter != null || visible);
		}

		@Override
		protected void clearText() {
			setFilterText(""); //$NON-NLS-1$
			if (!locked && viewerFilter != null) {
				getViewer().removeFilter(viewerFilter);
				viewerFilter = null;
				getShell().setText(cachedTitle);
			}
			textChanged();
		}
	}

	protected PreferenceFilteredTree filteredTree;

	private Object pageData;

	IWorkingCopyManager workingCopyManager;

	private Collection updateJobs = new ArrayList();

	PreferencePageHistory history;

	private Sash sash;

	private IHandlerActivation showViewHandler;

	private boolean locked;

	public FilteredPreferenceDialog(Shell parentShell, PreferenceManager manager) {
		super(parentShell, manager);
		history = new PreferencePageHistory(this);
	}

	@Override
	protected IPreferenceNode findNodeMatching(String nodeId) {
		IPreferenceNode node = super.findNodeMatching(nodeId);
		if (WorkbenchActivityHelper.filterItem(node)) {
			return null;
		}
		return node;
	}

	@Override
	protected TreeViewer createTreeViewer(Composite parent) {
		int styleBits = SWT.SINGLE;
		TreeViewer tree;
		if (!hasAtMostOnePage()) {
			filteredTree= new PreferenceFilteredTree(parent, styleBits, new PreferencePatternFilter());
			GridData gd= new GridData(SWT.FILL, SWT.FILL, true, true);
			gd.horizontalIndent= IDialogConstants.HORIZONTAL_MARGIN;
			filteredTree.setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_LIST_BACKGROUND));

			tree= filteredTree.getViewer();
		} else
			tree= new TreeViewer(parent, styleBits);

		setContentAndLabelProviders(tree);
		tree.setInput(getPreferenceManager());


		tree.addFilter(new CapabilityFilter());

		tree.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				handleTreeSelectionChanged(event);
			}
		});

		super.addListeners(tree);
		return tree;
	}

	private boolean hasAtMostOnePage() {
		ITreeContentProvider contentProvider= new PreferenceContentProvider();
		try {
			Object[] children= contentProvider.getElements(getPreferenceManager());
			return children.length == 0 || children.length == 1 && !contentProvider.hasChildren(children[0]);
		} finally {
			contentProvider.dispose();
		}
	}

	protected void setContentAndLabelProviders(TreeViewer treeViewer) {
		if (hasAtMostOnePage()) {
			treeViewer.setLabelProvider(new PreferenceLabelProvider());
		} else {
			treeViewer.setLabelProvider(new PreferenceBoldLabelProvider(filteredTree));
		}
		IContributionService cs = PlatformUI
				.getWorkbench().getActiveWorkbenchWindow().getService(
						IContributionService.class);
		treeViewer.setComparator(cs.getComparatorFor(getContributionType()));
		treeViewer.setContentProvider(new PreferenceContentProvider());
	}

	protected String getContributionType() {
		return IContributionService.TYPE_PREFERENCE;
	}

	protected void handleTreeSelectionChanged(SelectionChangedEvent event) {
	}

	@Override
	protected Control createTreeAreaContents(Composite parent) {
		Composite leftArea = new Composite(parent, SWT.NONE);
		leftArea.setBackground(parent.getDisplay().getSystemColor(
				SWT.COLOR_LIST_BACKGROUND));
		leftArea.setFont(parent.getFont());
		GridLayout leftLayout = new GridLayout();
		leftLayout.numColumns = 1;
		leftLayout.marginHeight = 0;
		leftLayout.marginTop = IDialogConstants.VERTICAL_MARGIN;
		leftLayout.marginWidth = IDialogConstants.HORIZONTAL_MARGIN;
		leftLayout.horizontalSpacing = 0;
		leftLayout.verticalSpacing = 0;
		leftArea.setLayout(leftLayout);

		TreeViewer viewer = createTreeViewer(leftArea);
		setTreeViewer(viewer);

		updateTreeFont(JFaceResources.getDialogFont());
		GridData viewerData = new GridData(GridData.FILL_BOTH
				| GridData.GRAB_VERTICAL);
		viewer.getControl().getParent().setLayoutData(viewerData);

		layoutTreeAreaControl(leftArea);

		return leftArea;
	}

	public void showOnly(String[] filteredIds) {
		if (!hasAtMostOnePage()) {
			filteredTree.addFilter(new PreferenceNodeFilter(filteredIds));
		}
	}

	public void setPageData(Object pageData) {
		this.pageData = pageData;
	}

	@Override
	protected void createPage(IPreferenceNode node) {

		super.createPage(node);
		if (this.pageData == null) {
			return;
		}
		IPreferencePage page = node.getPage();
		if (page instanceof PreferencePage) {
			((PreferencePage) page).applyData(this.pageData);
		}

	}

	@Override
	public IPreferencePage getCurrentPage() {
		return super.getCurrentPage();
	}

	@Override
	public boolean openPage(String pageId, Object data) {
		setPageData(data);
		setCurrentPageId(pageId);
		IPreferencePage page = getCurrentPage();
		if (page instanceof PreferencePage) {
			((PreferencePage) page).applyData(data);
		}
		return true;
	}

	public final void setCurrentPageId(final String preferencePageId) {
		final IPreferenceNode node = findNodeMatching(preferencePageId);
		if (node != null) {
			getTreeViewer().setSelection(new StructuredSelection(node));
			showPage(node);
		}
	}

	@Override
	public IWorkingCopyManager getWorkingCopyManager() {
		if (workingCopyManager == null) {
			workingCopyManager = new WorkingCopyManager();
		}
		return workingCopyManager;
	}

	@Override
	protected void okPressed() {
		super.okPressed();

		if (getReturnCode() == FAILED) {
			return;
		}

		if (workingCopyManager != null) {
			try {
				workingCopyManager.applyChanges();
			} catch (BackingStoreException e) {
				String msg = e.getMessage();
				if (msg == null) {
					msg = WorkbenchMessages.FilteredPreferenceDialog_PreferenceSaveFailed;
				}
				StatusUtil
						.handleStatus(
								WorkbenchMessages.PreferencesExportDialog_ErrorDialogTitle
										+ ": " + msg, e, StatusManager.SHOW, //$NON-NLS-1$
								getShell());
			}
		}

		Iterator updateIterator = updateJobs.iterator();
		while (updateIterator.hasNext()) {
			((Job) updateIterator.next()).schedule();
		}
	}

	@Override
	public void registerUpdateJob(Job job) {
		updateJobs.add(job);
	}

	Control getContainerToolBar(Composite composite) {

		final ToolBarManager historyManager = new ToolBarManager(SWT.HORIZONTAL
				| SWT.FLAT);
		historyManager.createControl(composite);

		history.createHistoryControls(historyManager.getControl(),
				historyManager);

		Action popupMenuAction = new Action() {
			@Override
			public ImageDescriptor getImageDescriptor() {
				return WorkbenchImages
						.getImageDescriptor(IWorkbenchGraphicConstants.IMG_LCL_VIEW_MENU);
			}

			@Override
			public void run() {
				MenuManager manager = new MenuManager();
				manager.add(new Action() {
					@Override
					public void run() {
						
						sash.addFocusListener(new FocusAdapter() {
							@Override
							public void focusGained(FocusEvent e) {
								sash.setBackground(sash.getDisplay()
										.getSystemColor(
												SWT.COLOR_LIST_SELECTION));
							}

							@Override
							public void focusLost(FocusEvent e) {
								sash.setBackground(sash.getDisplay()
										.getSystemColor(SWT.COLOR_LIST_BACKGROUND));
							}
						});
						sash.setFocus();
					}

					@Override
					public String getText() {
						return WorkbenchMessages.FilteredPreferenceDialog_Resize;
					}
				});
				manager.add(new Action() {
					@Override
					public void run() {
						activeKeyScrolling();
					}

					@Override
					public String getText() {
						return WorkbenchMessages.FilteredPreferenceDialog_Key_Scrolling;
					}
				});
				Menu menu = manager.createContextMenu(getShell());
				Rectangle bounds = historyManager.getControl().getBounds();
				Point topLeft = new Point(bounds.x + bounds.width, bounds.y + bounds.height);
				topLeft = historyManager.getControl().toDisplay(topLeft);
				menu.setLocation(topLeft.x, topLeft.y);
				menu.setVisible(true);
			}
		};
		popupMenuAction.setToolTipText(WorkbenchMessages.FilteredPreferenceDialog_FilterToolTip);
		historyManager.add(popupMenuAction);
		IHandlerService service = PlatformUI.getWorkbench()
				.getService(IHandlerService.class);
		showViewHandler = service
				.activateHandler(
						IWorkbenchCommandConstants.WINDOW_SHOW_VIEW_MENU,
						new ActionHandler(popupMenuAction),
						new ActiveShellExpression(getShell()));

		historyManager.update(false);

		return historyManager.getControl();
	}
	
	private boolean keyScrollingEnabled = false;
	private Listener keyScrollingFilter = null;

	void activeKeyScrolling() {
		if (keyScrollingFilter == null) {
			Composite pageParent = getPageContainer().getParent();
			if (!(pageParent instanceof ScrolledComposite)) {
				return;
			}
			final ScrolledComposite sc = (ScrolledComposite) pageParent;
			keyScrollingFilter = new Listener() {
				@Override
				public void handleEvent(Event event) {
					if (!keyScrollingEnabled || sc.isDisposed()) {
						return;
					}
					switch (event.keyCode) {
					case SWT.ARROW_DOWN:
						sc.setOrigin(sc.getOrigin().x, sc.getOrigin().y
								+ INCREMENT);
						break;
					case SWT.ARROW_UP:
						sc.setOrigin(sc.getOrigin().x, sc.getOrigin().y
								- INCREMENT);
						break;
					case SWT.ARROW_LEFT:
						sc.setOrigin(sc.getOrigin().x - INCREMENT, sc
								.getOrigin().y);
						break;
					case SWT.ARROW_RIGHT:
						sc.setOrigin(sc.getOrigin().x + INCREMENT, sc
								.getOrigin().y);
						break;
					case SWT.PAGE_DOWN:
						sc.setOrigin(sc.getOrigin().x, sc.getOrigin().y
								+ PAGE_MULTIPLIER * INCREMENT);
						break;
					case SWT.PAGE_UP:
						sc.setOrigin(sc.getOrigin().x, sc.getOrigin().y
								- PAGE_MULTIPLIER * INCREMENT);
						break;
					case SWT.HOME:
						sc.setOrigin(0, 0);
						break;
					case SWT.END:
						sc.setOrigin(0, sc.getSize().y);
						break;
					default:
						keyScrollingEnabled = false;
					}
					event.type = SWT.None;
					event.doit = false;
				}
			};
			Display display = PlatformUI.getWorkbench().getDisplay();
			display.addFilter(SWT.KeyDown, keyScrollingFilter);
			display.addFilter(SWT.Traverse, keyScrollingFilter);
			sc.addDisposeListener(new DisposeListener() {
				@Override
				public void widgetDisposed(DisposeEvent e) {
					removeKeyScrolling();
				}
			});
		}
		keyScrollingEnabled = true;
	}
	
	void removeKeyScrolling() {
		if (keyScrollingFilter != null) {
			keyScrollingEnabled = false;
			Display display = PlatformUI.getWorkbench().getDisplay();
			if (display != null) {
				display.removeFilter(SWT.KeyDown, keyScrollingFilter);
				display.removeFilter(SWT.Traverse, keyScrollingFilter);
			}
			keyScrollingFilter = null;
		}
	}

	@Override
	protected boolean showPage(IPreferenceNode node) {
		final boolean success = super.showPage(node);
		if (success) {
			history.addHistoryEntry(new PreferenceHistoryEntry(node.getId(),
					node.getLabelText(), null));
		}
		return success;
	}

	@Override
	public boolean close() {
		if (showViewHandler != null) {
			IHandlerService service = PlatformUI
					.getWorkbench().getService(IHandlerService.class);
			service.deactivateHandler(showViewHandler);
			showViewHandler.getHandler().dispose();
			showViewHandler = null;
		}
		removeKeyScrolling();
		history.dispose();
		return super.close();
	}

	@Override
	protected Composite createTitleArea(Composite parent) {

		GridLayout parentLayout = (GridLayout) parent.getLayout();
		parentLayout.numColumns = 2;
		parentLayout.marginHeight = 0;
		parentLayout.marginTop = IDialogConstants.VERTICAL_MARGIN;
		parent.setLayout(parentLayout);

		Composite titleComposite = super.createTitleArea(parent);

		Composite toolbarArea = new Composite(parent, SWT.NONE);
		GridLayout toolbarLayout = new GridLayout();
		toolbarLayout.marginHeight = 0;
		toolbarLayout.verticalSpacing = 0;
		toolbarArea.setLayout(toolbarLayout);
		toolbarArea.setLayoutData(new GridData(SWT.END, SWT.FILL, false, true));
		Control topBar = getContainerToolBar(toolbarArea);
		topBar.setLayoutData(new GridData(SWT.END, SWT.FILL, false, true));

		return titleComposite;
	}

	@Override
	protected void selectSavedItem() {
		getTreeViewer().setInput(getPreferenceManager());
		super.selectSavedItem();
		if (getTreeViewer().getTree().getItemCount() > 1) {
			Text filterText = filteredTree.getFilterControl();
			if (filterText != null) {
				filterText.setFocus();
			}
		}
	}

	@Override
	protected void updateTreeFont(Font dialogFont) {
		if (hasAtMostOnePage()) {
			Composite composite= getTreeViewer().getTree();
			applyDialogFont(composite, dialogFont);
			composite.layout(true);
		} else {
			applyDialogFont(filteredTree, dialogFont);
			filteredTree.layout(true);
		}
	}

	private void applyDialogFont(Control control, Font dialogFont) {
		control.setFont(dialogFont);
		if (control instanceof Composite) {
			Control[] children = ((Composite) control).getChildren();
			for (int i = 0; i < children.length; i++) {
				applyDialogFont(children[i], dialogFont);
			}
		}
	}
	
	@Override
	protected Sash createSash(Composite composite, Control rightControl) {
		sash = super.createSash(composite, rightControl);
		return sash;
	}

	public void setLocked(boolean b) {
		this.locked = b;
	}
}
