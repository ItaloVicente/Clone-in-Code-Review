
	private static final class HistorySearchBar extends SearchBar {

		private IActionBars bars;

		private final IAction openCloseToggle;

		private boolean wasVisible = false;

		private final KeyListener keyListener = new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				int key = SWTKeySupport.convertEventToUnmodifiedAccelerator(e);
				if (key == SWT.ESC) {
					setVisible(false);
					e.doit = false;
				}
			}
		};

		private final Listener mouseListener = new Listener() {

			private boolean hasFocus;

			private boolean hadFocusOnMouseDown;

			@Override
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.FocusIn:
					toolbar.getDisplay().asyncExec(new Runnable() {
						@Override
						public void run() {
							hasFocus = true;
						}
					});

					break;
				case SWT.FocusOut:
					hasFocus = false;
					break;
				case SWT.MouseDown:
					hadFocusOnMouseDown = hasFocus;
					break;
				case SWT.MouseUp:
					if (!hadFocusOnMouseDown) {
						graph.getControl().setFocus();
						toolbar.setFocus();
					}
					break;
				default:
					break;
				}
			}
		};

		public HistorySearchBar(String id, CommitGraphTable graph,
				IAction openCloseAction, IActionBars bars) {
			super(id, graph);
			this.bars = bars;
			this.openCloseToggle = openCloseAction;
			super.setVisible(false);
		}

		@Override
		public boolean isDynamic() {
			return true;
		}

		@Override
		protected FindToolbar createControl(Composite parent) {
			FindToolbar createdControl = super.createControl(parent);
			toolbar.addKeyListener(keyListener);
			toolbar.addListener(SWT.FocusIn, mouseListener);
			toolbar.addListener(SWT.FocusOut, mouseListener);
			toolbar.addListener(SWT.MouseDown, mouseListener);
			toolbar.addListener(SWT.MouseUp, mouseListener);

			if (wasVisible) {
				return toolbar;
			}
			wasVisible = true;
			toolbar.getDisplay().asyncExec(new Runnable() {

				@Override
				public void run() {
					if (toolbar != null && !toolbar.isDisposed()) {
						graph.getControl().setFocus();
						toolbar.setFocus();
					}
				}
			});
			return createdControl;
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
