	private void addCopyHandler(final TreeViewer viewer) {
		IFocusService focusService = CommonUtils.getService(getSite(),
				IFocusService.class);
		IHandlerService handlerService = CommonUtils.getService(getSite(),
				IHandlerService.class);
		focusService.addFocusTracker(viewer.getControl(),
				UNSTAGED_VIEW_ID);
		IHandlerActivation activateHandler = handlerService.activateHandler(
				IWorkbenchCommandConstants.EDIT_COPY,
				new CopyHandler(viewer),
				new ControlExpression(viewer.getControl()));
		viewer.getControl().addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				handlerService.deactivateHandler(activateHandler);
			}
		});
	}

