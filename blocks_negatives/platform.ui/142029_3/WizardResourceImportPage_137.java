        	return (IContainer) resource;

        }


        return null;
    }

    /**
     * Returns a collection of the currently-specified resource types for
     * use by the type selection dialog.
     */
    protected java.util.List getTypesToImport() {

        return selectedTypes;
    }

    /**
     * Opens a container selection dialog and displays the user's subsequent
     * container resource selection in this page's container name field.
     */
    protected void handleContainerBrowseButtonPressed() {
        IPath containerPath = queryForContainer(getSpecifiedContainer(),
                IDEWorkbenchMessages.WizardImportPage_selectFolderLabel,
                IDEWorkbenchMessages.WizardImportPage_selectFolderTitle);

            setErrorMessage(null);
            containerNameField.setText(containerPath.makeRelative().toString());
        }
    }

    /**
     * The <code>WizardResourceImportPage</code> implementation of this
     * <code>Listener</code> method handles all events and enablements for controls
     * on this page. Subclasses may extend.
     * @param event Event
     */
    @Override
