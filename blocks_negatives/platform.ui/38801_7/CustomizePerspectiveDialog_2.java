			
			wasChanged = true;
		}
	}

	/**
	 * A label provider to include the description field of ShortcutItems in the
	 * table.
	 * 
	 * @since 3.5
	 */
	private class ShortcutLabelProvider extends
			TreeManager.TreeItemLabelProvider implements ITableLabelProvider {
		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			if (columnIndex == 0) {
				return this.getImage(element);
			}
			return null;
		}

		@Override
		public String getColumnText(Object element, int columnIndex) {
			if (columnIndex == 1) {
				return ((ShortcutItem) element).getDescription();
			}
			return this.getText(element);
		}

		@Override
		public void addListener(ILabelProviderListener listener) {
		}

		@Override
		public boolean isLabelProperty(Object element, String property) {
			return false;
		}

		@Override
		public void removeListener(ILabelProviderListener listener) {
		}
	}

	/**
	 * Provides the check logic for the categories viewer in the shortcuts tab.
	 * Categories have a dual concept of children - their proper children
	 * (sub-Categories, as in the wizards), and the actual elements they
	 * contribute to the menu system. The check state must take this into
	 * account.
	 * 
	 * @since 3.5
	 */
	private static class CategoryCheckProvider implements ICheckStateProvider {
		@Override
		public boolean isChecked(Object element) {
			Category category = (Category) element;

			if (category.getChildren().isEmpty()
					&& category.getContributionItems().isEmpty()) {
				return false;
			}

			for (Iterator i = category.getChildren().iterator(); i.hasNext();) {
				Category child = (Category) i.next();
				if (isChecked(child)) {
					return true;
				}
			}

			for (Iterator<ShortcutItem> i = category.getContributionItems().iterator(); i
					.hasNext();) {
				DisplayItem item = i.next();
				if (item.getState()) {
					return true;
				}
			}

			return false;
		}

		@Override
		public boolean isGrayed(Object element) {
			boolean hasChecked = false;
			boolean hasUnchecked = false;
			Category category = (Category) element;


			for (Iterator i = category.getChildren().iterator(); i.hasNext();) {
				Category child = (Category) i.next();
				if (isGrayed(child)) {
					return true;
				}
				if (isChecked(child)) {
					hasChecked = true;
				} else {
					hasUnchecked = true;
				}
				if (hasChecked && hasUnchecked) {
					return true;
				}
			}

			for (Iterator<ShortcutItem> i = category.getContributionItems().iterator(); i
					.hasNext();) {
				DisplayItem item = i.next();
				if (item.getState()) {
					hasChecked = true;
				} else {
					hasUnchecked = true;
				}
				if (hasChecked && hasUnchecked) {
					return true;
				}
			}

			return false;
		}
	}

	/**
	 * A tooltip which, given a model element, will display its icon (if there
	 * is one), name, and a description (if there is one).
	 * 
	 * @since 3.5
	 */
	private abstract class NameAndDescriptionToolTip extends ToolTip {
		public NameAndDescriptionToolTip(Control control, int style) {
			super(control, style, false);
		}

		protected abstract Object getModelElement(Event event);

		/**
		 * Adds logic to only show a tooltip if a meaningful item is under the
		 * cursor.
		 */
		@Override
		protected boolean shouldCreateToolTip(Event event) {
			return super.shouldCreateToolTip(event)
					&& getModelElement(event) != null;
		}

		@Override
		protected Composite createToolTipContentArea(Event event,
				Composite parent) {
			Object modelElement = getModelElement(event);

			Image iconImage = null;
			String nameString = null;

			if (modelElement instanceof DisplayItem) {
				iconImage = ((DisplayItem) modelElement).getImage();
				nameString = ((DisplayItem) modelElement).getLabel();
			} else if (modelElement instanceof ActionSet) {
				nameString = ((ActionSet) modelElement).descriptor.getLabel();
			}

			Composite composite = new Composite(parent, SWT.NONE);
			composite.setBackground(parent.getDisplay().getSystemColor(
					SWT.COLOR_INFO_BACKGROUND));
			composite.setLayout(new GridLayout(2, false));

			Label title = createEntry(composite, iconImage, nameString);
			title.setFont(tooltipHeading);
			GridDataFactory.createFrom((GridData)title.getLayoutData())
				.hint(SWT.DEFAULT, SWT.DEFAULT)
				.minSize(MIN_TOOLTIP_WIDTH, 1)
				.applyTo(title);

			String descriptionString = getDescription(modelElement);
			if (descriptionString != null) {
				createEntry(composite, null, descriptionString);
			}

			addContent(composite, modelElement);

			return composite;
		}

		/**
		 * Adds a line of information to <code>parent</code>. If
		 * <code>icon</code> is not <code>null</code>, an icon is placed on the
		 * left, and then a label with <code>text</code>.
		 * 
		 * @param parent
		 *            the composite to add the entry to
		 * @param icon
		 *            the icon to place next to the text. <code>null</code> for
		 *            none.
		 * @param text
		 *            the text to display
		 * @return the created label
		 */
		protected Label createEntry(Composite parent, Image icon, String text) {
			Color fg = parent.getDisplay().getSystemColor(SWT.COLOR_INFO_FOREGROUND);
			Color bg = parent.getDisplay().getSystemColor(SWT.COLOR_INFO_BACKGROUND);
			if (icon != null) {
				Label iconLabel = new Label(parent, SWT.NONE);
				iconLabel.setImage(icon);
				iconLabel.setForeground(fg);
				iconLabel.setBackground(bg);
				iconLabel.setData(new GridData());
			}

			Label textLabel = new Label(parent, SWT.WRAP);
			
			if(icon == null) {
				GridDataFactory.generate(textLabel, 2, 1);
			} else {
				GridDataFactory.generate(textLabel, 1, 1);
			}
			
			textLabel.setText(text);
			textLabel.setForeground(fg);
			textLabel.setBackground(bg);
			return textLabel;
		}

		/**
		 * Adds a line of information to <code>parent</code>. If
		 * <code>icon</code> is not <code>null</code>, an icon is placed on the
		 * left, and then a label with <code>text</code>, which supports using
		 * anchor tags to creates links
		 * 
		 * @param parent
		 *            the composite to add the entry to
		 * @param icon
		 *            the icon to place next to the text. <code>null</code> for
		 *            none.
		 * @param text
		 *            the text to display
		 * @return the created link
		 */
		protected Link createEntryWithLink(Composite parent, Image icon,
				String text) {
			Color fg = parent.getDisplay().getSystemColor(SWT.COLOR_INFO_FOREGROUND);
			Color bg = parent.getDisplay().getSystemColor(SWT.COLOR_INFO_BACKGROUND);
			if (icon != null) {
				Label iconLabel = new Label(parent, SWT.NONE);
				iconLabel.setImage(icon);
				iconLabel.setForeground(fg);
				iconLabel.setBackground(bg);
				iconLabel.setData(new GridData());
			}
			
			Link textLink = new Link(parent, SWT.WRAP);

			if(icon == null) {
				GridDataFactory.generate(textLink, 2, 1);
			}
			
			textLink.setText(text);
			textLink.setForeground(fg);
			textLink.setBackground(bg);
			return textLink;
		}

		protected void addContent(Composite destination, Object modelElement) {
		}
	}

	/**
	 * A tooltip with useful information based on the type of ContributionItem
	 * the cursor hovers over in a Table.
	 * 
	 * @since 3.5
	 */
	private class TableToolTip extends NameAndDescriptionToolTip {
		private Table table;

		public TableToolTip(Table table) {
			super(table, RECREATE);
			this.table = table;
		}

		@Override
		protected Object getModelElement(Event event) {
			TableItem tableItem = table.getItem(new Point(event.x, event.y));
			if (tableItem == null) {
				return null;
			}
			return tableItem.getData();
		}
	}

	/**
	 * A tooltip with useful information based on the type of ContributionItem
	 * the cursor hovers over in a Tree. In addition to the content provided by
	 * the {@link NameAndDescriptionToolTip} this includes action set
	 * information and key binding data.
	 * 
	 * @since 3.5
	 */
	private class ItemDetailToolTip extends NameAndDescriptionToolTip {
		private Tree tree;
		private boolean showActionSet;
		private boolean showKeyBindings;
		private ViewerFilter filter;
		private TreeViewer v;
		
		/**
		 * @param tree
		 *            The tree for the tooltip to hover over
		 */
		private ItemDetailToolTip(TreeViewer v, Tree tree, boolean showActionSet,
				boolean showKeyBindings, ViewerFilter filter) {
			super(tree,NO_RECREATE);
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
				
				if(isEffectivelyAvailable(item, filter)) {
					if(item.actionSet != null) {
						
						final String actionSetName = item.getActionSet().descriptor
								.getLabel();
						
						text = NLS.bind(
								WorkbenchMessages.HideItems_itemInActionSet,
								actionSetName);
					}
				} else {
					
					image = warningImageDescriptor.createImage();
					
					if (item.getChildren().isEmpty() && item.getActionSet() != null) {
						
						final String actionSetName = item.getActionSet().
								descriptor.getLabel();
						
						text = NLS.bind(
								WorkbenchMessages.HideItems_itemInUnavailableActionSet,
								actionSetName);
						
					} else {

						Set<ActionSet> actionGroup = new LinkedHashSet<ActionSet>();
						collectDescendantCommandGroups(actionGroup, item,
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
									"{0}{1}", new Object[] { NEW_LINE, commandGroupList }); //$NON-NLS-1$
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
							ActionSet actionSet = idToActionSet
									.get(e.text);
							if (actionSet == null) {
								hide();
								viewActionSet(item);
							} else {
								hide();
								viewActionSet(actionSet);
							}
						}
					});
				}
			}

			if (showKeyBindings && getCommandID(item) != null) {
				ICommandService commandService = (ICommandService) window
						.getService(ICommandService.class);
				Command command = commandService.getCommand(getCommandID(item));

				if (command != null && command.isDefined()) {
					Binding[] bindings = getKeyBindings(item);
					String keybindings = keyBindingsAsString(bindings);

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
							if (isNewWizard(item)) {
								parameters.put(
												IWorkbenchCommandConstants.FILE_NEW_PARM_WIZARDID,
										getParamID(item));
							} else if (isShowPerspective(item)) {
								parameters
										.put(
												IWorkbenchCommandConstants.PERSPECTIVES_SHOW_PERSPECTIVE_PARM_ID,
												getParamID(item));
							} else if (isShowView(item)) {
								parameters.put(
										IWorkbenchCommandConstants.VIEWS_SHOW_VIEW_PARM_ID,
										getParamID(item));
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
							PreferenceDialog dialog = PreferencesUtil
									.createPreferenceDialogOn(getShell(),
											KEYS_PREFERENCE_PAGE_ID,
											new String[0], highlight);
							hide();
							dialog.open();
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
	}

	/**
	 * Filters out contribution items which are not in a given action set.
	 * 
	 * @since 3.5
	 */
	private static class ActionSetFilter extends ViewerFilter {
		private ActionSet actionSet;

		public void setActionSet(ActionSet actionSet) {
			this.actionSet = actionSet;
		}

		@Override
		public boolean select(Viewer viewer, Object parentElement,
				Object element) {
			if (!(element instanceof DisplayItem)) {
				return false;
			}
			if (actionSet == null) {
				return false;
			}
			return includeInSetStructure((DisplayItem) element, actionSet);
		}
	}

	/**
	 * A check provider which calculates checked state based on leaf states in
	 * the tree (as opposed to children in a model).
	 * 
	 * @since 3.5
	 */
	private static class FilteredTreeCheckProvider implements
			ICheckStateProvider {
		private ITreeContentProvider contentProvider;
		private ViewerFilter filter;

		public FilteredTreeCheckProvider(ITreeContentProvider contentProvider,
				ViewerFilter filter) {
			this.contentProvider = contentProvider;
			this.filter = filter;
		}

		@Override
		public boolean isChecked(Object element) {
			TreeItem treeItem = (TreeItem) element;
			return getLeafStates(treeItem, contentProvider, filter) != TreeManager.CHECKSTATE_UNCHECKED;
		}

		@Override
		public boolean isGrayed(Object element) {
			TreeItem treeItem = (TreeItem) element;
			return getLeafStates(treeItem, contentProvider, filter) == TreeManager.CHECKSTATE_GRAY;
		}
	}

	/**
	 * A check listener to bring about the expected change in a model based on a
	 * check event in a filtered viewer. Since the checked state of a parent in
	 * a filtered viewer is not based on its model state, but rather its leafs'
	 * states, when a non-leaf element's check state changes, its model state
	 * does not necessarily change, but its leafs' model states do.
	 * 
	 * @since 3.5
	 */
	private static class FilteredViewerCheckListener implements
			ICheckStateListener {
		private ITreeContentProvider contentProvider;
		private ViewerFilter filter;

		public FilteredViewerCheckListener(
				ITreeContentProvider contentProvider, ViewerFilter filter) {
			this.contentProvider = contentProvider;
			this.filter = filter;
		}

		@Override
		public void checkStateChanged(CheckStateChangedEvent event) {
			setAllLeafs((DisplayItem) event.getElement(), event
					.getChecked(), contentProvider, filter);
		}
	}

	/**
	 * On a model change, update a filtered listener. While the check listener
	 * provided by the model will take care of the elements which change, since
	 * we simulate our own check state of parents, the parents may need to be
	 * updated.
	 * 
	 * @since 3.5
	 */
	private final class FilteredModelCheckListener implements CheckListener {
		private final ActionSetFilter filter;
		private final StructuredViewer viewer;

		private FilteredModelCheckListener(ActionSetFilter filter,
				StructuredViewer viewer) {
			this.filter = filter;
			this.viewer = viewer;
		}

		@Override
		public void checkChanged(TreeItem changedItem) {
			TreeItem item = changedItem;
			boolean update = false;

			while (item != null) {
				update = update || filter.select(null, null, item);
				if (update) {
					viewer.update(item, null);
				}
				item = item.getParent();
			}
		}
	}

	/**
	 * A check listener which, upon changing the check state of a contribution
	 * item, checks if that item is eligible to be checked (i.e. it is in an
	 * available action set), and if not, informs the user of the illegal
	 * operation. If the operation is legal, the event is forwarded to the check
	 * listener to actually perform a useful action.
	 * 
	 * @since 3.5
	 */
	private class UnavailableContributionItemCheckListener implements
			ICheckStateListener {
		private CheckboxTreeViewer viewer;
		private ICheckStateListener originalListener;

		/**
		 * @param viewer
		 *            the viewer being listened to
		 * @param originalListener
		 *            the listener to invoke upon a legal action
		 */
		public UnavailableContributionItemCheckListener(
				CheckboxTreeViewer viewer, ICheckStateListener originalListener) {
			this.viewer = viewer;
			this.originalListener = originalListener;
		}

		@Override
		public void checkStateChanged(CheckStateChangedEvent event) {
			DisplayItem item = (DisplayItem) event.getElement();
			ViewerFilter[] filters = viewer.getFilters();
			boolean isEffectivelyAvailable = isEffectivelyAvailable(item, filters.length > 0 ? filters[0] : null);

			if (isEffectivelyAvailable) {
				originalListener.checkStateChanged(event);
				return;
			}

			boolean isAvailable = isAvailable(item);
			viewer.update(event.getElement(), null);

			if (isAvailable) {
				if (viewer.getExpandedState(item)) {
					MessageBox mb = new MessageBox(viewer.getControl()
							.getShell(), SWT.OK | SWT.ICON_WARNING | SWT.SHEET);
					mb
							.setText(WorkbenchMessages.HideItemsCannotMakeVisible_dialogTitle);
					mb
							.setMessage(NLS
									.bind(
											WorkbenchMessages.HideItemsCannotMakeVisible_unavailableChildrenText,
											item.getLabel()));
					mb.open();
				} else {
					MessageBox mb = new MessageBox(viewer.getControl()
							.getShell(), SWT.OK | SWT.ICON_WARNING | SWT.SHEET);
					mb
							.setText(WorkbenchMessages.HideItemsCannotMakeVisible_dialogTitle);
					mb
							.setMessage(NLS
									.bind(
											WorkbenchMessages.HideItemsCannotMakeVisible_unavailableChildrenText,
											item.getLabel()));
					mb.open();
				}
			} else {
				MessageBox mb = new MessageBox(viewer.getControl().getShell(),
						SWT.YES | SWT.NO | SWT.ICON_WARNING | SWT.SHEET);
				mb
						.setText(WorkbenchMessages.HideItemsCannotMakeVisible_dialogTitle);
				final String errorExplanation = NLS
						.bind(
								WorkbenchMessages.HideItemsCannotMakeVisible_unavailableCommandGroupText,
								item.getLabel(), item.getActionSet());
				final String message = NLS
						.bind(
								"{0}{1}{1}{2}", new Object[] { errorExplanation, NEW_LINE, WorkbenchMessages.HideItemsCannotMakeVisible_switchToCommandGroupTab }); //$NON-NLS-1$
				mb.setMessage(message);
				if (mb.open() == SWT.YES) {
					viewActionSet(item);
				}
			}
		}
	}

	/**
	 * A label provider which takes the default label provider in the
	 * TreeManager, and adds on functionality to gray out text and icons of
	 * contribution items whose action sets are unavailable.
	 * 
	 * @since 3.5
	 * 
	 */
	private class GrayOutUnavailableLabelProvider extends
			TreeManager.TreeItemLabelProvider implements IColorProvider {
		private Display display;
		private ViewerFilter filter;

		public GrayOutUnavailableLabelProvider(Display display, ViewerFilter filter) {
			this.display = display;
			this.filter = filter;
		}

		@Override
		public Color getBackground(Object element) {
			return null;
		}

		@Override
		public Color getForeground(Object element) {
			if (!isEffectivelyAvailable((DisplayItem) element, filter)) {
				return display.getSystemColor(SWT.COLOR_GRAY);
			}
			return null;
		}

		@Override
		public Image getImage(Object element) {
			Image actual = super.getImage(element);

			if (element instanceof DisplayItem && actual != null) {
				DisplayItem item = (DisplayItem) element;
				if (!isEffectivelyAvailable(item, filter)) {
					ImageDescriptor original = ImageDescriptor
							.createFromImage(actual);
					ImageDescriptor disable = ImageDescriptor.createWithFlags(
							original, SWT.IMAGE_DISABLE);
					Image newImage = disable.createImage();
					toDispose.add(newImage);
					return newImage;
				}
			}

			return actual;
		}
	}

	/**
	 * The proxy IActionBarConfigurer that gets passed to the application's
	 * ActionBarAdvisor. This is used to construct a representation of the
	 * window's hardwired menus and toolbars in order to display their structure
	 * properly in the preview panes.
	 * 
	 * @since 3.5
	 */
	public class CustomizeActionBars implements IActionBarConfigurer2,
			IActionBars2 {
