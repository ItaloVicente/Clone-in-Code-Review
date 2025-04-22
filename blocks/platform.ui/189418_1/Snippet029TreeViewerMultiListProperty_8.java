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
