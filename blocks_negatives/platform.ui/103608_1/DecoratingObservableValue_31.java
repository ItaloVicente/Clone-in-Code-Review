			valueChangeListener = new IValueChangeListener<T>() {
				@Override
				public void handleValueChange(ValueChangeEvent<? extends T> event) {
					DecoratingObservableValue.this.handleValueChange(event);
				}
			};
