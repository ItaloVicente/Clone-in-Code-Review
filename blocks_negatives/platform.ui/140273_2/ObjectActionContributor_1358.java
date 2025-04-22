            }
        }
    }

    /**
     * Contributes actions applicable for the current selection.
     */
    @Override
	public boolean contributeObjectActions(final IWorkbenchPart part,
            IMenuManager menu, ISelectionProvider selProv,
            List actionIdOverrides) {
        if (!configRead) {
