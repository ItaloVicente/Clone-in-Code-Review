	private final IPropertyChangeListener listener = new IPropertyChangeListener() {
		public void propertyChange(PropertyChangeEvent event) {
			if (event.getProperty().equals(
					UIPreferences.RESOURCEHISTORY_SHOW_RELATIVE_DATE))
				if (graph.setRelativeDate(((Boolean) event.getNewValue())
						.booleanValue()))
					graph.getTableView().refresh();
		}
	};

