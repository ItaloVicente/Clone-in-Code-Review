		if (!isDisposed() && listener == null) {
			listener = property.adaptListener(new ISimplePropertyListener<MapDiff<K, V>>() {
				@Override
				public void handleEvent(final SimplePropertyEvent<MapDiff<K, V>> event) {
					if (!isDisposed() && !updating) {
						getRealm().exec(new Runnable() {
