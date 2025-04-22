package org.eclipse.ui.internal.dialogs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.osgi.util.NLS;

import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.ToolBar;

import org.eclipse.core.commands.IHandler;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.commands.ActionHandler;

import org.eclipse.ui.ActiveShellExpression;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchCommandConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.IHandlerActivation;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.internal.WorkbenchPlugin;

class PreferencePageHistory {

	private ToolBarManager historyToolbar;

	private List history = new ArrayList();

	private int historyIndex = -1;

	private final FilteredPreferenceDialog dialog;

	private Set activations = new HashSet();

	public PreferencePageHistory(FilteredPreferenceDialog dialog) {
		this.dialog = dialog;
	}

	private PreferenceHistoryEntry getHistoryEntry(int index) {
		if (index >= 0 && index < history.size()) {
			return (PreferenceHistoryEntry) history.get(index);
		}
		return null;
	}

	public void addHistoryEntry(PreferenceHistoryEntry entry) {
		if (historyIndex == -1 || !history.get(historyIndex).equals(entry)) {
			history.subList(historyIndex + 1, history.size()).clear();
			history.add(entry);
			historyIndex++;
			updateHistoryControls();
		}
	}

	private void jumpToHistory(int index) {
		if (index >= 0 && index < history.size()) {
			historyIndex = index;
			dialog.setCurrentPageId(getHistoryEntry(index).getId());
		}
		updateHistoryControls();
	}

	private void updateHistoryControls() {
		historyToolbar.update(false);
		IContributionItem[] items = historyToolbar.getItems();
		for (int i = 0; i < items.length; i++) {
			items[i].update(IAction.ENABLED);
			items[i].update(IAction.TOOL_TIP_TEXT);
		}
	}

	public ToolBar createHistoryControls(ToolBar historyBar,
			ToolBarManager manager) {

		historyToolbar = manager;
		abstract class HistoryNavigationAction extends Action implements
				IMenuCreator {
			private Menu lastMenu;

			protected final static int MAX_ENTRIES = 5;

			HistoryNavigationAction() {
				super("", IAction.AS_DROP_DOWN_MENU); //$NON-NLS-1$
			}

			@Override
			public IMenuCreator getMenuCreator() {
				return this;
			}

			@Override
			public void dispose() {
				if (lastMenu != null) {
					lastMenu.dispose();
					lastMenu = null;
				}
			}

			@Override
			public Menu getMenu(Control parent) {
				if (lastMenu != null) {
					lastMenu.dispose();
				}
				lastMenu = new Menu(parent);
				createEntries(lastMenu);
				return lastMenu;

			}

			@Override
			public Menu getMenu(Menu parent) {
				return null;
			}

			protected void addActionToMenu(Menu parent, IAction action) {
				ActionContributionItem item = new ActionContributionItem(action);
				item.fill(parent, -1);
			}

			protected abstract void createEntries(Menu menu);
		}

		class HistoryItemAction extends Action {

			private final int index;

			HistoryItemAction(int index, String label) {
				super(label, IAction.AS_PUSH_BUTTON);
				this.index = index;
			}

			@Override
			public void run() {
				jumpToHistory(index);
			}
		}

		HistoryNavigationAction backward = new HistoryNavigationAction() {
			@Override
			public void run() {
				jumpToHistory(historyIndex - 1);
			}

			@Override
			public boolean isEnabled() {
				boolean enabled = historyIndex > 0;
				if (enabled) {
					setToolTipText(NLS.bind(WorkbenchMessages.NavigationHistoryAction_backward_toolTipName,getHistoryEntry(historyIndex - 1).getLabel() ));
				}
				return enabled;
			}

			@Override
			protected void createEntries(Menu menu) {
				int limit = Math.max(0, historyIndex - MAX_ENTRIES);
				for (int i = historyIndex - 1; i >= limit; i--) {
					IAction action = new HistoryItemAction(i,
							getHistoryEntry(i).getLabel());
					addActionToMenu(menu, action);
				}
			}
		};
		backward.setText(WorkbenchMessages.NavigationHistoryAction_backward_text);
		backward
				.setActionDefinitionId(IWorkbenchCommandConstants.NAVIGATE_BACKWARD_HISTORY);
		backward.setImageDescriptor(WorkbenchPlugin.getDefault()
				.getSharedImages().getImageDescriptor(
						ISharedImages.IMG_TOOL_BACK));
		backward.setDisabledImageDescriptor(WorkbenchPlugin.getDefault()
				.getSharedImages().getImageDescriptor(
						ISharedImages.IMG_TOOL_BACK_DISABLED));
		registerKeybindings(backward);
		historyToolbar.add(backward);

		HistoryNavigationAction forward = new HistoryNavigationAction() {
			@Override
			public void run() {
				jumpToHistory(historyIndex + 1);
			}

			@Override
			public boolean isEnabled() {
				boolean enabled = historyIndex < history.size() - 1;
				if (enabled) {
					setToolTipText(NLS.bind(WorkbenchMessages.NavigationHistoryAction_forward_toolTipName, getHistoryEntry(historyIndex + 1).getLabel() ));
				}
				return enabled;
			}

			@Override
			protected void createEntries(Menu menu) {
				int limit = Math.min(history.size(), historyIndex + MAX_ENTRIES
						+ 1);
				for (int i = historyIndex + 1; i < limit; i++) {
					IAction action = new HistoryItemAction(i,
							getHistoryEntry(i).getLabel());
					addActionToMenu(menu, action);
				}
			}
		};
		forward.setText(WorkbenchMessages.NavigationHistoryAction_forward_text);
		forward.setActionDefinitionId(IWorkbenchCommandConstants.NAVIGATE_FORWARD_HISTORY);
		forward.setImageDescriptor(WorkbenchPlugin.getDefault()
				.getSharedImages().getImageDescriptor(
						ISharedImages.IMG_TOOL_FORWARD));
		forward.setDisabledImageDescriptor(WorkbenchPlugin.getDefault()
				.getSharedImages().getImageDescriptor(
						ISharedImages.IMG_TOOL_FORWARD_DISABLED));
		registerKeybindings(forward);
		historyToolbar.add(forward);

		return historyBar;
	}

	private void registerKeybindings(IAction action) {
		final IHandler handler = new ActionHandler(action);
		final IHandlerService handlerService = PlatformUI.getWorkbench().getService(IHandlerService.class);
		final IHandlerActivation activation = handlerService.activateHandler(
				action.getActionDefinitionId(), handler,
				new ActiveShellExpression(dialog.getShell()));
		activations.add(activation);
	}

	public void dispose() {
		final IHandlerService handlerService = PlatformUI.getWorkbench().getService(IHandlerService.class);
		final Iterator iterator = activations.iterator();
		while (iterator.hasNext()) {
			handlerService.deactivateHandler((IHandlerActivation) iterator
					.next());
		}
		activations.clear();
		
	}

}
