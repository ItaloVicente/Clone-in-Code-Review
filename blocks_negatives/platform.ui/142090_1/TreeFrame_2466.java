        }
        if (frameInput != null) {
            input = frameInput;
        }
        IMemento expandedMem = memento.getChild(TAG_EXPANDED);
        if (expandedMem != null) {
            List<IAdaptable> elements = restoreElements(expandedMem);
            expandedElements = elements.toArray(new Object[elements.size()]);
        } else {
            expandedElements = new Object[0];
        }
        IMemento selectionMem = memento.getChild(TAG_SELECTION);
        if (selectionMem != null) {
            List<IAdaptable> elements = restoreElements(selectionMem);
            selection = new StructuredSelection(elements);
        } else {
            selection = StructuredSelection.EMPTY;
        }
    }
