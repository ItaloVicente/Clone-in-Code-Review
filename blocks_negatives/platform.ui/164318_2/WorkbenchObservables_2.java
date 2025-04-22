		return new ListeningValue<IWorkbenchPartReference>() {
			private final IPartListener2 listener = new IPartListener2() {
				@Override
				public void partActivated(IWorkbenchPartReference partRef) {
					protectedSetValue(partRef);
				}

				@Override
				public void partDeactivated(IWorkbenchPartReference partRef) {
					if (partRef == doGetValue()) {
						protectedSetValue(null);
					}
				}

				@Override
				public void partBroughtToTop(IWorkbenchPartReference partRef) {
				}

				@Override
				public void partClosed(IWorkbenchPartReference partRef) {
				}

				@Override
				public void partOpened(IWorkbenchPartReference partRef) {
				}

				@Override
				public void partHidden(IWorkbenchPartReference partRef) {
				}

				@Override
				public void partVisible(IWorkbenchPartReference partRef) {
				}

				@Override
				public void partInputChanged(IWorkbenchPartReference partRef) {
				}
			};

			@Override
			protected void startListening() {
				partService.addPartListener(listener);
			}

			@Override
			protected void stopListening() {
				partService.removePartListener(listener);
			}

			@Override
			protected IWorkbenchPartReference calculate() {
				return partService.getActivePartReference();
			}
		};
