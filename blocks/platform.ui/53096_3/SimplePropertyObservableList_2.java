		if (!isDisposed() && listener == null) {
			listener = property.adaptListener(new ISimplePropertyListener<S, ListDiff<E>>() {
				@Override
				public void handleEvent(final SimplePropertyEvent<S, ListDiff<E>> event) {
					if (!isDisposed() && !updating) {
						getRealm().exec(new Runnable() {
