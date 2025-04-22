			}
			getRealm().exec(new Runnable() {
				@Override
				public void run() {
					cachedValue = property.getValue(source);
					stale = false;
					if (listener != null)
						listener.addTo(source);
