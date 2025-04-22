			Object selectedElement = selection.getFirstElement();
			if (selectedElement == null) {
				currentTreeSelection = null;
				listViewer.setInput(currentTreeSelection);
				return;
			}

			if (selectedElement != currentTreeSelection) {
