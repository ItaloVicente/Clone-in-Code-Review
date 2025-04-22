
	private static class PresentationAction extends Action
			implements IWorkbenchAction, IMenuCreator, IPropertyChangeListener {

		private Menu menu;

		private ToolBarManager toolbar;

		private final IPreferenceStore store;

		private final IAction[] actions;

		public PresentationAction(IPreferenceStore store, IAction... actions) {
			super(UIText.StagingView_Presentation, IAction.AS_DROP_DOWN_MENU);
			this.actions = actions;
			this.store = store;
			store.addPropertyChangeListener(this);
		}

		public void setToolbar(ToolBarManager toolbar) {
			this.toolbar = toolbar;
		}

		@Override
		public IMenuCreator getMenuCreator() {
			return this;
		}

		@Override
		public Menu getMenu(Menu parent) {
			return null;
		}

		@Override
		public void runWithEvent(Event event) {
			if (!isEnabled()) {
				return;
			}
			Widget widget = event.widget;
			if (widget instanceof ToolItem) {
				ToolItem item = (ToolItem) widget;
				Rectangle bounds = item.getBounds();
				event.detail = SWT.ARROW;
				event.x = bounds.x;
				event.y = bounds.y + bounds.height;
				item.notifyListeners(SWT.Selection, event);
			}
		}

		@Override
		public Menu getMenu(Control parent) {
			if (menu != null) {
				menu.dispose();
				menu = null;
			}
			if (isEnabled()) {
				menu = new Menu(parent);
				for (IAction action : actions) {
					ActionContributionItem item = new ActionContributionItem(
							action);
					item.fill(menu, -1);
				}
			}
			return menu;
		}

		@Override
		public void dispose() {
			if (menu != null) {
				menu.dispose();
				menu = null;
			}
			store.removePropertyChangeListener(this);
		}

		@Override
		public void propertyChange(PropertyChangeEvent event) {
			if (UIPreferences.STAGING_VIEW_PRESENTATION
					.equals(event.getProperty())) {
				Presentation current = getPresentation(
						event.getNewValue().toString(), Presentation.LIST);
				switch (current) {
				case LIST:
					setImageDescriptor(UIIcons.FLAT);
					break;
				case TREE:
					setImageDescriptor(UIIcons.HIERARCHY);
					break;
				case COMPACT_TREE:
					setImageDescriptor(UIIcons.COMPACT);
					break;
				default:
					return;
				}
				update();
			}
		}

		public void update() {
			if (toolbar != null) {
				toolbar.update(true);
			}
		}
	}
