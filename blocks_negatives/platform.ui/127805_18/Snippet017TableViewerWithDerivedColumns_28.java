			ViewerSupport.bind(mothercomboViewer, viewModel.getPeople(),
					BeanProperties.value("name"));
			bindingContext.bindValue(ViewersObservables
					.observeSingleSelection(mothercomboViewer), BeanProperties
					.value((Class) selection.getValueType(), "mother", Person.class)
					.observeDetail(selection));
