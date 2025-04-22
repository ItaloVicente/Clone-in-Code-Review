			}

			getRealm().exec(new Runnable() {
				@Override
				public void run() {
					cachedList = new ArrayList(getList());
					stale = false;

					if (listener != null)
						listener.addTo(source);
