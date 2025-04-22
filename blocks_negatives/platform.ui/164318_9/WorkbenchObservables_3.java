		return new ListeningValue<IEditorInput>() {
			private final IPropertyListener listener = (Object source, int propId) -> {
				if (propId == IWorkbenchPartConstants.PROP_INPUT) {
					protectedSetValue(editor.getEditorInput());
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

	/**
	 * A base class for creating observable values that track the state of a
	 * non-{@link IObservable} objects.
	 */
	private abstract static class ListeningValue<T> extends AbstractObservableValue<T> {
		private T value;
		private boolean isListening;
		private volatile boolean hasListeners;

		@Override
		protected final T doGetValue() {
			if (isListening) {
				return value;
			}
			return calculate();
		}

		/**
		 * Sets the value. Must be invoked in the {@link Realm} of the observable.
		 * Subclasses must call this method instead of {@link #setValue} or
		 * {@link #doSetValue}.
		 *
		 * @param value the value to set
		 */
		protected final void protectedSetValue(T value) {
			checkRealm();
			if (!isListening)
				throw new IllegalStateException();
			if (this.value != value) {
				fireValueChange(Diffs.createValueDiff(this.value, this.value = value));
			}
		}

		@Override
		protected final void firstListenerAdded() {
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
		protected final void lastListenerRemoved() {
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
			isListening = true;
			value = calculate();
			startListening();
		}

		private void stopListeningInternal() {
			isListening = false;
			value = null;
			stopListening();
		}

		/**
		 * Subclasses must override this method to attach listeners to the
		 * non-{@link IObservable} objects the state of which is tracked by this
		 * observable.
		 */
		protected abstract void startListening();

		/**
		 * Subclasses must override this method to detach listeners from the
		 * non-{@link IObservable} objects the state of which is tracked by this
		 * observable.
		 */
		protected abstract void stopListening();

		/**
		 * Subclasses must override this method to provide the object's value that will
		 * be used when the value is not set explicitly by {@link #doSetValue(Object)}.
		 *
		 * @return the object's value
		 */
		protected abstract T calculate();

		@Override
		public Object getValueType() {
			return null;
		}
