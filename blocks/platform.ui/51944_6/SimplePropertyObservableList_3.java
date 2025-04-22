		if (!isDisposed() && listener == null) {
			listener = property.adaptListener(new ISimplePropertyListener<ListDiff<E>>() {
				@Override
				public void handleEvent(final SimplePropertyEvent<ListDiff<E>> event) {
					if (!isDisposed() && !updating) {
						getRealm().exec(new Runnable() {
