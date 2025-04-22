
		getRealm().exec(new Runnable() {
			@Override
			public void run() {
				cachedSet = new HashSet<>(getSet());
				stale = false;

				if (listener != null)
					listener.addTo(source);
			}
		});
