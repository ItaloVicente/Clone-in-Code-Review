
	private class FindDropDownAction extends Action implements IMenuCreator {

		private final Action findNextAction;

		private final Action findPreviousAction;

		private Action currentAction;

		private Menu menu;

		public FindDropDownAction() {
			findNextAction = new Action() {
				@Override
				public void run() {
					findNext();
				}
			};
			findNextAction.setImageDescriptor(UIIcons.ELCL16_NEXT);
			findNextAction.setText(UIText.HistoryPage_findbar_next);
			findNextAction.setToolTipText(UIText.FindToolbar_NextTooltip);
			findPreviousAction = new Action() {
				@Override
				public void run() {
					findPrevious();
				}
			};
			findPreviousAction.setImageDescriptor(UIIcons.ELCL16_PREVIOUS);
			findPreviousAction.setText(UIText.HistoryPage_findbar_previous);
			findPreviousAction
					.setToolTipText(UIText.FindToolbar_PreviousTooltip);
			setAction(findNextAction);
			setMenuCreator(this);
		}

		@Override
		public void setEnabled(boolean enabled) {
			if (enabled && !isEnabled()) {
				setAction(findNextAction);
			}
			super.setEnabled(enabled);
		}

		public void findNext() {
			if (isEnabled()) {
				setAction(findNextAction);
				find(true);
			}
		}

		public void findPrevious() {
			if (isEnabled()) {
				setAction(findPreviousAction);
				find(false);
			}
		}

		private void setAction(Action action) {
			currentAction = action;
			setImageDescriptor(action.getImageDescriptor());
			setToolTipText(action.getToolTipText());
		}

		@Override
		public void run() {
			if (currentAction != null) {
				currentAction.run();
			}
		}

		@Override
		public void dispose() {
			if (menu != null) {
				menu.dispose();
				menu = null;
			}
		}

		@Override
		public Menu getMenu(Control parent) {
			if (menu != null) {
				return menu;
			}
			menu = new Menu(parent);
			ActionContributionItem item = new ActionContributionItem(
					findNextAction);
			item.fill(menu, -1);
			item = new ActionContributionItem(findPreviousAction);
			item.fill(menu, -1);
			return menu;
		}

		@Override
		public Menu getMenu(Menu parent) {
			return null;
		}

	}
