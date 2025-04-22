				new PropertyChangeEvent(controller.getConflictModel(),
						ConflictModel.PROP_CONFLICTS, null, conflicts),
				new PropertyChangeEvent(controller.getConflictModel(),
						CommonModel.PROP_SELECTED_ELEMENT, null, conflict1),
				new PropertyChangeEvent(controller.getContextModel(),
						CommonModel.PROP_SELECTED_ELEMENT, null, conflict1
								.getContext()),
				new PropertyChangeEvent(controller.getBindingModel(),
						CommonModel.PROP_SELECTED_ELEMENT, null, conflict1) };
