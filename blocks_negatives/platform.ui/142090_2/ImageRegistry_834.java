        }
    }

    /**
     * Creates an empty image registry.
     * <p>
     * There must be an SWT Display created in the current
     * thread before calling this method.
     * </p>
     */
    public ImageRegistry() {
        this(Display.getCurrent());
    }

    /**
     * Creates an empty image registry using the given resource manager to allocate images.
     *
     * @param manager the resource manager used to allocate images
     *
     * @since 3.1
     */
    public ImageRegistry(ResourceManager manager) {
        Assert.isNotNull(manager);
        Device dev = manager.getDevice();
        if (dev instanceof Display) {
            this.display = (Display)dev;
        }
        this.manager = manager;
        manager.disposeExec(disposeRunnable);
    }

    /**
     * Creates an empty image registry.
     *
     * @param display this <code>Display</code> must not be
     *        <code>null</code> and must not be disposed in order
     *        to use this registry
     */
    public ImageRegistry(Display display) {
        this(JFaceResources.getResources(display));
    }

    /**
     * Returns the image associated with the given key in this registry,
     * or <code>null</code> if none.
     *
     * @param key the key
     * @return the image, or <code>null</code> if none
     */
    public Image get(String key) {

        if (key == null) {
            return null;
        }

        if (display != null) {
            /**
             * NOTE, for backwards compatibility the following images are supported
             * here, they should never be disposed, hence we explicitly return them
             * rather then registering images that SWT will dispose.
             *
             * Applications should go direclty to SWT for these icons.
             *
             * @see Display.getSystemIcon(int ID)
             */
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

    /**
     * Returns the descriptor associated with the given key in this registry,
     * or <code>null</code> if none.
     *
     * @param key the key
     * @return the descriptor, or <code>null</code> if none
     * @since 2.1
     */
    public ImageDescriptor getDescriptor(String key) {
        Entry entry = getEntry(key);
        if (entry == null) {
            return null;
        }

        return entry.descriptor;
    }

    /**
