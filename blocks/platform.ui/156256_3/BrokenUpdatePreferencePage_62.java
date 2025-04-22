	private IPropertyChangeListener badListener = new IPropertyChangeListener() {
		@Override
		public void propertyChange(PropertyChangeEvent event) {
			String[] strings = new String[1];
			System.out.println(strings[2]);
		}
