	}

	protected abstract Image getDefaultImage();

	public final void destroyImage(ImageDescriptor descriptor) {
		destroy(descriptor);
	}

	public final Color createColor(ColorDescriptor descriptor) {
		return (Color)create(descriptor);
	}

	public final Color createColor(RGB descriptor) {
		return createColor(new RGBColorDescriptor(descriptor));
	}

	public final void destroyColor(RGB descriptor) {
		destroyColor(new RGBColorDescriptor(descriptor));
	}

	public final void destroyColor(ColorDescriptor descriptor) {
		destroy(descriptor);
	}

	public final Font createFont(FontDescriptor descriptor) {
		return (Font)create(descriptor);
	}

	public final void destroyFont(FontDescriptor descriptor) {
		destroy(descriptor);
	}

	public void dispose() {
		if (disposeExecs == null) {
			return;
		}

		RuntimeException foundException = null;

		Runnable[] execs = disposeExecs.toArray(new Runnable[disposeExecs.size()]);
		for (Runnable exec : execs) {
			try {
				exec.run();
			} catch (RuntimeException e) {
				foundException = e;
			}
		}

		if (foundException != null) {
			throw foundException;
		}
	}

	public abstract Object find(DeviceResourceDescriptor descriptor);

	public void disposeExec(Runnable r) {
		Assert.isNotNull(r);

		if (disposeExecs == null) {
			disposeExecs = new ArrayList<>();
		}

		disposeExecs.add(r);
	}

	public void cancelDisposeExec(Runnable r) {
		Assert.isNotNull(r);

		if (disposeExecs == null) {
			return;
		}

		disposeExecs.remove(r);

		if (disposeExecs.isEmpty()) {
			disposeExecs = null;
		}
	}
