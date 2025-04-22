		public QuickAccessElement findElement(String id, String filterText) {
			QuickAccessElement[] elementsSorted = getElementsSorted(filterText, new NullProgressMonitor());
			return Arrays.stream(elementsSorted)
					.filter(element -> element.getId().equals(id))
					.findAny()
					.orElse(null);
