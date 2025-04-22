package org.eclipse.ui.internal;

import org.eclipse.core.expressions.Expression;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.ContributionManager;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IContributionManager;
import org.eclipse.jface.action.IContributionManagerOverrides;
import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.SubContributionManager;
import org.eclipse.jface.action.SubCoolBarManager;
import org.eclipse.jface.action.SubMenuManager;
import org.eclipse.jface.action.SubStatusLineManager;
import org.eclipse.jface.action.SubToolBarManager;
import org.eclipse.jface.internal.provisional.action.IToolBarContributionItem;
import org.eclipse.jface.internal.provisional.action.ToolBarContributionItem2;
import org.eclipse.jface.internal.provisional.action.ToolBarManager2;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IActionBars2;
import org.eclipse.ui.IEditorActionBarContributor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.SubActionBars2;
import org.eclipse.ui.actions.RetargetAction;
import org.eclipse.ui.internal.expressions.LegacyEditorActionBarExpression;
import org.eclipse.ui.internal.misc.Policy;
import org.eclipse.ui.services.IServiceLocator;

public class EditorActionBars extends SubActionBars2 {

	private class Overrides implements IContributionManagerOverrides {

		@Override
		public Integer getAccelerator(IContributionItem item) {
			return null;
		}

		@Override
		public String getAcceleratorText(IContributionItem item) {
			return null;
		}

		@Override
		public Boolean getEnabled(IContributionItem item) {
			if (((item instanceof ActionContributionItem) && (((ActionContributionItem) item)
					.getAction() instanceof RetargetAction))
					|| enabledAllowed) {
				return null;
			} else {
				return Boolean.FALSE;
			}
		}

		@Override
		public String getText(IContributionItem item) {
			return null;
		}

		@Override
		public Boolean getVisible(IContributionItem item) {
			return null;
		}
	}

	private static final boolean DEBUG_TOOLBAR_DISPOSAL = Policy.DEBUG_TOOLBAR_DISPOSAL;

	private IToolBarManager coolItemToolBarMgr = null;

	private IEditorActionBarContributor editorContributor;

	private boolean enabledAllowed = false;

	private IEditorActionBarContributor extensionContributor;

	private int refCount;

	private IToolBarContributionItem toolBarContributionItem = null;

	private String type;

	private WorkbenchPage page;

	public EditorActionBars(WorkbenchPage page, final IServiceLocator serviceLocator, String type) {
		super((IActionBars2) page.getActionBars(), serviceLocator);
		this.page = page;
		this.type = type;
	}

	public WorkbenchPage getPage() {
		return page;
	}

	@Override
	public void activate(boolean forceVisibility) {
		setActive(true, forceVisibility);
	}

	public void addRef() {
		++refCount;
	}

	@Override
	protected SubMenuManager createSubMenuManager(IMenuManager parent) {
		return new EditorMenuManager(parent);
	}

	@Override
	protected SubToolBarManager createSubToolBarManager(IToolBarManager parent) {
		return null;
	}

	@Override
	public void deactivate(boolean forceVisibility) {
		setActive(false, forceVisibility);
	}

	@Override
	public void dispose() {
		super.dispose();
		if (editorContributor != null) {
			editorContributor.dispose();
		}
		if (extensionContributor != null) {
			extensionContributor.dispose();
		}

		if (toolBarContributionItem != null) {
			ICoolBarManager coolBarManager = getCoolBarManager();
			if (coolBarManager instanceof SubContributionManager) {
				SubContributionManager subManager = (SubContributionManager) coolBarManager;
				IContributionManager manager = subManager.getParent();
				if (manager instanceof CoolBarToTrimManager) {
					CoolBarToTrimManager trimManager = (CoolBarToTrimManager) manager;
					trimManager.remove(toolBarContributionItem);
				} else if (manager instanceof ContributionManager) {
					final IContributionItem replacementItem = new PlaceholderContributionItem(
							toolBarContributionItem);
					boolean succeeded = ((ContributionManager) manager).replaceItem(replacementItem
							.getId(), replacementItem);
					if (!succeeded && DEBUG_TOOLBAR_DISPOSAL) {
						System.out.println("FAILURE WHILE DISPOSING EditorActionBars"); //$NON-NLS-1$
						System.out
								.println("Could not replace " + replacementItem.getId() + " in the contribution manager."); //$NON-NLS-1$ //$NON-NLS-2$
					}
				} else if (DEBUG_TOOLBAR_DISPOSAL) {
					System.out.println("FAILURE WHILE DISPOSING EditorActionBars"); //$NON-NLS-1$
					System.out.println("The manager was not a ContributionManager."); //$NON-NLS-1$
					System.out.println("It was a " + manager.getClass().getName()); //$NON-NLS-1$
				}
			} else if (DEBUG_TOOLBAR_DISPOSAL) {
				System.out.println("FAILURE WHILE DISPOSING EditorActionBars"); //$NON-NLS-1$
				System.out.println("The coolBarManager was not a SubContributionManager"); //$NON-NLS-1$
				System.out.println("It was a " + coolBarManager.getClass().getName()); //$NON-NLS-1$
			}

			toolBarContributionItem.dispose();
		}
		toolBarContributionItem = null;
		if (coolItemToolBarMgr != null) {
			coolItemToolBarMgr.removeAll();
		}
		coolItemToolBarMgr = null;
		editorHandlerExpression = null;
	}

	public IEditorActionBarContributor getEditorContributor() {
		return editorContributor;
	}

	public String getEditorType() {
		return type;
	}

	public IEditorActionBarContributor getExtensionContributor() {
		return extensionContributor;
	}

	public int getRef() {
		return refCount;
	}

	@Override
	public IToolBarManager getToolBarManager() {

		ICoolBarManager coolBarManager = getCastedParent().getCoolBarManager();
		if (coolBarManager == null) {
			return null;
		}

		if (coolBarManager.find(IWorkbenchActionConstants.GROUP_EDITOR) == null) {
			coolBarManager.add(new GroupMarker(IWorkbenchActionConstants.GROUP_EDITOR));
		}
		if (toolBarContributionItem == null) {
			IContributionItem foundItem = coolBarManager.find(type);
			if ((foundItem instanceof IToolBarContributionItem)) {
				toolBarContributionItem = (IToolBarContributionItem) foundItem;
				coolItemToolBarMgr = toolBarContributionItem.getToolBarManager();
				if (coolItemToolBarMgr == null) {
					coolItemToolBarMgr = new ToolBarManager2(SWT.FLAT);
					toolBarContributionItem = new ToolBarContributionItem2(coolItemToolBarMgr, type);
					coolBarManager.prependToGroup(IWorkbenchActionConstants.GROUP_EDITOR,
							toolBarContributionItem);
				}
			} else {
				coolItemToolBarMgr = new ToolBarManager2(SWT.FLAT);
				if ((coolBarManager instanceof ContributionManager)
						&& (foundItem instanceof PlaceholderContributionItem)) {
					PlaceholderContributionItem placeholder = (PlaceholderContributionItem) foundItem;
					toolBarContributionItem = createToolBarContributionItem(coolItemToolBarMgr,
							placeholder);
					((ContributionManager) coolBarManager).replaceItem(type,
							toolBarContributionItem);
				} else {
					toolBarContributionItem = new ToolBarContributionItem2(coolItemToolBarMgr, type);
					coolBarManager.prependToGroup(IWorkbenchActionConstants.GROUP_EDITOR,
							toolBarContributionItem);
				}
			}
			((ContributionManager) coolItemToolBarMgr).setOverrides(new Overrides());
			toolBarContributionItem.setVisible(getActive());
			coolItemToolBarMgr.markDirty();
		}

		return coolItemToolBarMgr;
	}

	IToolBarContributionItem createToolBarContributionItem(final IToolBarManager manager,
			PlaceholderContributionItem item) {
		IToolBarContributionItem toolBarContributionItem = new ToolBarContributionItem2(manager,
				item.getId());
		toolBarContributionItem.setCurrentHeight(item.getHeight());
		toolBarContributionItem.setCurrentWidth(item.getWidth());
		toolBarContributionItem.setMinimumItemsToShow(item.getMinimumItemsToShow());
		toolBarContributionItem.setUseChevron(item.getUseChevron());
		return toolBarContributionItem;
	}

	private boolean isVisible() {
		if (toolBarContributionItem != null) {
			return toolBarContributionItem.isVisible();
		}
		return false;
	}

	@Override
	public void partChanged(IWorkbenchPart part) {
		super.partChanged(part);
		if (part instanceof IEditorPart) {
			IEditorPart editor = (IEditorPart) part;
			if (editorContributor != null) {
				editorContributor.setActiveEditor(editor);
			}
			if (extensionContributor != null) {
				extensionContributor.setActiveEditor(editor);
			}
		}
	}

	public void removeRef() {
		--refCount;
	}

	private void setActive(boolean set, boolean forceVisibility) {
		basicSetActive(set);
		if (isSubMenuManagerCreated()) {
			((EditorMenuManager) getMenuManager()).setVisible(set, forceVisibility);
		}

		if (isSubStatusLineManagerCreated()) {
			((SubStatusLineManager) getStatusLineManager()).setVisible(set);
		}

		setVisible(set, forceVisibility);
	}

	public void setEditorContributor(IEditorActionBarContributor c) {
		editorContributor = c;
	}

	private void setEnabledAllowed(boolean enabledAllowed) {
		if (this.enabledAllowed == enabledAllowed) {
			return;
		}
		this.enabledAllowed = enabledAllowed;
		if (coolItemToolBarMgr != null) {
			IContributionItem[] items = coolItemToolBarMgr.getItems();
			for (int i = 0; i < items.length; i++) {
				IContributionItem item = items[i];
				if (item != null) {
					item.update(IContributionManagerOverrides.P_ENABLED);
				}
			}
		}
	}

	public void setExtensionContributor(IEditorActionBarContributor c) {
		extensionContributor = c;
	}

	private void setVisible(boolean visible) {
		if (toolBarContributionItem != null) {
			toolBarContributionItem.setVisible(visible);
			if (toolBarContributionItem.getParent() != null) {
				toolBarContributionItem.getParent().markDirty();
			}
		}
	}

	private void setVisible(boolean visible, boolean forceVisibility) {
		if (visible) {
			setEnabledAllowed(true);
			if (!isVisible()) {
				setVisible(true);
			}
		} else {
			if (forceVisibility) {
				setVisible(false);
			} else {
				setEnabledAllowed(false);
			}
		}

		ICoolBarManager coolBarManager = getCastedParent().getCoolBarManager();
		if ((coolItemToolBarMgr != null) && (coolBarManager != null)) {
			IContributionItem[] items = coolItemToolBarMgr.getItems();
			for (int i = 0; i < items.length; i++) {
				IContributionItem item = items[i];
				item.setVisible(visible || !forceVisibility);
				coolItemToolBarMgr.markDirty();
				if (!coolBarManager.isDirty()) {
					coolBarManager.markDirty();
				}
			}
			coolItemToolBarMgr.update(false);
			if (toolBarContributionItem != null) {
				toolBarContributionItem.setVisible(visible || !forceVisibility);
			}
			if (getCoolBarManager() != null) {
				((SubCoolBarManager) getCoolBarManager()).setVisible(visible || !forceVisibility);
			}
		}
	}

	private LegacyEditorActionBarExpression editorHandlerExpression = null;

	public Expression getHandlerExpression() {
		if (editorHandlerExpression == null) {
			editorHandlerExpression = new LegacyEditorActionBarExpression(type);
		}
		return editorHandlerExpression;
	}
}
