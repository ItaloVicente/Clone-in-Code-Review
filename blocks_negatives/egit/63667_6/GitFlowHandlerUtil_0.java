		ISelection selection = HandlerUtil
				.getCurrentSelection(event);
		IStructuredSelection structuredSelection = SelectionUtils
				.getStructuredSelection(selection);
		Repository repository = SelectionUtils
				.getRepository(structuredSelection);

