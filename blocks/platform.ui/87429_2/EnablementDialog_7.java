            viewer.addCheckStateListener(event -> {
			    if (event.getChecked()) {
					activitiesToEnable.add(event.getElement());
				} else {
					activitiesToEnable.remove(event.getElement());
				}

			    getButton(Window.OK).setEnabled(
			            !activitiesToEnable.isEmpty());
			});
            viewer.addSelectionChangedListener(event -> {
			    selectedActivity = (String) ((IStructuredSelection) event
			            .getSelection()).getFirstElement();
			    setDetails();
			});
