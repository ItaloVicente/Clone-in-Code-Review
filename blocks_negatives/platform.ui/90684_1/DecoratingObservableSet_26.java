			setChangeListener = new ISetChangeListener<E>() {
				@Override
				public void handleSetChange(SetChangeEvent<? extends E> event) {
					DecoratingObservableSet.this.handleSetChange(event);
				}
			};
