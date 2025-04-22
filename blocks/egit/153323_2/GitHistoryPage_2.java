
	private static final class HistorySearchBar extends SearchBar {

		private IActionBars bars;

		public HistorySearchBar(String id, CommitGraphTable graph,
				IAction openCloseAction,
				IActionBars bars) {
			super(id, graph, openCloseAction);
			this.bars = bars;
		}

		private void beforeHide() {
			lastText = toolbar.getText();
			lastSearchContext = searchContext;
			showStatus(toolbar, ""); //$NON-NLS-1$
			toolbar = null;
			openCloseToggle.setChecked(false);
			wasVisible = false;
		}

		private void workAroundBug551067(boolean visible) {
			IContributionManager parent = getParent();
			if (parent instanceof SubToolBarManager) {
				SubToolBarManager subManager = (SubToolBarManager) parent;
				IContributionItem item = subManager.getParent().find(getId());
				if (item instanceof SubContributionItem) {
					item.setVisible(visible && subManager.isVisible());
				}
			}
		}

		@Override
		public void setVisible(boolean visible) {
			if (visible != isVisible()) {
				if (!visible) {
					beforeHide();
				}
				super.setVisible(visible);
				workAroundBug551067(visible);
				if (bars != null) {
					bars.updateActionBars();
				}
				if (visible && toolbar != null) {
					openCloseToggle.setChecked(true);
					graph.getControl().setFocus();
					toolbar.setFocus();
				} else if (!visible && !graph.getControl().isDisposed()) {
					graph.getControl().setFocus();
				}
			}
		}

		@Override
		protected void showStatus(FindToolbar originator, String text) {
			bars.getStatusLineManager().setMessage(text);
		}

	}
