		knownMasterElements.addSetChangeListener(event -> {
			for (M key1 : event.diff.getRemovals()) {
				if (detailListener != null)
					detailListener.removeFrom(key1);
				cachedValues.remove(key1);
				staleElements.remove(key1);
			}
			for (M key2 : event.diff.getAdditions()) {
				cachedValues.put(key2, detailProperty.getValue(key2));
				if (detailListener != null)
					detailListener.addTo(key2);
