    		if (buttonWindowSet.getSelection()) {
    			IWorkingSet [] windowSet = new IWorkingSet[] {workbenchWindow.getActivePage().getAggregateWorkingSet()};
    			setSelection(windowSet);
    			setResult(Arrays.asList(getSelection()));
    		}
    		else if (buttonNoSet.getSelection()) {
