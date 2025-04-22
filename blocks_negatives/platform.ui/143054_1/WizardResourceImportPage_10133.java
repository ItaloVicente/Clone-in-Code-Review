            setErrorMessage(IDEWorkbenchMessages.WizardImportPage_projectNotExist);
            return false;
        }
        if (!container.isAccessible()) {
             setErrorMessage(INACCESSABLE_FOLDER_MESSAGE);
             return false;
        }
        if (container.getLocationURI() == null) {
          if (container.isLinked()) {
               setErrorMessage(IDEWorkbenchMessages.WizardImportPage_undefinedPathVariable);
          } else {
               setErrorMessage(IDEWorkbenchMessages.WizardImportPage_containerNotExist);
          }
         return false;
        }


        if (sourceConflictsWithDestination(containerPath)) {
            setErrorMessage(getSourceConflictMessage());
            return false;
        }

        if (container instanceof IWorkspaceRoot){
        	setErrorMessage(EMPTY_PROJECT_MESSAGE);
        	return false;
        }
        return true;

    }

    /**
     * Returns the error message for when the source conflicts
     * with the destination.
     */
    protected final String getSourceConflictMessage() {
        return (IDEWorkbenchMessages.WizardImportPage_importOnReceiver);
    }

    /**
     * Returns whether or not the source location conflicts
     * with the destination resource. By default this is not
     * checked, so <code>false</code> is returned.
     *
     * @param sourcePath the path being checked
     * @return <code>true</code> if the source location conflicts with the
     *   destination resource, <code>false</code> if not
     */
    protected boolean sourceConflictsWithDestination(IPath sourcePath) {
        return false;
    }

    /*
     * @see WizardDataTransferPage.determinePageCompletion.
     */
    @Override
