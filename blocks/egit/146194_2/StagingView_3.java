
	private static class PresentationAction extends ToolbarMenuAction
			implements IPropertyChangeListener {

		private ToolBarManager toolbar;

		private final IPreferenceStore store;

		private final @NonNull List<IAction> actions;

		public PresentationAction(IPreferenceStore store, IAction... actions) {
			super(UIText.StagingView_Presentation);
			@SuppressWarnings("null")
			@NonNull
			List<IAction> a = actions != null ? Arrays.asList(actions)
					: Collections.emptyList();
			this.actions = a;
			this.store = store;
			store.addPropertyChangeListener(this);
		}

		@Override
		protected Collection<IAction> getActions() {
			return actions;
		}

		public void setToolbar(ToolBarManager toolbar) {
			this.toolbar = toolbar;
		}

		@Override
		public void dispose() {
			store.removePropertyChangeListener(this);
			super.dispose();
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
