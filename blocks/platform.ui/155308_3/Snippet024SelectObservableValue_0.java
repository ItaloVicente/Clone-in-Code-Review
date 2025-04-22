	static class ViewModel {
		private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
		private Color color;

		public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
			propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
		}

		public Color getColor() {
			return color;
		}

		public void setColor(Color color) {
			Color old = this.color;
			this.color = color;
			propertyChangeSupport.firePropertyChange("color", old, color);
		}
	}

