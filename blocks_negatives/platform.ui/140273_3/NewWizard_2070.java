        }
        return null;
    }

    /**
     * Lazily create the wizards pages
     * @param aWorkbench the workbench
     * @param currentSelection the current selection
     */
    public void init(IWorkbench aWorkbench,
            IStructuredSelection currentSelection) {
        this.workbench = aWorkbench;
        this.selection = currentSelection;
