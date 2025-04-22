	private interface ICommitsProvider {

		Object getSearchContext();

		SWTCommit[] getCommits();

		RevFlag getHighlight();
	}

	private static class SearchBar extends ControlContribution {

		private IActionBars bars;

		private FindToolbar toolbar;

		private Object searchContext;

		private String lastText;

		private ObjectId lastObjectId;

		private Object lastSearchContext;

		private ICommitsProvider provider;

		private boolean wasVisible = false;

		private final CommitGraphTable graph;

		private final IAction openCloseToggle;

		/**
		 * "Go to next/previous" from the {@link FindToolbar} sends
		 * {@link SWT#Selection} events with the chosen {@link RevCommit} as
		 * data.
		 */
		private final Listener selectionListener = new Listener() {

			@Override
			public void handleEvent(Event evt) {
				final RevCommit commit = (RevCommit) evt.data;
				lastObjectId = commit.getId();
				graph.selectCommit(commit);
			}
		};

		/**
		 * Listener to close the search bar on ESC. (Ctrl/Cmd-F is already
		 * handled via global retarget action.)
		 */
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

		/**
		 * Listener to display status messages from the asynchronous find. (Is
		 * called in the UI thread.)
		 */
		private final StatusListener statusListener = new StatusListener() {

			@Override
			public void setMessage(FindToolbar originator, String text) {
				IStatusLineManager status = bars.getStatusLineManager();
				if (status != null) {
					status.setMessage(text);
				}
			}
		};

		/**
		 * Listener to ensure that the history view is fully activated when the
		 * user clicks into the search bar's text widget. This makes sure our
		 * status manager gets activated and thus shows the status messages. We
		 * don't get a focus event when the user clicks in the field; and
		 * fiddling with the focus in a FocusListener could get hairy anyway.
		 */
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

		public SearchBar(String id, CommitGraphTable graph,
				IAction openCloseAction, IActionBars bars) {
			super(id);
			super.setVisible(false);
			this.graph = graph;
			this.openCloseToggle = openCloseAction;
			this.bars = bars;
		}

		private void beforeHide() {
			lastText = toolbar.getText();
			lastSearchContext = searchContext;
			statusListener.setMessage(toolbar, ""); //$NON-NLS-1$
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
				bars.updateActionBars();
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
		public boolean isDynamic() {
			return true;
		}

		@Override
		protected Control createControl(Composite parent) {
			toolbar = new FindToolbar(parent);
			toolbar.setBackground(null);
			toolbar.addKeyListener(keyListener);
			toolbar.addListener(SWT.FocusIn, mouseListener);
			toolbar.addListener(SWT.FocusOut, mouseListener);
			toolbar.addListener(SWT.MouseDown, mouseListener);
			toolbar.addListener(SWT.MouseUp, mouseListener);
			toolbar.addListener(SWT.Modify,
					(e) -> lastText = toolbar.getText());
			toolbar.addStatusListener(statusListener);
			toolbar.addSelectionListener(selectionListener);
			boolean hasInput = provider != null;
			if (hasInput) {
				setInput(provider);
			}
			if (lastText != null) {
				if (lastSearchContext != null
						&& lastSearchContext.equals(searchContext)) {
					toolbar.setPreselect(lastObjectId);
				}
				toolbar.setText(lastText, hasInput);
			}
			lastSearchContext = null;
			lastObjectId = null;
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
			return toolbar;
		}

		public void setInput(ICommitsProvider provider) {
			this.provider = provider;
			if (toolbar != null) {
				searchContext = provider.getSearchContext();
				toolbar.setInput(provider.getHighlight(),
						graph.getTableView().getTable(), provider.getCommits());
			}
		}

	}

