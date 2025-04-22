			Object input = null;
			IWorkbenchPart part = getPart(event);
			if (part instanceof IHistoryView) {
				input = ((IHistoryView) part).getHistoryPage().getInput();
			}

			if (input != null && input instanceof IFile) {
				IFile resource = (IFile) input;
