		} else if (client != null) {
			if (part.getTransientData().get(E4PartWrapper.E4_WRAPPER_KEY) instanceof E4PartWrapper) {
				IWorkbenchPart workbenchPart = (IWorkbenchPart) part.getTransientData()
						.get(E4PartWrapper.E4_WRAPPER_KEY);
				final IWorkbenchPartReference partReference = getReference(workbenchPart);

				if (partReference != null) {
					for (final Object listener : partListenerList.getListeners()) {
						SafeRunner.run(new SafeRunnable() {
							@Override
							public void run() throws Exception {
								((IPartListener) listener).partDeactivated(workbenchPart);
							}
						});
					}

					for (final Object listener : partListener2List.getListeners()) {
						SafeRunner.run(new SafeRunnable() {
							@Override
							public void run() throws Exception {
								((IPartListener2) listener).partDeactivated(partReference);
							}
						});
					}
				}
			}
