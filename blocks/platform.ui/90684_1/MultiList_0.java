			listChangeListener = event -> getRealm().exec(() -> {
				stale = null;
				listChanged(event);
				if (isStale())
					fireStale();
			});
