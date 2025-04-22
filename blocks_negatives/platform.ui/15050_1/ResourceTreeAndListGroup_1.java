     *	Handle the selection of an item in the tree viewer
     *
     *	@param event SelectionChangedEvent
     */
    public void selectionChanged(SelectionChangedEvent event) {
        IStructuredSelection selection = (IStructuredSelection) event
                .getSelection();
        Object selectedElement = selection.getFirstElement();
        if (selectedElement == null) {
            currentTreeSelection = null;
            listViewer.setInput(currentTreeSelection);
            return;
        }

        if (selectedElement != currentTreeSelection) {
			populateListViewer(selectedElement);
		}

        currentTreeSelection = selectedElement;
    }

    /**
     * Select or deselect all of the elements in the tree depending on the value of the selection
