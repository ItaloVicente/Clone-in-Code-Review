			fireValueChange(new ValueDiff<T>() {

				@Override
				public T getOldValue() {
					return oldValue;
				}

				@Override
				public T getNewValue() {
					return getValue();
				}
			});
