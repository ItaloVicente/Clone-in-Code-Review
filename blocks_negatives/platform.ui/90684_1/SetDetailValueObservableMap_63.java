		detailValue.addValueChangeListener(new IValueChangeListener<E>() {
			@Override
			public void handleValueChange(ValueChangeEvent<? extends E> event) {
				if (!event.getObservableValue().isStale()) {
					staleDetailObservables.remove(detailValue);
				}

				fireSingleChange(addedKey, event.diff.getOldValue(),
						event.diff.getNewValue());
