		StringJoiner joiner = new StringJoiner(" - "); //$NON-NLS-1$
		StringJoiner sj = new StringJoiner(" - "); //$NON-NLS-1$
		{
			String workspaceLocation = wbAdvisor.getWorkspaceLocation();
			if (workspaceLocation != null) {
				sj.add(workspaceLocation);
			}

			IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
			IWorkbenchPage currentPage = configurer.getWindow().getActivePage();
			IEditorPart activeEditor = null;
			if (currentPage != null) {
				activeEditor = lastActiveEditor;
			}
			if (currentPage != null) {
				if (activeEditor != null) {
					sj.add(activeEditor.getTitleToolTip());
				}
			}
			IProduct product = Platform.getProduct();
			if (product != null) {
				sj.add(product.getName());
			}
		}
