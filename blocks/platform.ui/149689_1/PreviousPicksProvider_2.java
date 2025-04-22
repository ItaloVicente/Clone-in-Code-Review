	public void addPreviousPick(QuickAccessElement element, Consumer<QuickAccessElement> onRemoveElement) {
		elements.remove(element);
		if (elements.size() == maxNumberOfElements) {
			QuickAccessElement removedElement = elements.removeLast();
			if (onRemoveElement != null) {
				onRemoveElement.accept(removedElement);
			}
		}
		elements.addFirst(element);
