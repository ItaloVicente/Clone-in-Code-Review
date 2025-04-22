    }

    private int getInitialRadioSelection() {
    		IWorkingSet windowSet = workbenchWindow.getActivePage().getAggregateWorkingSet();

    		int selectionIndex;
    		if (getSelection() != null && getSelection().length > 0) {
    			if (windowSet.equals(getSelection()[0])) {
    				selectionIndex = 0;
    			}
    			else {
    				selectionIndex = 2;
    			}
    		}
    		else {
    			selectionIndex = 1;
    		}
