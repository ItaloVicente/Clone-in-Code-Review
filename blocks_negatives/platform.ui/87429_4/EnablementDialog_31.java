            viewer.addCheckStateListener(new ICheckStateListener() {

                @Override
				public void checkStateChanged(CheckStateChangedEvent event) {
                    if (event.getChecked()) {
						activitiesToEnable.add(event.getElement());
					} else {
						activitiesToEnable.remove(event.getElement());
					}

                    getButton(Window.OK).setEnabled(
                            !activitiesToEnable.isEmpty());
                }
            });
            viewer.addSelectionChangedListener(new ISelectionChangedListener() {
                @Override
				public void selectionChanged(SelectionChangedEvent event) {
                    selectedActivity = (String) ((IStructuredSelection) event
                            .getSelection()).getFirstElement();
                    setDetails();
                }
            });
