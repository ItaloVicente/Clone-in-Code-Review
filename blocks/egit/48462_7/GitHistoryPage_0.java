			Object useAsInput = object;

			ISelection selection = getSite().getPage().getSelection();
			if (selection instanceof IStructuredSelection) {
				IStructuredSelection structuredSelection = (IStructuredSelection) selection;
				HistoryPageInput mostFittingInput = SelectionUtils
						.getMostFittingInput(structuredSelection, object);
				if (mostFittingInput != null) {
					useAsInput = mostFittingInput;
				}
			}

