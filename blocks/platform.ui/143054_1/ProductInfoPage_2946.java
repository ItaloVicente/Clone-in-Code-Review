
	protected final void addCopySupport(Table table) {
		CopyTableSelectionHandler handler = new CopyTableSelectionHandler();
		Menu menu = new Menu(table);
		MenuItem copyItem = new MenuItem(menu, SWT.NONE);
		copyItem.addSelectionListener(SelectionListener.widgetSelectedAdapter(e -> handler.copySelection(table)));
		copyItem.setText(JFaceResources.getString("copy")); //$NON-NLS-1$
		copyItem.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_TOOL_COPY));

		table.setMenu(menu);

		IFocusService fs = PlatformUI.getWorkbench().getService(IFocusService.class);
		IHandlerService hs = PlatformUI.getWorkbench().getService(IHandlerService.class);
		if (fs != null && hs != null) {
			fs.addFocusTracker(table, getClass().getName() + ".table"); //$NON-NLS-1$
			IHandlerActivation handlerActivation = hs.activateHandler(IWorkbenchCommandConstants.EDIT_COPY, handler,
					controlFocusedExpression(table));
			table.addDisposeListener(e -> hs.deactivateHandler(handlerActivation));
		}
	}

	private static Expression controlFocusedExpression(Control control) {
		return new Expression() {
			@Override
			public EvaluationResult evaluate(IEvaluationContext context) {
				return context.getVariable(ISources.ACTIVE_FOCUS_CONTROL_NAME) == control ? EvaluationResult.TRUE
						: EvaluationResult.FALSE;
			}

			@Override
			public void collectExpressionInfo(ExpressionInfo info) {
				info.addVariableNameAccess(ISources.ACTIVE_FOCUS_CONTROL_NAME);
			}
		};
	}
