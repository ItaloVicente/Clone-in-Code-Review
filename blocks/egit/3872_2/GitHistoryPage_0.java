	private final IPropertyChangeListener listener = new IPropertyChangeListener() {
		public void propertyChange(PropertyChangeEvent event) {
			if (UIPreferences.RESOURCEHISTORY_SHOW_RELATIVE_DATE.equals(event
					.getProperty()))
				if (graph.setRelativeDate(((Boolean) event.getNewValue())
						.booleanValue()))
					graph.getTableView().refresh();
		}
	};

