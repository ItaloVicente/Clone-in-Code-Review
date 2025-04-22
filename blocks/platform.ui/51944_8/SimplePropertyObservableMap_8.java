
		getRealm().exec(new Runnable() {
			@Override
			public void run() {
				cachedMap = new HashMap<>(getMap());
				stale = false;

				if (listener != null)
					listener.addTo(source);
			}
		});
