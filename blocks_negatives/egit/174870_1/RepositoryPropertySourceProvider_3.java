			if (lastSourceType == SourceType.TAG) {
				store.removePropertyChangeListener(dateFormatListener);
				listenToDateFormatChanges = false;
			} else if (type == SourceType.TAG) {
				listenToDateFormatChanges = true;
				store.addPropertyChangeListener(dateFormatListener);
			}
