			ViewerSupport.bind(fatherComboViewer, viewModel.getPeople(),
					BeanProperties.value("name"));
			bindingContext.bindValue(ViewersObservables
					.observeSingleSelection(fatherComboViewer),
					BeanProperties.value((Class) selection.getValueType(), "father", Person.class)
					.observeDetail(selection));
