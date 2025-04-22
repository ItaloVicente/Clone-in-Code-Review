        if (isDisposed()) {
            return;
        }

        propChangeListeners.add(listener);
    }

    /**
     * @see IWorkbenchPart
     */
    @Override
	public void removePropertyListener(IPropertyListener listener) {
        if (isDisposed()) {
            return;
        }
        propChangeListeners.remove(listener);
    }
