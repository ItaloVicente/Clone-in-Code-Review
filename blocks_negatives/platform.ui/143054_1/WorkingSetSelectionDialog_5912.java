                editedWorkingSet.setElements(originalWorkingSet.getElements());
            }
        }
    }

    /**
     * Adds back removed working sets to the working set manager.
     */
    private void restoreRemovedWorkingSets() {
        IWorkingSetManager manager = WorkbenchPlugin.getDefault()
                .getWorkingSetManager();
        Iterator iterator = getRemovedWorkingSets().iterator();

        while (iterator.hasNext()) {
            manager.addWorkingSet(((IWorkingSet) iterator.next()));
        }
        iterator = getRemovedMRUWorkingSets().iterator();
        while (iterator.hasNext()) {
            manager.addRecentWorkingSet(((IWorkingSet) iterator.next()));
        }
    }

    /**
     * Implements IWorkingSetSelectionDialog.
     *
     * @see org.eclipse.ui.dialogs.IWorkingSetSelectionDialog#setSelection(IWorkingSet[])
     */
    @Override
