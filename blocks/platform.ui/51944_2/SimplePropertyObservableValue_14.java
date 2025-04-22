		if (!isDisposed() && listener == null) {
			listener = property.adaptListener(new ISimplePropertyListener<ValueDiff<? extends T>>() {
				@Override
				public void handleEvent(final SimplePropertyEvent<ValueDiff<? extends T>> event) {
					if (!isDisposed() && !updating) {
						getRealm().exec(new Runnable() {
