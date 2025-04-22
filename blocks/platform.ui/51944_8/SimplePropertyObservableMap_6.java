		if (!isDisposed() && listener == null) {
			listener = property.adaptListener(new ISimplePropertyListener<S, MapDiff<K, V>>() {
				@Override
				public void handleEvent(final SimplePropertyEvent<S, MapDiff<K, V>> event) {
					if (!isDisposed() && !updating) {
						getRealm().exec(new Runnable() {
