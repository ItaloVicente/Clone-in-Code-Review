	private interface ICommitsProvider {
		SWTCommit[] getCommits();

		RevFlag getHighlight();
	}

	private static class SearchBar extends ViewBar {

		private FindToolbar toolbar;

		private String lastText;

		private ICommitsProvider provider;

		private final CommitGraphTable graph;

		private final IAction openCloseToggle;

		private final Listener selectionListener = new Listener() {

			@Override
			public void handleEvent(Event evt) {
				graph.selectCommit((RevCommit) evt.data);
			}
		};

		private final LayoutListener layoutListener = new LayoutListener() {

			@Override
			public void layoutChange(Composite changed) {
				resize();
			}
		};

		private final KeyListener keyListener = new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int key = SWTKeySupport.convertEventToUnmodifiedAccelerator(e);
				if (key == openCloseToggle.getAccelerator()) {
					setVisible(false);
					getParent().setFocus();
					e.doit = false;
				}
			}
		};

		public SearchBar(Control control, CommitGraphTable graph,
				IAction openCloseAction, IWorkbenchPart part) {
			super(control, part);
			this.graph = graph;
			this.openCloseToggle = openCloseAction;
			GridLayout layout = new GridLayout();
			layout.marginHeight = 0;
			layout.marginTop = 0;
			layout.marginBottom = 0;
			setLayout(layout);
		}

		@Override
		protected Composite createContent(Composite parentControl) {
			toolbar = new FindToolbar(parentControl);
			if (lastText != null) {
				toolbar.setText(lastText);
			}
			return toolbar;
		}

		@Override
		protected void aboutToShow(Rectangle bounds) {
			super.aboutToShow(bounds);
			toolbar.addKeyListener(keyListener);
			toolbar.addLayoutListener(layoutListener);
			toolbar.addSelectionListener(selectionListener);
			openCloseToggle.setChecked(true);
			if (provider != null) {
				setInput(provider);
			}
		}

		@Override
		protected void hide() {
			if (toolbar != null) {
				if (!toolbar.isDisposed()) {
					lastText = toolbar.getText();
					toolbar.removeKeyListener(keyListener);
					toolbar.removeLayoutListener(layoutListener);
					toolbar.removeSelectionListener(selectionListener);
				}
				toolbar.clear();
			}
			super.hide();
			toolbar = null;
			openCloseToggle.setChecked(false);
		}

		public void setInput(ICommitsProvider provider) {
			this.provider = provider;
			if (toolbar != null) {
				toolbar.setInput(provider.getHighlight(),
						graph.getTableView().getTable(), provider.getCommits());
			}
		}
	}

