        if (persistable != null) {
            IMemento frameMemento = memento.createChild(TAG_FRAME_INPUT);

            frameMemento.putString(TAG_FACTORY_ID, persistable.getFactoryId());
            persistable.saveState(frameMemento);

            if (expandedElements.length > 0) {
                IMemento expandedMem = memento.createChild(TAG_EXPANDED);
                saveElements(expandedElements, expandedMem);
            }
            if (selection instanceof IStructuredSelection) {
                Object[] elements = ((IStructuredSelection) selection)
                        .toArray();
                if (elements.length > 0) {
                    IMemento selectionMem = memento.createChild(TAG_SELECTION);
                    saveElements(elements, selectionMem);
                }
            }
        }
    }

    /**
     * Sets the input element.
     *
     * @param input the input element
     */
    public void setInput(Object input) {
        this.input = input;
    }

    /**
     * Sets the expanded elements.
     *
     * @param expandedElements the expanded elements
     */
    public void setExpandedElements(Object[] expandedElements) {
        this.expandedElements = expandedElements;
    }

    /**
     * Sets the selection.
     *
     * @param selection the selection
     */
    public void setSelection(ISelection selection) {
        this.selection = selection;
    }
