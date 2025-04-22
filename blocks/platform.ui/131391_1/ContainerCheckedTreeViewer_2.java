        
        ArrayList<Object> changedElements = new ArrayList<>(Arrays.asList(elements));
        changedElements.removeAll(Arrays.asList(oldCheckedElements));

        Control tree = getControl();
        try {
	        tree.setRedraw(false);
	        doCheckStateChanged(changedElements.toArray());
        }
        finally {
        	tree.setRedraw(true);
