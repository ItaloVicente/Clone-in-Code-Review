		knownMasterValues.addSetChangeListener(new ISetChangeListener<I>() {
			@Override
			public void handleSetChange(SetChangeEvent<? extends I> event) {
				for (I key : event.diff.getRemovals()) {
					if (detailListener != null)
						detailListener.removeFrom(key);
					cachedValues.remove(key);
					staleMasterValues.remove(key);
				}
				for (I key : event.diff.getAdditions()) {
					cachedValues.put(key, detailProperty.getValue(key));
					if (detailListener != null)
						detailListener.addTo(key);
				}
