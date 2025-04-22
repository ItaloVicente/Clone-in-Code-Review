				if (!partRef.getId().equals(getSite().getId())) {
					return;
				}
				if (partRef.getPart(false) != ExtendedMarkersView.this) {
					return;
				}
				isViewVisible = true;
				boolean needUpdate = hasPendingChanges();
				if (needUpdate) {
					builder.getUpdateScheduler().scheduleUIUpdate(MarkerUpdateScheduler.SHORT_DELAY);
				} else {
					setTitleToolTip(null);
