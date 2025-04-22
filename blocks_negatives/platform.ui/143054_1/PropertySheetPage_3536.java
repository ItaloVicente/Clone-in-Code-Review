        if (sourcePart != null) {
        	sourcePart.getSite().getPage().removePartListener(partListener);
        	sourcePart = null;
        }

        if (selection instanceof IStructuredSelection) {
        	sourcePart = part;
            viewer.setInput(((IStructuredSelection) selection).toArray());
        }

        if (sourcePart != null) {
        	sourcePart.getSite().getPage().addPartListener(partListener);
        }
    }

    /**
     * The <code>PropertySheetPage</code> implementation of this <code>IPage</code> method
     * calls <code>makeContributions</code> for backwards compatibility with
     * previous versions of <code>IPage</code>.
     * <p>
     * Subclasses may reimplement.
     * </p>
     */
    @Override
