				new PropertyChangeEvent(conflict2, BindingElement.PROP_CONFLICT, Boolean.TRUE, Boolean.FALSE),
				new PropertyChangeEvent(conflict2, BindingElement.PROP_CONTEXT, dialog, window),
				new PropertyChangeEvent(conflict2, BindingElement.PROP_USER_DELTA, Integer.valueOf(Binding.SYSTEM),
						Integer.valueOf(Binding.USER)),
				new PropertyChangeEvent(conflict2, ModelElement.PROP_MODEL_OBJECT, c2model, conflict2.getModelObject()),
				new PropertyChangeEvent(cm, CommonModel.PROP_SELECTED_ELEMENT, dialog, window), };
