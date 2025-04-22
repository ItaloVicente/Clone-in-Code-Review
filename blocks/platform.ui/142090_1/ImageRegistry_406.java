	public void put(String key, ImageDescriptor descriptor) {
		Entry entry = getEntry(key);
		if (entry == null) {
			entry = new Entry();
			getTable().put(key, entry);
		}

		if (entry.image != null) {
			throw new IllegalArgumentException(
					"ImageRegistry key already in use: " + key); //$NON-NLS-1$
		}

		entry.descriptor = descriptor;
	}

	public void put(String key, Image image) {
		Entry entry = getEntry(key);

		if (entry == null) {
			entry = new Entry();
			putEntry(key, entry);
		}

		if (entry.image != null || entry.descriptor != null) {
			throw new IllegalArgumentException(
					"ImageRegistry key already in use: " + key); //$NON-NLS-1$
		}

		Assert.isNotNull(image, "Cannot register a null image."); //$NON-NLS-1$
		entry.image = image;
		entry.descriptor = new OriginalImageDescriptor(image, manager.getDevice());

		try {
			manager.create(entry.descriptor);
		} catch (DeviceResourceException e) {
		}
	}

	public void remove(String key) {
		ImageDescriptor descriptor = getDescriptor(key);
		if (descriptor != null) {
			manager.destroy(descriptor);
			getTable().remove(key);
		}
	}

	private Entry getEntry(String key) {
		return getTable().get(key);
	}

	private void putEntry(String key, Entry entry) {
		getTable().put(key, entry);
	}

	private Map<String, Entry> getTable() {
		if (table == null) {
			table = new HashMap<>(10);
		}
		return table;
	}

	public void dispose() {
		manager.cancelDisposeExec(disposeRunnable);

		if (table != null) {
			for (Entry entry : table.values()) {
				if (entry.image != null) {
					manager.destroyImage(entry.descriptor);
				}
			}
			table = null;
		}
		display = null;
	}
