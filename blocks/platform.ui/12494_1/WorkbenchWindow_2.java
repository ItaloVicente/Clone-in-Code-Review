	private static class SaveableLabelProvider extends LabelProvider implements ITableLabelProvider {
		private WorkbenchPartLabelProvider partLabelProvider;

		public void addListener(ILabelProviderListener listener) {
			partLabelProvider.addListener(listener);
		}

		public Object getObject(Object element) {
			if (element instanceof MPart) {
				Object obj = ((MPart) element).getObject();
				if (obj instanceof CompatibilityPart) {
					element = ((CompatibilityPart) obj).getPart();
				}
			}
			return element;
		}

		public boolean isLabelProperty(Object element, String property) {
			return partLabelProvider.isLabelProperty(getObject(element), property);
		}

		public void removeListener(ILabelProviderListener listener) {
			partLabelProvider.removeListener(listener);
		}

		public final Image getImage(Object element) {
			return partLabelProvider.getImage(getObject(element));
		}

		public final String getText(Object element) {
			return partLabelProvider.getText(getObject(element));
		}

		public final Image getColumnImage(Object element, int columnIndex) {
			return partLabelProvider.getColumnImage(getObject(element), columnIndex);
		}

		public final String getColumnText(Object element, int columnIndex) {
			return partLabelProvider.getColumnText(getObject(element), columnIndex);
		}

		public void dispose() {
			partLabelProvider.dispose();
		}

		public String toString() {
			return partLabelProvider.toString();
		}

		public SaveableLabelProvider() {
			partLabelProvider = new WorkbenchPartLabelProvider();
		}

	}

