    }

    /**
     *	Reveal and select the passed element selection in self's visual component
     */
    public void selectReveal(ISelection selection) {
        StructuredSelection ssel = convertSelection(selection);
        if (!ssel.isEmpty()) {
            getViewer().setSelection(ssel, true);
        }
    }

    /**
     * @see IWorkbenchPart#setFocus()
     */
    @Override
