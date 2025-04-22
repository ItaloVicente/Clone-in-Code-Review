		if (!isDisposed() && listener == null) {
			listener = property.adaptListener(new ISimplePropertyListener<S, SetDiff<E>>() {
				@Override
				public void handleEvent(final SimplePropertyEvent<S, SetDiff<E>> event) {
					if (!isDisposed() && !updating) {
						getRealm().exec(new Runnable() {
