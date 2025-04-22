		if (!isDisposed() && listener == null) {
			listener = property.adaptListener(new ISimplePropertyListener<ValueDiff<T>>() {
				@Override
				public void handleEvent(final SimplePropertyEvent<ValueDiff<T>> event) {
					if (!isDisposed() && !updating) {
						getRealm().exec(new Runnable() {
