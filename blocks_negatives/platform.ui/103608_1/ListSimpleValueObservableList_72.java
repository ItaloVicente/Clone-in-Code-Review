		knownMasterElements.addSetChangeListener(new ISetChangeListener<M>() {
			@Override
			public void handleSetChange(SetChangeEvent<? extends M> event) {
				for (M key : event.diff.getRemovals()) {
					if (detailListener != null)
						detailListener.removeFrom(key);
					cachedValues.remove(key);
					staleElements.remove(key);
				}
				for (M key : event.diff.getAdditions()) {
					cachedValues.put(key, detailProperty.getValue(key));
					if (detailListener != null)
						detailListener.addTo(key);
				}
