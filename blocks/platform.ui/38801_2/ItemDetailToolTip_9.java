package org.eclipse.ui.internal.dialogs.cpd;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.jface.bindings.Binding;
import org.eclipse.jface.bindings.BindingManager;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IWorkbenchCommandConstants;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.dialogs.PreferencesUtil;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.internal.WorkbenchWindow;
import org.eclipse.ui.internal.dialogs.cpd.CustomizePerspectiveDialog.ActionSet;
import org.eclipse.ui.internal.dialogs.cpd.CustomizePerspectiveDialog.DisplayItem;
import org.eclipse.ui.internal.dialogs.cpd.CustomizePerspectiveDialog.DynamicContributionItem;
import org.eclipse.ui.internal.dialogs.cpd.CustomizePerspectiveDialog.ShortcutItem;
import org.eclipse.ui.internal.dialogs.cpd.TreeManager.TreeItem;
import org.eclipse.ui.internal.keys.BindingService;
import org.eclipse.ui.internal.util.Util;
import org.eclipse.ui.keys.IBindingService;

class ItemDetailToolTip extends NameAndDescriptionToolTip {
	private Tree tree;
	private boolean showActionSet;
	private boolean showKeyBindings;
	private ViewerFilter filter;
	private TreeViewer v;
	private CustomizePerspectiveDialog dialog;

	ItemDetailToolTip(CustomizePerspectiveDialog dialog, TreeViewer v, Tree tree, boolean showActionSet,
			boolean showKeyBindings, ViewerFilter filter) {
		super(tree,NO_RECREATE);
		this.dialog = dialog;
		this.tree = tree;
		this.v = v;
		this.showActionSet = showActionSet;
		this.showKeyBindings = showKeyBindings;
		this.filter = filter;
		this.setHideOnMouseDown(false);
	}

	@Override
	public Point getLocation(Point tipSize, Event event) {
		ViewerCell cell = v.getCell(new Point(event.x, event.y));

		if( cell != null ) {
			return tree.toDisplay(event.x,cell.getBounds().y+cell.getBounds().height);
		}

		return super.getLocation(tipSize, event);
	}

	@Override
	protected Object getToolTipArea(Event event) {
		return v.getCell(new Point(event.x, event.y));
	}

	@Override
	protected void addContent(Composite destination, Object modelElement) {
		final DisplayItem item = (DisplayItem) modelElement;

		if (showActionSet) {
			String text = null;
			Image image = null;

			if(CustomizePerspectiveDialog.isEffectivelyAvailable(item, filter)) {
				if(item.actionSet != null) {

					final String actionSetName = item.getActionSet().descriptor
							.getLabel();

					text = NLS.bind(
							WorkbenchMessages.HideItems_itemInActionSet,
							actionSetName);
				}
			} else {

				image = dialog.warningImageDescriptor.createImage();

				if (item.getChildren().isEmpty() && item.getActionSet() != null) {

					final String actionSetName = item.getActionSet().
							descriptor.getLabel();

					text = NLS.bind(
							WorkbenchMessages.HideItems_itemInUnavailableActionSet,
							actionSetName);

				} else {

					Set<ActionSet> actionGroup = new LinkedHashSet<ActionSet>();
					ItemDetailToolTip.collectDescendantCommandGroups(actionGroup, item,
							filter);

					if (actionGroup.size() == 1) {
						ActionSet actionSet = actionGroup.
								iterator().next();
						text = NLS.bind(
								WorkbenchMessages.HideItems_unavailableChildCommandGroup,
								actionSet.descriptor.getId(),
								actionSet.descriptor.getLabel());
					} else {
						String commandGroupList = null;

						for (Iterator<ActionSet> i = actionGroup.iterator(); i.hasNext();) {
							ActionSet actionSet = i.next();

							String commandGroupLink = MessageFormat.format(
									"<a href=\"{0}\">{1}</a>", //$NON-NLS-1$
									new Object[] { actionSet.descriptor.getId(),
											actionSet.descriptor.getLabel() });

							if (commandGroupList == null) {
								commandGroupList = commandGroupLink;
							} else {
								commandGroupList = Util.createList(
										commandGroupList, commandGroupLink);
							}
						}

						commandGroupList = NLS.bind(
								"{0}{1}", new Object[] { CustomizePerspectiveDialog.NEW_LINE, commandGroupList }); //$NON-NLS-1$
						text = NLS.bind(
								WorkbenchMessages.HideItems_unavailableChildCommandGroups,
								commandGroupList);
					}
				}
			}

			if(text != null) {
				Link link = createEntryWithLink(destination, image, text);
				link.addSelectionListener(new SelectionListener() {
					@Override
					public void widgetDefaultSelected(SelectionEvent e) {
						widgetSelected(e);
					}

					@Override
					public void widgetSelected(SelectionEvent e) {
						ActionSet actionSet = dialog.idToActionSet.get(e.text);
						if (actionSet == null) {
							hide();
							dialog.showActionSet(item);
						} else {
							hide();
							dialog.showActionSet(actionSet);
						}
					}
				});
			}
		}

		if (showKeyBindings && CustomizePerspectiveDialog.getCommandID(item) != null) {
			ICommandService commandService = (ICommandService) dialog.window
					.getService(ICommandService.class);
			Command command = commandService.getCommand(CustomizePerspectiveDialog.getCommandID(item));

			if (command != null && command.isDefined()) {
				Binding[] bindings = ItemDetailToolTip.getKeyBindings(dialog.window, item);
				String keybindings = ItemDetailToolTip.keyBindingsAsString(bindings);

				String text = null;

				final boolean available = (item.getActionSet() == null)
						|| (item.getActionSet().isActive());

				if (bindings.length > 0) {
					if (available) {
						text = NLS.bind(
								WorkbenchMessages.HideItems_keyBindings,
								keybindings);
					} else {
						text = NLS
								.bind(
										WorkbenchMessages.HideItems_keyBindingsActionSetUnavailable,
										keybindings);
					}
				} else {
					if (available) {
						text = WorkbenchMessages.HideItems_noKeyBindings;
					} else {
						text = WorkbenchMessages.HideItems_noKeyBindingsActionSetUnavailable;
					}
				}

				final Object highlight;
				if (bindings.length == 0) {
					Map<String, String> parameters = new HashMap<String, String>();

					if (item instanceof ShortcutItem) {
						if (CustomizePerspectiveDialog.isNewWizard(item)) {
							parameters.put(
											IWorkbenchCommandConstants.FILE_NEW_PARM_WIZARDID,
									CustomizePerspectiveDialog.getParamID(item));
						} else if (CustomizePerspectiveDialog.isShowPerspective(item)) {
							parameters
									.put(
											IWorkbenchCommandConstants.PERSPECTIVES_SHOW_PERSPECTIVE_PARM_ID,
											CustomizePerspectiveDialog.getParamID(item));
						} else if (CustomizePerspectiveDialog.isShowView(item)) {
							parameters.put(
									IWorkbenchCommandConstants.VIEWS_SHOW_VIEW_PARM_ID,
									CustomizePerspectiveDialog.getParamID(item));
						}
					}

					ParameterizedCommand pc = ParameterizedCommand
							.generateCommand(command, parameters);
					highlight = pc;
				} else {
					highlight = bindings[0];
				}

				Link bindingLink = createEntryWithLink(destination, null,
						text);

				bindingLink.addSelectionListener(new SelectionListener() {
					@Override
					public void widgetDefaultSelected(SelectionEvent e) {
						widgetDefaultSelected(e);
					}

					@Override
					public void widgetSelected(SelectionEvent e) {
						PreferenceDialog pdialog = PreferencesUtil.createPreferenceDialogOn(dialog.getShell(),
										CustomizePerspectiveDialog.KEYS_PREFERENCE_PAGE_ID,
										new String[0], highlight);
						hide();
						pdialog.open();
					}
				});
			}
		}

		if (item instanceof DynamicContributionItem) {
			DynamicContributionItem dynamic = ((DynamicContributionItem) item);
			StringBuffer text = new StringBuffer();
			final List<MenuItem> currentItems = dynamic.getCurrentItems();

			if (currentItems.size() > 0) {
				text.append(WorkbenchMessages.HideItems_dynamicItemList);
				for (Iterator<MenuItem> i = currentItems.iterator(); i.hasNext();) {
					MenuItem menuItem = i.next();
					text.append(CustomizePerspectiveDialog.NEW_LINE).append("- ") //$NON-NLS-1$
							.append(menuItem.getText());
				}
			} else {
				text
						.append(WorkbenchMessages.HideItems_dynamicItemEmptyList);
			}
			createEntry(destination, null, text.toString());
		}
	}

	@Override
	protected Object getModelElement(Event event) {
		org.eclipse.swt.widgets.TreeItem treeItem = tree.getItem(new Point(
				event.x, event.y));
		if (treeItem == null) {
			return null;
		}
		return treeItem.getData();
	}

	static void collectDescendantCommandGroups(Collection<ActionSet> collection,
			DisplayItem item, ViewerFilter filter) {
		List<TreeItem> children = item.getChildren();
		for (TreeItem treeItem : children) {
			DisplayItem child = (DisplayItem) treeItem;
			if ((filter == null || filter.select(null, null, child))
					&& child.getActionSet() != null) {
				collection.add(child.getActionSet());
			}
			collectDescendantCommandGroups(collection, child, filter);
		}
	}

	static String keyBindingsAsString(Binding[] bindings) {
		String keybindings = null;
		for (int i = 0; i < bindings.length; i++) {
			boolean alreadyRecorded = false;
			for (int j = 0; j < i && !alreadyRecorded; j++) {
				if (bindings[i].getTriggerSequence().equals(
						bindings[j].getTriggerSequence())) {
					alreadyRecorded = true;
				}
			}
			if (!alreadyRecorded) {
				String keybinding = bindings[i].getTriggerSequence().format();
				if (i == 0) {
					keybindings = keybinding;
				} else {
					keybindings = Util.createList(keybindings, keybinding);
				}
			}
		}
		return keybindings;
	}

	static Binding[] getKeyBindings(WorkbenchWindow window, DisplayItem item) {
		IBindingService bindingService = (IBindingService) window
				.getService(IBindingService.class);

		if (!(bindingService instanceof BindingService)) {
			return new Binding[0];
		}

		String id = CustomizePerspectiveDialog.getCommandID(item);
		String param = CustomizePerspectiveDialog.getParamID(item);

		BindingManager bindingManager = ((BindingService) bindingService)
				.getBindingManager();

		Collection<?> allBindings = bindingManager
				.getActiveBindingsDisregardingContextFlat();

		List<Binding> foundBindings = new ArrayList<Binding>(2);

		for (Iterator<?> i = allBindings.iterator(); i.hasNext();) {
			Binding binding = (Binding) i.next();
			if (binding.getParameterizedCommand() == null) {
				continue;
			}
			if (binding.getParameterizedCommand().getId() == null) {
				continue;
			}
			if (binding.getParameterizedCommand().getId().equals(id)) {
				if (param == null) {
					foundBindings.add(binding);
				} else {
					Map<?, ?> m = binding.getParameterizedCommand().getParameterMap();
					String key = null;
					if (CustomizePerspectiveDialog.isNewWizard(item)) {
						key = IWorkbenchCommandConstants.FILE_NEW_PARM_WIZARDID;
					} else if (CustomizePerspectiveDialog.isShowView(item)) {
						key = IWorkbenchCommandConstants.VIEWS_SHOW_VIEW_PARM_ID;
					} else if (CustomizePerspectiveDialog.isShowPerspective(item)) {
						key = IWorkbenchCommandConstants.PERSPECTIVES_SHOW_PERSPECTIVE_PARM_ID;
					}

					if (key != null) {
						if (param.equals(m.get(key))) {
							foundBindings.add(binding);
						}
					}
				}
			}
		}

		Binding[] bindings = foundBindings
				.toArray(new Binding[foundBindings.size()]);

		return bindings;
	}
}
