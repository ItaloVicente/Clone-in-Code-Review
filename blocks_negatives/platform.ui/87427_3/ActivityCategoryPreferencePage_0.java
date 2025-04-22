                .addSelectionChangedListener(new ISelectionChangedListener() {

                    @Override
                    public void selectionChanged(SelectionChangedEvent event) {
                        ICategory element = (ICategory) ((IStructuredSelection) event
                                .getSelection()).getFirstElement();
                        setDetails(element);
                    }
                });
