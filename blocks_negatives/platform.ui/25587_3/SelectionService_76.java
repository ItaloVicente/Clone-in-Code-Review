			handlePostSelectionChanged(part, selection, true);
		}
	};

	private void handleSelectionChanged(MPart part, Object selection, boolean targeted) {
		selection = createCompatibilitySelection(selection);
		context.set(ISources.ACTIVE_CURRENT_SELECTION_NAME, selection);

		IEclipseContext applicationContext = application.getContext();
		if (applicationContext.getActiveChild() == context) {
			application.getContext().set(ISources.ACTIVE_CURRENT_SELECTION_NAME, selection);
		}

		Object client = part.getObject();
		if (client instanceof CompatibilityPart) {
			IWorkbenchPart workbenchPart = ((CompatibilityPart) client).getPart();
			if (targeted) {
				notifyListeners(workbenchPart, (ISelection) selection, part.getElementId(),
						targetedListeners);
			} else {
				notifyListeners(workbenchPart, (ISelection) selection, listeners);
			}
		}
	}
