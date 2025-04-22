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

		private final CommitGraphTable graph;

		private final IAction openCloseToggle;

		private final Listener selectionListener = new Listener() {

			@Override
			public void handleEvent(Event evt) {
				final RevCommit commit = (RevCommit) evt.data;
				lastObjectId = commit.getId();
				graph.selectCommit(commit);
			}
		};

		private final KeyListener keyListener = new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				int key = SWTKeySupport.convertEventToUnmodifiedAccelerator(e);
				if (key == openCloseToggle.getAccelerator() || key == SWT.ESC) {
					setVisible(false);
					e.doit = false;
				}
			}
		};

		private final StatusListener statusListener = new StatusListener() {

			@Override
			public void setMessage(FindToolbar originator, String text) {
				IStatusLineManager status = bars.getStatusLineManager();
				if (status != null) {
					status.setMessage(text);
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
		}

		@Override
		public void setVisible(boolean visible) {
			if (visible != isVisible()) {
				if (!visible) {
					beforeHide();
				}
				super.setVisible(visible);
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
		protected Control createControl(Composite parent) {
			toolbar = new FindToolbar(parent);
			toolbar.setBackground(null);
			toolbar.addKeyListener(keyListener);
			toolbar.addListener(SWT.FocusIn, mouseListener);
			toolbar.addListener(SWT.FocusOut, mouseListener);
			toolbar.addListener(SWT.MouseDown, mouseListener);
			toolbar.addListener(SWT.MouseUp, mouseListener);
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

