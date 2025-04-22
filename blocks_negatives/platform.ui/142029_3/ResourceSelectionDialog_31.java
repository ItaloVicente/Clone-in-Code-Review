                TableColumn[] columns = selectionGroup.getListTable()
                        .getColumns();
                for (TableColumn column : columns) {
                    column.pack();
                }
            }
        });

        return composite;
    }

    /**
     * Returns a content provider for <code>IResource</code>s that returns
     * only children of the given resource type.
     */
    private ITreeContentProvider getResourceProvider(final int resourceType) {
        return new WorkbenchContentProvider() {
            @Override
