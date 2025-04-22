
	public static IObservableValue<IWorkbenchWindow> observeActiveWorkbenchWindow(IWorkbench workbench) {
		Assert.isNotNull(workbench);
		return new ListeningValue<IWorkbenchWindow>() {
			private final IWindowListener listener = new IWindowListener() {
				@Override
				public void windowActivated(IWorkbenchWindow window) {
					setValue(window);
				}

				@Override
				public void windowDeactivated(IWorkbenchWindow window) {
					if (window == doGetValue()) {
						setValue(null);
					}
				}

				@Override
				public void windowClosed(IWorkbenchWindow window) {
				}

				@Override
				public void windowOpened(IWorkbenchWindow window) {
				}
			};

			@Override
			protected void startListening() {
				workbench.addWindowListener(listener);
			}

			@Override
			protected void stopListening() {
				workbench.removeWindowListener(listener);
			}

			@Override
			protected IWorkbenchWindow calculate() {
				return workbench.getActiveWorkbenchWindow();
			}
		};
	}

	public static IObservableValue<IWorkbenchPage> observeActiveWorkbenchPage(IWorkbenchWindow window) {
		Assert.isNotNull(window);
		return new ListeningValue<IWorkbenchPage>() {
			private final IPageListener listener = new IPageListener() {
				@Override
				public void pageActivated(IWorkbenchPage page) {
					setValue(page);
				}

				@Override
				public void pageClosed(IWorkbenchPage page) {
					if (page == doGetValue()) {
						setValue(null);
					}
				}

				@Override
				public void pageOpened(IWorkbenchPage page) {
				}
			};

			@Override
			protected void firstListenerAdded() {
				window.addPageListener(listener);
			}

			@Override
			protected void lastListenerRemoved() {
				window.removePageListener(listener);
			}

			@Override
			protected void startListening() {
				window.addPageListener(listener);
			}

			@Override
			protected void stopListening() {
				window.removePageListener(listener);
			}

			@Override
			protected IWorkbenchPage calculate() {
				return window.getActivePage();
			}
		};
	}

	public static IObservableValue<IWorkbenchPartReference> observeActivePart(IPartService partService) {
		Assert.isNotNull(partService);
		return new ListeningValue<IWorkbenchPartReference>() {
			private final IPartListener2 listener = new IPartListener2() {
				@Override
				public void partActivated(IWorkbenchPartReference partRef) {
					setValue(partRef);
				}

				@Override
				public void partDeactivated(IWorkbenchPartReference partRef) {
					if (partRef == doGetValue()) {
						setValue(null);
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
	}

	public static IObservableValue<IEditorReference> observeActiveEditor(IPartService partService) {
		final IObservableValue<IWorkbenchPartReference> partObservable = observeActivePart(partService);
		return ComputedValue.create(() -> {
			IWorkbenchPartReference value = partObservable.getValue();
			return value instanceof IEditorReference ? (IEditorReference) value : null;
		});
	}

	public static IObservableValue<IEditorInput> observeEditorInput(IEditorPart editor) {
		Assert.isNotNull(editor);
		return new ListeningValue<IEditorInput>() {
			private final IPropertyListener listener = new IPropertyListener() {
				@Override
				public void propertyChanged(Object source, int propId) {
					if (propId == IWorkbenchPartConstants.PROP_INPUT) {
						setValue(editor.getEditorInput());
					}
				}
			};

			@Override
			protected void startListening() {
				editor.addPropertyListener(listener);
			}

			@Override
			protected void stopListening() {
				editor.removePropertyListener(listener);
			}

			@Override
			protected IEditorInput calculate() {
				return editor.getEditorInput();
			}
		};
	}

	private abstract static class ListeningValue<T> extends AbstractObservableValue<T> {
		private T value;
		private boolean isListening;
		private volatile boolean hasListeners;

		@Override
		protected T doGetValue() {
			if (isListening) {
				return value;
			}
			return calculate();
		}

		@Override
		protected void doSetValue(T value) {
			if (this.value != value) {
				fireValueChange(Diffs.createValueDiff(this.value, this.value = value));
			}
		}

		@Override
		protected void firstListenerAdded() {
			if (getRealm().isCurrent()) {
				startListeningInternal();
			} else {
				getRealm().asyncExec(() -> {
					if (hasListeners && !isListening) {
						startListeningInternal();
					}
				});
			}
			hasListeners = true;
			super.firstListenerAdded();
		}

		@Override
		protected void lastListenerRemoved() {
			if (getRealm().isCurrent()) {
				stopListeningInternal();
			} else {
				getRealm().asyncExec(() -> {
					if (!hasListeners && isListening) {
						stopListeningInternal();
					}
				});
			}
			hasListeners = false;
			super.lastListenerRemoved();
		}

		private void startListeningInternal() {
			startListening();
			isListening = true;
			value = calculate();
		}

		private void stopListeningInternal() {
			isListening = false;
			stopListening();
			value = null;
		}

		protected abstract void startListening();

		protected abstract void stopListening();

		protected abstract T calculate();

		@Override
		public Object getValueType() {
			return null;
		}
	}
