					.getProperty())) {
				Display.getCurrent().syncExec(new Runnable() {
					public void run() {
						graph.setRelativeDate(isShowingRelativeDates());
						graph.getTableView().refresh();
					}
				});
