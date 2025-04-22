	public void removeElement(QuickAccessElement removedElement) {
		if (this.elements != null) {
			this.elements.remove(removedElement);
		}
	}

	@Override
	public boolean requiresUiAccess() {
		if (this.initialProviders == null) {
			return false;
		}
		return this.initialProviders.stream().anyMatch(QuickAccessProvider::requiresUiAccess);
	}
}
