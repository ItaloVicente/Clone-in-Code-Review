					.getProperty())) {
				Object oldValue = event.getOldValue();
				if (oldValue == null || !oldValue.equals(event.getNewValue())) {
					graph.setRelativeDate(isShowingRelativeDates());
					graph.getTableView().refresh();
				}
