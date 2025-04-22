				if (partRef.getId().equals(getSite().getId())) {
					isViewVisible = true;
					boolean needUpdate = hasPendingChanges();
					if (needUpdate) {
						builder.getUpdateScheduler().scheduleUIUpdate(MarkerUpdateScheduler.SHORT_DELAY);
					} else {
						setTitleToolTip(null);
					}
