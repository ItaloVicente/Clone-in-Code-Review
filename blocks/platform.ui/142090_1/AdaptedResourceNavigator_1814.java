		getViewer().getTree().setFocus();
	}

	public void setLabelDecorator(ILabelDecorator decorator) {
		DecoratingLabelProvider provider = (DecoratingLabelProvider) getViewer()
				.getLabelProvider();
		if (decorator == null) {
			IDecoratorManager manager = getSite().getWorkbenchWindow()
					.getWorkbench().getDecoratorManager();
			provider.setLabelDecorator(manager.getLabelDecorator());
		} else {
			provider.setLabelDecorator(decorator);
		}
	}

	void updateStatusLine(IStructuredSelection selection) {
		String msg = getStatusLineMessage(selection);
		getViewSite().getActionBars().getStatusLineManager().setMessage(msg);
	}

	void updateTitle() {
		Object input = getViewer().getInput();
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		if (input == null || input.equals(workspace)
				|| input.equals(workspace.getRoot())) {
			setContentDescription(""); //$NON-NLS-1$
			setTitleToolTip(""); //$NON-NLS-1$
		} else {
			ILabelProvider labelProvider = (ILabelProvider) getViewer()
					.getLabelProvider();
			setContentDescription(labelProvider.getText(input));
			setTitleToolTip(getToolTipText(input));
		}
	}
