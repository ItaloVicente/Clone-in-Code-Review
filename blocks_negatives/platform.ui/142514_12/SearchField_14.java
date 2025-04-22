	private void hookUpSelectAll() {
		final IEclipseContext windowContext = window.getContext();
		IFocusService focus = windowContext.get(IFocusService.class);
		focus.addFocusTracker(txtQuickAccess, SearchField.class.getName());

		Expression focusExpr = new Expression() {
			@Override
			public void collectExpressionInfo(ExpressionInfo info) {
				info.addVariableNameAccess(ISources.ACTIVE_FOCUS_CONTROL_ID_NAME);
			}

			@Override
			public EvaluationResult evaluate(IEvaluationContext context) {
				return EvaluationResult.valueOf(
						SearchField.class.getName().equals(context.getVariable(ISources.ACTIVE_FOCUS_CONTROL_ID_NAME)));
			}
		};

		IHandlerService whService = windowContext.get(IHandlerService.class);
		whService.activateHandler(IWorkbenchCommandConstants.EDIT_SELECT_ALL, new AbstractHandler() {
			@Override
			public Object execute(ExecutionEvent event) {
				txtQuickAccess.selectAll();
				return null;
			}
		}, focusExpr);
		whService.activateHandler(IWorkbenchCommandConstants.EDIT_CUT, new AbstractHandler() {
			@Override
			public Object execute(ExecutionEvent event) {
				txtQuickAccess.cut();
				return null;
			}
		}, focusExpr);
		whService.activateHandler(IWorkbenchCommandConstants.EDIT_COPY, new AbstractHandler() {
			@Override
			public Object execute(ExecutionEvent event) {
				txtQuickAccess.copy();
				return null;
			}
		}, focusExpr);
		whService.activateHandler(IWorkbenchCommandConstants.EDIT_PASTE, new AbstractHandler() {
			@Override
			public Object execute(ExecutionEvent event) {
				txtQuickAccess.paste();
				return null;
			}
		}, focusExpr);
	}

	/**
	 * This method was copy/pasted from JFace.
	 */
	private Rectangle getConstrainedShellBounds(Display display, Rectangle preferredSize) {
		Rectangle result = new Rectangle(preferredSize.x, preferredSize.y, preferredSize.width, preferredSize.height);

		Point topLeft = new Point(preferredSize.x, preferredSize.y);
		Monitor mon = Util.getClosestMonitor(display, topLeft);
		Rectangle bounds = mon.getClientArea();

		if (result.height > bounds.height) {
			result.height = bounds.height;
		}

		if (result.width > bounds.width) {
			result.width = bounds.width;
		}

		result.x = Math.max(bounds.x, Math.min(result.x, bounds.x + bounds.width - result.width));
		result.y = Math.max(bounds.y, Math.min(result.y, bounds.y + bounds.height - result.height));

		return result;
	}

	void layoutShell() {
		Display display = txtQuickAccess.getDisplay();
		Rectangle tempBounds = txtQuickAccess.getBounds();
		Rectangle compBounds = display.map(txtQuickAccess, null, tempBounds);
		Rectangle shellBounds = txtQuickAccess.getShell().getBounds();
		int preferredWidth = Math.max(MINIMUM_DIALOG_WIDTH,
				dialogWidth == -1 ? (int) (shellBounds.width * 0.6) : dialogWidth);
		int width = Math.max(preferredWidth, compBounds.width);
		int height = Math.max(MINIMUM_DIALOG_HEIGHT,
				dialogHeight == -1 ? (int) (shellBounds.height * 0.9) : dialogHeight);

		if (compBounds.x + width > shellBounds.x + shellBounds.width) {
			compBounds.x = Math.max(shellBounds.x, (compBounds.x + compBounds.width - width));
		}

		shell.setBounds(getConstrainedShellBounds(display,
				new Rectangle(compBounds.x, compBounds.y + compBounds.height, width, height)));
		shell.layout();
	}

	public void activate(Control previousFocusControl) {
		this.previousFocusControl = previousFocusControl;
		createContentsAndTable();
		if (!shell.isVisible()) {
			layoutShell();
			quickAccessContents.preOpen();
			shell.setVisible(true);
			addAccessibleListener();
			quickAccessContents.refresh(txtQuickAccess.getText().toLowerCase());
		} else {
			quickAccessContents.setShowAllMatches(!quickAccessContents.getShowAllMatches());
		}
	}

	/**
	 * Checks if the text or shell has focus. If not, closes the shell.
	 *
	 * @param table the shell's table
	 * @param text  the search text field
	 */
	protected void checkFocusLost(final Table table, final Text text) {
		if (shell == null) {
			return;
		}
		if (!shell.isDisposed() && !table.isDisposed() && !text.isDisposed()) {
			if (table.getDisplay().getActiveShell() == table.getShell()) {
				text.setFocus();
				return;
			}
			if (!shell.isFocusControl() && !table.isFocusControl() && !text.isFocusControl()) {
				quickAccessContents.doClose();
			}
		}
	}

	/**
	 * Adds a listener to the <code>org.eclipse.swt.accessibility.Accessible</code>
	 * object assigned to the Quick Access search box. The listener sets a name of a
	 * selected element in the search result list as a text to read for a screen
	 * reader.
	 */
	private void addAccessibleListener() {
		if (accessibleListener == null) {
			accessibleListener = new AccessibleAdapter() {
				@Override
				public void getName(AccessibleEvent e) {
					e.result = selectedString;
				}
			};
			txtQuickAccess.getAccessible().addAccessibleListener(accessibleListener);
		}
	}

	/**
	 * Removes a listener from the
	 * <code>org.eclipse.swt.accessibility.Accessible</code> object assigned to the
	 * Quick Access search box.
	 */
	private void removeAccessibleListener() {
		if (accessibleListener != null) {
			txtQuickAccess.getAccessible().removeAccessibleListener(accessibleListener);
			accessibleListener = null;
		}
	}

	/**
	 * Notifies <code>org.eclipse.swt.accessibility.Accessible</code> object that
	 * selected item has been changed.
	 */
	private void notifyAccessibleTextChanged() {
		if (table.getSelection().length == 0) {
			return;
		}
		TableItem item = table.getSelection()[0];
		selectedString = NLS.bind(QuickAccessMessages.QuickAccess_SelectedString, item.getText(0), item.getText(1));
		txtQuickAccess.getAccessible().sendEvent(ACC.EVENT_NAME_CHANGED, null);
	}

	private void restoreDialog() {
		IDialogSettings dialogSettings = getDialogSettings();
		if (dialogSettings != null) {
			try {
				dialogHeight = dialogSettings.getInt(DIALOG_HEIGHT);
				dialogWidth = dialogSettings.getInt(DIALOG_WIDTH);
			} catch (NumberFormatException e) {
				dialogHeight = -1;
				dialogWidth = -1;
			}

			/*
			 * add place holders, so that we don't change element order due to first
			 * restoring non-UI elements and then restoring UI elements
			 */
			String[] orderedProviders = dialogSettings.getArray(ORDERED_PROVIDERS);
			if (orderedProviders != null) {
				previousPicksList.addAll(Arrays.asList(new QuickAccessElement[orderedProviders.length]));
			}

			isLoadingPreviousElements = true;
				@Override
				public IStatus runInUIThread(IProgressMonitor monitor) {
					try {
						if (txtQuickAccess.isDisposed()) {
							return Status.OK_STATUS;
						}
						restoreDialogEntries(dialogSettings, true, monitor);
						quickAccessContents.refresh(txtQuickAccess.getText());
						List<QuickAccessElement> previousPicks = getLoadedPreviousPicks();
						previousPicksList.clear();
						previousPicksList.addAll(previousPicks);
					} finally {
						isLoadingPreviousElements = false;
