		}
	}

	public ImageRegistry() {
		this(Display.getCurrent());
	}

	public ImageRegistry(ResourceManager manager) {
		Assert.isNotNull(manager);
		Device dev = manager.getDevice();
		if (dev instanceof Display) {
			this.display = (Display)dev;
		}
		this.manager = manager;
		manager.disposeExec(disposeRunnable);
	}

	public ImageRegistry(Display display) {
		this(JFaceResources.getResources(display));
	}

	public Image get(String key) {

		if (key == null) {
			return null;
		}

		if (display != null) {
			int swtKey = -1;
			if (key.equals(Dialog.DLG_IMG_INFO)) {
				swtKey = SWT.ICON_INFORMATION;
			}
			if (key.equals(Dialog.DLG_IMG_QUESTION)) {
				swtKey = SWT.ICON_QUESTION;
			}
			if (key.equals(Dialog.DLG_IMG_WARNING)) {
				swtKey = SWT.ICON_WARNING;
			}
			if (key.equals(Dialog.DLG_IMG_ERROR)) {
				swtKey = SWT.ICON_ERROR;
			}
			if (swtKey != -1) {
				final Image[] image = new Image[1];
				final int id = swtKey;
				display.syncExec(() -> image[0] = display.getSystemImage(id));
				return image[0];
			}
		}

		Entry entry = getEntry(key);
		if (entry == null) {
			return null;
		}

		if (entry.image == null) {
			entry.image = manager.createImageWithDefault(entry.descriptor);
		}

		return entry.image;
	}

	public ImageDescriptor getDescriptor(String key) {
		Entry entry = getEntry(key);
		if (entry == null) {
			return null;
		}

		return entry.descriptor;
	}

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
