        boolean refreshNeeded = internalSetWorkingSet(workingSet);

        workingSetFilter.setWorkingSet(emptyWorkingSet ? null : workingSet);
        if (workingSet != null) {
            settings.put(STORE_WORKING_SET, workingSet.getName());
        } else {
            settings.put(STORE_WORKING_SET, ""); //$NON-NLS-1$
        }
        updateTitle();
        if(refreshNeeded) {
        	treeViewer.refresh();
        }
        treeViewer.setExpandedElements(expanded);
