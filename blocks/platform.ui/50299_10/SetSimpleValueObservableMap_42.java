			listener = detailProperty.adaptListener(new ISimplePropertyListener<ValueDiff<V>>() {
				@Override
				public void handleEvent(final SimplePropertyEvent<ValueDiff<V>> event) {
					if (!isDisposed() && !updating) {
						getRealm().exec(new Runnable() {
							@Override
							public void run() {
								@SuppressWarnings("unchecked")
								K source = (K) event.getSource();
								if (event.type == SimplePropertyEvent.CHANGE) {
									notifyIfChanged(source);
								} else if (event.type == SimplePropertyEvent.STALE) {
									boolean wasStale = !staleKeys.isEmpty();
									staleKeys.add(source);
									if (!wasStale)
										fireStale();
								}
