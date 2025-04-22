			listChangeListener = new IListChangeListener<E>() {
				@Override
				public void handleListChange(final ListChangeEvent<? extends E> event) {
					getRealm().exec(new Runnable() {
						@Override
						public void run() {
							stale = null;
							listChanged(event);
							if (isStale())
								fireStale();
						}
					});
				}
			};
