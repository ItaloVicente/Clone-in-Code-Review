    public void put(String key, ImageDescriptor descriptor) {
        Entry entry = getEntry(key);
        if (entry == null) {
            entry = new Entry();
            getTable().put(key, entry);
        }

        if (entry.image != null) {
            throw new IllegalArgumentException(
        }

        entry.descriptor = descriptor;
    }

    /**
     * Adds an image to this registry.  This method fails if there
     * is already an image or descriptor for the given key.
     * <p>
     * Note that an image registry owns all of the image objects registered
     * with it, and automatically disposes of them when the SWT Display is disposed.
     * Because of this, clients must not register an image object
     * that is managed by another object.
     * </p>
     *
     * @param key the key
     * @param image the image, should not be <code>null</code>
     * @exception IllegalArgumentException if the key already exists
     */
    public void put(String key, Image image) {
        Entry entry = getEntry(key);

        if (entry == null) {
            entry = new Entry();
            putEntry(key, entry);
        }

        if (entry.image != null || entry.descriptor != null) {
            throw new IllegalArgumentException(
        }

        Assert.isNotNull(image, "Cannot register a null image."); //$NON-NLS-1$
        entry.image = image;
        entry.descriptor = new OriginalImageDescriptor(image, manager.getDevice());

        try {
            manager.create(entry.descriptor);
        } catch (DeviceResourceException e) {
        }
    }

    /**
     * Removes an image from this registry.
     * If an SWT image was allocated, it is disposed.
     * This method has no effect if there is no image or descriptor for the given key.
     * @param key the key
     */
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

    /**
     * Disposes this image registry, disposing any images
     * that were allocated for it, and clearing its entries.
     *
     * @since 3.1
     */
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
