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

