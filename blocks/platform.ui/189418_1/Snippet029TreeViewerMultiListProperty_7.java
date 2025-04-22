		public CatalogItem getItem() {
			return item;
		}

		public void setItem(CatalogItem item) {
			firePropertyChange("item", this.item, this.item = item);
		}

