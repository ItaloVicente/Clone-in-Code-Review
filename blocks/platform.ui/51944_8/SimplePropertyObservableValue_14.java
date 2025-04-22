		if (!isDisposed() && listener == null) {
			listener = property.adaptListener(new ISimplePropertyListener<S, ValueDiff<? extends T>>() {
				@Override
				public void handleEvent(final SimplePropertyEvent<S, ValueDiff<? extends T>> event) {
					if (!isDisposed() && !updating) {
						getRealm().exec(new Runnable() {
