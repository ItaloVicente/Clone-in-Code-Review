		whService.activateHandler(IWorkbenchCommandConstants.EDIT_SELECT_ALL, new AbstractHandler() {
			@Override
			public Object execute(ExecutionEvent event) {
				txtQuickAcesss.selectAll();
				return null;
			}
		}, focusExpr);
