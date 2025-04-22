        }

        return false;
    }

    /**
     * Persists additional setting that are to be restored in the next instance of
     * this page.
     * <p>
     * The <code>WizardImportPage</code> implementation of this method does
     * nothing. Subclasses may extend to persist additional settings.
     * </p>
     */
    protected void internalSaveWidgetValues() {
    }

    /**
     * Queries the user for the resource types that are to be exported and returns
     * these types as an array.
     *
     * @return the resource types selected for export (element type:
     *   <code>String</code>), or <code>null</code> if the user canceled the
     *   selection
     */
    protected Object[] queryResourceTypesToExport() {

        TypeFilteringDialog dialog = new TypeFilteringDialog(getContainer()
                .getShell(), getTypesToExport());

        dialog.open();

        return dialog.getResult();
    }

    /**
     * Restores resource specification control settings that were persisted
     * in the previous instance of this page. Subclasses wishing to restore
     * persisted values for their controls may extend.
     */
    protected void restoreResourceSpecificationWidgetValues() {
    }

    /**
     * Persists resource specification control setting that are to be restored
     * in the next instance of this page. Subclasses wishing to persist additional
     * setting for their controls should extend hook method
     * <code>internalSaveWidgetValues</code>.
     */
    @Override
