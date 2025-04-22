		IStructuredSelection selection = (IStructuredSelection) HandlerUtil
				.getCurrentSelectionChecked(event);
		Object selected = selection.getFirstElement();

		Ref ref = getRef(selected);
		final Repository repository = getRepository(event);
