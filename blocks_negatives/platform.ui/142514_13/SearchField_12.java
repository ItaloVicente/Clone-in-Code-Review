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
		}));

		quickAccessContents = new QuickAccessContents(providers.toArray(new QuickAccessProvider[providers.size()])) {
			@Override
			protected void updateFeedback(boolean filterTextEmpty, boolean showAllMatches) {
			}

			@Override
			protected void doClose() {
				resetProviders();
				dialogHeight = shell.getSize().y;
				dialogWidth = shell.getSize().x;
				shell.setVisible(false);
				removeAccessibleListener();
			}

			@Override
			protected QuickAccessElement getPerfectMatch(String filter) {
				return elementMap.get(filter);
			}

			@Override
			protected void handleElementSelected(String string, Object selectedElement) {
				lastSelectionFilter = string;
				if (selectedElement instanceof QuickAccessElement) {
					QuickAccessElement element = (QuickAccessElement) selectedElement;
					addPreviousPick(string, element);
					element.execute();

					if (txtQuickAccess.isDisposed()) {
						return;
					}

					/*
					 * By design, attempting to activate a part that is already active does not
					 * change the focus. However in the case of using Quick Access, focus is not in
					 * the active part, so re-activating the active part results in focus being left
					 * behind in the text field. If this happens then assign focus to the active
					 * part explicitly.
					 */
					if (txtQuickAccess.isFocusControl()) {
						MPart activePart = partService.getActivePart();
						if (activePart != null) {
							IPresentationEngine pe = activePart.getContext().get(IPresentationEngine.class);
							pe.focusGui(activePart);
						}
					}

					if (shell.isVisible()) {
						quickAccessContents.doClose();
					}
				}
			}

			@Override
			public void refresh(String filter) {
				super.refresh(filter);
				if (isLoadingPreviousElements) {
					showHintText(QuickAccessMessages.QuickAccessContents_RestoringPreviousChoicesLabel, null);
				}
			}
		};

		restoreDialog();

		quickAccessContents.hookFilterText(txtQuickAccess);
		shell = new Shell(txtQuickAccess.getShell(), SWT.RESIZE | SWT.ON_TOP);
		shell.setBackground(shell.getDisplay().getSystemColor(SWT.COLOR_WHITE));
		shell.setMinimumSize(new Point(MINIMUM_DIALOG_WIDTH, MINIMUM_DIALOG_HEIGHT));
		GridLayoutFactory.fillDefaults().applyTo(shell);
		quickAccessContents.createHintText(shell, Window.getDefaultOrientation());
		table = quickAccessContents.createTable(shell, Window.getDefaultOrientation());
		table.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				table.getDisplay().asyncExec(() -> checkFocusLost(table, txtQuickAccess));
			}
		});
		quickAccessContents.createInfoLabel(shell);
