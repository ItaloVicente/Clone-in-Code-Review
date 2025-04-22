        categoryViewer = new CheckboxTableViewer(table);
        categoryViewer.getControl().setLayoutData(
                new GridData(GridData.FILL_BOTH));
        categoryViewer.setContentProvider(new CategoryContentProvider());
        CategoryLabelProvider categoryLabelProvider = new CategoryLabelProvider(
                true);
        workingCopy.addActivityManagerListener(categoryLabelProvider);
        categoryViewer.setLabelProvider(categoryLabelProvider);
        categoryViewer.setComparator(new ViewerComparator());
        categoryViewer.addFilter(new EmptyCategoryFilter());

        categoryViewer
                .addSelectionChangedListener(new ISelectionChangedListener() {

                    /*
                     * (non-Javadoc)
                     * 
                     * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
                     */
                    public void selectionChanged(SelectionChangedEvent event) {
                        ICategory element = (ICategory) ((IStructuredSelection) event
                                .getSelection()).getFirstElement();
                        setDetails(element);
                    }
                });
        categoryViewer.setInput(workingCopy.getDefinedCategoryIds());

		updateCategoryCheckState(); 
    }
