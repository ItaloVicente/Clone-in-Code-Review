		parent.getShell().addControlListener(new ControlListener() {
			@Override
			public void controlResized(ControlEvent e) {
				closeDropDown();
			}

			@Override
			public void controlMoved(ControlEvent e) {
				closeDropDown();
			}

			private void closeDropDown() {
				if (shell == null || shell.isDisposed() || txtQuickAccess.isDisposed() || !shell.isVisible())
					return;
				quickAccessContents.doClose();
			}
		});

		hookUpSelectAll();

		txtQuickAccess.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				showList();
			}
		});
		txtQuickAccess.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				if (table != null) {
					table.getDisplay().asyncExec(() -> checkFocusLost(table, txtQuickAccess));
				}
				activated = false;
			}

			@Override
			public void focusGained(FocusEvent e) {
				previousFocusControl = (Control) e.getSource();
				activated = true;
			}
		});
		txtQuickAccess.addModifyListener(e -> showList());
		txtQuickAccess.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.keyCode == SWT.ESC) {
					activated = false;
					if (txtQuickAccess == previousFocusControl) {
						txtQuickAccess.getShell().forceFocus();
					} else if (previousFocusControl != null && !previousFocusControl.isDisposed())
						previousFocusControl.setFocus();
				} else if (e.keyCode == SWT.ARROW_UP) {
					e.doit = false;
				} else if (e.keyCode == SWT.ARROW_DOWN) {
					e.doit = false;
				}
				if (e.doit == false) {
					notifyAccessibleTextChanged();
				}
			}
		});
	}

	private void createContentsAndTable() {
		if (quickAccessContents != null) {
			return;
		}
		final CommandProvider commandProvider = new CommandProvider();
		txtQuickAccess.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				IHandlerService hs = SearchField.this.window.getContext().get(IHandlerService.class);
				if (commandProvider.getContextSnapshot() == null) {
					commandProvider.setSnapshot(hs.createContextSnapshot(true));
				}
			}
		});
		List<QuickAccessProvider> providers = new ArrayList<>();
		providers.add(new PreviousPicksProvider(previousPicksList));
		providers.add(new EditorProvider());
		providers.add(new ViewProvider(application, window));
		providers.add(new PerspectiveProvider());
		providers.add(commandProvider);
		providers.add(new ActionProvider());
		providers.add(new WizardProvider());
		providers.add(new PreferenceProvider());
		providers.add(new PropertiesProvider());
		providers.addAll(QuickAccessExtensionManager.getProviders(() -> {
			txtQuickAccess.getDisplay().asyncExec(() -> {
				txtQuickAccess.setText(lastSelectionFilter);
				txtQuickAccess.setFocus();
				SearchField.this.showList();
			});
