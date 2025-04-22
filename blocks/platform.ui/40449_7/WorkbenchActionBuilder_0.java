		partListener = new IPartListener() {

			@Override
			public void partOpened(IWorkbenchPart part) {
			}

			@Override
			public void partDeactivated(IWorkbenchPart part) {
			}

			@Override
			public void partClosed(IWorkbenchPart part) {
			}

			@Override
			public void partActivated(IWorkbenchPart part) {
			}

			@Override
			public void partBroughtToTop(IWorkbenchPart part) {
				if (!(part instanceof IEditorPart)) {
					return;
				}
				ICommandService commandService = window.getService(ICommandService.class);
				commandService.refreshElements(IWorkbenchCommandConstants.WINDOW_PIN_EDITOR, null);
			}

		};
		getWindow().getPartService().addPartListener(partListener);

