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

