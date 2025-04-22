
	public static IObservableValue<IWorkbenchWindow> observeActiveWorkbenchWindow(IWorkbench workbench) {
		return new WritableValue<IWorkbenchWindow>(null, IWorkbenchWindow.class) {
			private final IWindowListener listener = new IWindowListener() {
				@Override
				public void windowActivated(IWorkbenchWindow window) {
					setValue(window);
				}

				@Override
				public void windowDeactivated(IWorkbenchWindow window) {
					if (window == doGetValue())
						setValue(null);
				}

				@Override
				public void windowClosed(IWorkbenchWindow window) {
				}

				@Override
				public void windowOpened(IWorkbenchWindow window) {
				}
			};

			@Override
			protected void firstListenerAdded() {
				workbench.addWindowListener(listener);
				setValue(workbench.getActiveWorkbenchWindow());
			}

			@Override
			protected void lastListenerRemoved() {
				workbench.removeWindowListener(listener);
			}
		};
	}

	public static IObservableValue<IWorkbenchPage> observeActiveWorkbenchWindow(IWorkbenchWindow window) {
		return new WritableValue<IWorkbenchPage>(null, IWorkbenchPage.class) {
			private final IPageListener listener = new IPageListener() {
				@Override
				public void pageActivated(IWorkbenchPage page) {
					setValue(page);
				}

				@Override
				public void pageClosed(IWorkbenchPage page) {
					if (page == doGetValue())
						setValue(null);
				}

				@Override
				public void pageOpened(IWorkbenchPage page) {
				}
			};

			@Override
			protected void firstListenerAdded() {
				setValue(window.getActivePage());
				window.addPageListener(listener);
			}

			@Override
			protected void lastListenerRemoved() {
				window.removePageListener(listener);
			}
		};
	}

	public static IObservableValue<IWorkbenchPartReference> observeActivePart(IPartService partService) {
		return new WritableValue<IWorkbenchPartReference>(null, IWorkbenchPartReference.class) {
			private final IPartListener2 listener = new IPartListener2() {
				@Override
				public void partActivated(IWorkbenchPartReference partRef) {
					setValue(partRef);
				}

				@Override
				public void partDeactivated(IWorkbenchPartReference partRef) {
					if (partRef == doGetValue())
						setValue(null);
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
			protected void firstListenerAdded() {
				setValue(partService.getActivePartReference());
				partService.addPartListener(listener);
			}

			@Override
			protected void lastListenerRemoved() {
				partService.removePartListener(listener);
			}
		};
	}

	public static IObservableValue<IEditorReference> observeActiveEditor(IPartService partService) {
		return new ComputedValue<IEditorReference>(IEditorReference.class) {
			final IObservableValue<IWorkbenchPartReference> partObservable = observeActivePart(partService);

			@Override
			protected IEditorReference calculate() {
				IWorkbenchPartReference value = partObservable.getValue();
				return value instanceof IEditorReference ? (IEditorReference) value : null;
			}

			@Override
			public synchronized void dispose() {
				partObservable.dispose();
				super.dispose();
			}
		};
	}
