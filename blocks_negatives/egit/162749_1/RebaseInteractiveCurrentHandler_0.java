		super.execute(event);
		try {
			RebaseInteractiveView rebaseInteractiveView = (RebaseInteractiveView) HandlerUtil
					.getActiveWorkbenchWindowChecked(event).getActivePage()
					.showView(RebaseInteractiveView.VIEW_ID);
			rebaseInteractiveView.setInput(repository);
		} catch (PartInitException e) {
			Activator.showError(e.getMessage(), e);
		}
		return null;
