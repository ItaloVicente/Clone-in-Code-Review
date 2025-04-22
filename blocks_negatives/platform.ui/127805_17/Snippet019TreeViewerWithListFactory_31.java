		IObservableValue treeViewerSelectionObserveSelection = ViewersObservables
				.observeSingleSelection(beanViewer);
		IObservableValue textTextObserveWidget = WidgetProperties.text(SWT.Modify).observe(beanText);
		IObservableValue treeViewerValueObserveDetailValue = BeanProperties.value(
				(Class) treeViewerSelectionObserveSelection.getValueType(), "text", String.class).observeDetail(
				treeViewerSelectionObserveSelection);
