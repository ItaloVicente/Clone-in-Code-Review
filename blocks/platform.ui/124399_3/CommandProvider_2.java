	}

	private void lazyCreateCommandMap() {
		if (idToCommand == null) {
			idToCommand = new HashMap<>();
		}
