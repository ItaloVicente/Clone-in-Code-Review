				new PropertyChangeEvent(conflict4, BindingElement.PROP_CONTEXT,
						null, window),
				new PropertyChangeEvent(conflict4,
						BindingElement.PROP_USER_DELTA, Integer.valueOf(
								Binding.SYSTEM), Integer.valueOf(Binding.USER)),
				new PropertyChangeEvent(cm, CommonModel.PROP_SELECTED_ELEMENT,
						null, conflict4.getContext()),
				new PropertyChangeEvent(conflict4,
						ModelElement.PROP_MODEL_OBJECT, c4model, conflict4
								.getModelObject()),
				new PropertyChangeEvent(conflict4, BindingElement.PROP_TRIGGER,
						null, ctrl5), };
