
	public static abstract class AbstractModelObject {
		private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

		public void addPropertyChangeListener(PropertyChangeListener listener) {
			propertyChangeSupport.addPropertyChangeListener(listener);
		}

		public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
			propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
		}

		public void removePropertyChangeListener(PropertyChangeListener listener) {
			propertyChangeSupport.removePropertyChangeListener(listener);
		}

		public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
			propertyChangeSupport.removePropertyChangeListener(propertyName, listener);
		}

		protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
			propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
		}
	}

	public static class Content extends AbstractModelObject {
		private String text;

		public Content(String text) {
			this.text = text;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			firePropertyChange("text", this.text, this.text = text);
			this.text = text;
		}

	}

	public static class Catalog extends AbstractModelObject {
		private String name;
		private List<Catalog> catalogs = new ArrayList<>();
		private List<CatalogItem> items = new ArrayList<>();
		private CatalogItem item;

		public Catalog(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			firePropertyChange("name", this.name, this.name = name);
		}

		public List<Catalog> getCatalogs() {
			return catalogs;
		}

		public CatalogItem getItem() {
			return item;
		}

		public void setItem(CatalogItem item) {
			firePropertyChange("item", this.item, this.item = item);
		}

		public void setCatalogs(List<Catalog> catalogs) {
			firePropertyChange("catalogs", this.catalogs, this.catalogs = catalogs);
		}

		public List<CatalogItem> getItems() {
			return items;
		}

		public void setItems(List<CatalogItem> items) {
			firePropertyChange("items", this.items, this.items = items);
		}
	}

	public static class CatalogItem extends AbstractModelObject {
		private String name;
		private Content content;
		private CatalogItem item;

		public CatalogItem(String name, Content content) {
			this.name = name;
			this.content = content;
		}

		public Content getContent() {
			return content;
		}

		public void setContent(Content content) {
			firePropertyChange("content", this.content, this.content = content);
		}

		public CatalogItem getItem() {
			return item;
		}

		public void setItem(CatalogItem item) {
			firePropertyChange("item", this.item, this.item = item);
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			firePropertyChange("name", this.name, this.name = name);
		}

	}

