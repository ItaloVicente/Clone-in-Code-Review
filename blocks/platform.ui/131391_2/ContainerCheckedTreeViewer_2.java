

        Control tree = getControl();
        try {
			tree.setRedraw(false);
			if (oldCheckedElements.length > 0) {
				HashSet<Object> changedElements = new HashSet<>(Arrays.asList(elements));
				changedElements.removeAll(Arrays.asList(oldCheckedElements));
				doCheckStateChanged(changedElements.toArray());
			} else {
				doCheckStateChanged(elements);
			}
        }
        finally {
        	tree.setRedraw(true);
