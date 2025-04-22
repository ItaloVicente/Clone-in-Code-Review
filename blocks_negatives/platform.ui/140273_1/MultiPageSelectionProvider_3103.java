        }
        return StructuredSelection.EMPTY;
    }

    @Override
	public void removeSelectionChangedListener(
            ISelectionChangedListener listener) {
        listeners.remove(listener);
    }

    /**
     * Removes a listener for post selection changes in this multi page selection provider.
     *
     * @param listener a selection changed listener
     * @since 3.2
     */
    @Override
