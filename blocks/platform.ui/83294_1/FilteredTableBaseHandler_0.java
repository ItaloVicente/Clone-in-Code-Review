
	public class FilteredTableItem {
		String text;
		Image image;
		String tooltipText;
		Map<String, Object> dataMap = new HashMap<>();

		public void setText(String text) {
			this.text = text;
		}

		public void setImage(Image image) {
			this.image = image;
		}

		public void putData(String key, Object value) {
			this.dataMap.put(key, value);
		}

		public Object getData(String key) {
			return dataMap.get(key);
		}

	}
