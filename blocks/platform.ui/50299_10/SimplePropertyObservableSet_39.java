		if (!isDisposed() && listener == null) {
			listener = property.adaptListener(new ISimplePropertyListener<SetDiff<E>>() {
				@Override
				public void handleEvent(final SimplePropertyEvent<SetDiff<E>> event) {
					if (!isDisposed() && !updating) {
						getRealm().exec(new Runnable() {
