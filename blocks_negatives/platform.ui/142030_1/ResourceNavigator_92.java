        ViewerSorter sorter = getTreeViewer().getSorter();
        if (sorter instanceof ResourceSorter) {
        	return (ResourceSorter) sorter;
        }
        return null;
    }

    /**
     * Returns the comparator.  If a sorter was set using
