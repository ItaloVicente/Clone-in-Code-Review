        SimpleListContentProvider contentsProvider = new SimpleListContentProvider();
        contentsProvider
                .setElements(sortedDifference(allProjects, currentItems));

        ListSelectionDialog dialog = new ListSelectionDialog(this.getShell(),
                this, contentsProvider, labelProvider,
