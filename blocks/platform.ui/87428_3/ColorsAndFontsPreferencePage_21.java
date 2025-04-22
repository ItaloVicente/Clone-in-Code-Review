		tree.getViewer().addDoubleClickListener(event -> {
			IStructuredSelection s = (IStructuredSelection) event.getSelection();
			Object element = s.getFirstElement();
			if (tree.getViewer().isExpandable(element))
				tree.getViewer().setExpandedState(element,
						!tree.getViewer().getExpandedState(element));

			if (element instanceof ThemeElementDefinition) {
				ThemeElementDefinition definition = (ThemeElementDefinition) element;

				if (element instanceof FontDefinition) {
					editFont(tree.getDisplay());
				} else if (element instanceof ColorDefinition
						&& isAvailableInCurrentTheme(definition)) {
					editColor(tree.getDisplay());
