        categoryViewer
                .addSelectionChangedListener(event -> {
				    ICategory element = (ICategory) ((IStructuredSelection) event
				            .getSelection()).getFirstElement();
				    setDetails(element);
				});
