        ISelection sel = event.getSelection();
        selectionChanged(sel);
    }

    /**
     * The <code>SelectionChangedEventAction</code> implementation of this
     * <code>ISelectionListener</code> method calls
     * <code>selectionChanged(IStructuredSelection)</code> when the selection is
     * a structured one. Subclasses may extend this method to react to the change.
     */
    @Override
