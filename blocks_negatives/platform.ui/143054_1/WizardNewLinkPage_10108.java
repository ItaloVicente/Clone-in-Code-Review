            }
            selection = dialog.open();
        } else {
            DirectoryDialog dialog = new DirectoryDialog(getShell(), SWT.SHEET);
            if (store != null) {
                if (!store.fetchInfo().isDirectory()) {
                    linkTargetName = store.getParent().getName();
                }
                if (linkTargetName != null) {
                    dialog.setFilterPath(linkTargetName);
                }
            }
            dialog.setMessage(IDEWorkbenchMessages.WizardNewLinkPage_targetSelectionLabel);
            selection = dialog.open();
        }
        if (selection != null) {
            linkTargetField.setText(selection);
        }
    }

    /**
     * Opens a path variable selection dialog
     */
    private void handleVariablesButtonPressed() {
        PathVariableSelectionDialog dialog = new PathVariableSelectionDialog(
                getShell(), type);

        if (dialog.open() == IDialogConstants.OK_ID) {
            String[] variableNames = (String[]) dialog.getResult();

            if (variableNames != null) {
                IPathVariableManager pathVariableManager = ResourcesPlugin
                        .getWorkspace().getPathVariableManager();
                IPath path = pathVariableManager.getValue(variableNames[0]);

                if (path != null) {
                    linkTargetField.setText(path.toOSString());
                }
            }
        }
    }

    /**
     * Sets the container to use for link validation.
     * This should be the parent of the new resource that is being
     * linked.
     *
     * @param container the container to use for link validation.
     */
    public void setContainer(IContainer container) {
        this.container = container;
    }

    /**
     * Sets the value of the link target field
     *
     * @param target the value of the link target field
     */
    public void setLinkTarget(String target) {
        initialLinkTarget = target;
        if (linkTargetField != null && linkTargetField.isDisposed() == false) {
            linkTargetField.setText(target);
        }
    }

    /**
     * Validates the type of the given file against the link type specified
     * during page creation.
     *
     * @param linkTargetStore file to validate
     * @return boolean <code>true</code> if the link target type is valid
     * 	and <code>false</code> otherwise.
     */
    private boolean validateFileType(IFileStore linkTargetStore) {
        boolean valid = true;

        if (type == IResource.FILE && linkTargetStore.fetchInfo().isDirectory()) {
            setErrorMessage(IDEWorkbenchMessages.WizardNewLinkPage_linkTargetNotFile);
            valid = false;
        } else if (type == IResource.FOLDER
                && !linkTargetStore.fetchInfo().isDirectory()) {
            setErrorMessage(IDEWorkbenchMessages.WizardNewLinkPage_linkTargetNotFolder);
            valid = false;
        }
        return valid;
    }

    /**
     * Validates the name of the link target
     *
     * @param linkTargetName link target name to validate
     * @return boolean <code>true</code> if the link target name is valid
     * 	and <code>false</code> otherwise.
     */
    private boolean validateLinkTargetName(String linkTargetName) {
        boolean valid = true;

            setErrorMessage(IDEWorkbenchMessages.WizardNewLinkPage_linkTargetEmpty);
            valid = false;
        } else {
            if (path.isValidPath(linkTargetName) == false) {
                setErrorMessage(IDEWorkbenchMessages.WizardNewLinkPage_linkTargetInvalid);
                valid = false;
            }
        }
        return valid;
    }

    /**
     * Returns whether this page's controls currently all contain valid
     * values.
     *
     * @return <code>true</code> if all controls are valid, and
     *   <code>false</code> if at least one is invalid
     */
    private boolean validatePage() {
        boolean valid = true;
        IWorkspace workspace = IDEWorkbenchPlugin.getPluginWorkspace();

        if (createLink) {
            String linkTargetName = linkTargetField.getText();

            valid = validateLinkTargetName(linkTargetName);
            if (valid) {
                IFileStore linkTargetFile = IDEResourceInfoUtils.getFileStore(linkTargetName);
                if (linkTargetFile == null || !linkTargetFile.fetchInfo().exists()) {
                    setErrorMessage(IDEWorkbenchMessages.WizardNewLinkPage_linkTargetNonExistent);
                    valid = false;
                } else {
                    IStatus locationStatus = workspace.validateLinkLocation(
                            container, new Path(linkTargetName));

                    if (locationStatus.isOK() == false) {
                        setErrorMessage(IDEWorkbenchMessages.WizardNewLinkPage_linkTargetLocationInvalid);
                        valid = false;
                    } else {
                        valid = validateFileType(linkTargetFile);
                    }
                }
            }
        }
        if (valid) {
            setMessage(null);
            setErrorMessage(null);
        }
        return valid;
    }
