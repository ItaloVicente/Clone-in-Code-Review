    }

    /**
     * Returns the default image that will be returned in the event that the intended
     * image is missing.
     *
     * @since 3.1
     *
     * @return a default image that will be returned in the event that the intended
     * image is missing.
     */
    protected abstract Image getDefaultImage();

    /**
     * Undoes everything that was done by {@link #createImage(ImageDescriptor)}.
     *
     * @since 3.1
     *
     * @param descriptor identifier for the image to dispose
     */
    public final void destroyImage(ImageDescriptor descriptor) {
        destroy(descriptor);
    }

    /**
     * Allocates a color, given a color descriptor. Any color allocated in this
     * manner must be disposed by calling {@link #destroyColor(ColorDescriptor)},
     * or by an eventual call to {@link #dispose()}. {@link Color#dispose()} must
     * never been called directly on the returned color.
     *
     * @since 3.1
     *
     * @param descriptor descriptor for the color to create
     * @return the Color described by the given ColorDescriptor (not null)
     * @throws DeviceResourceException if unable to create the color
     */
    public final Color createColor(ColorDescriptor descriptor) {
        return (Color)create(descriptor);
    }

    /**
     * Allocates a color, given its RGB value. Any color allocated in this
     * manner must be disposed by calling {@link #destroyColor(RGB)},
     * or by an eventual call to {@link #dispose()}. {@link Color#dispose()} must
     * never been called directly on the returned color.
     *
     * @since 3.1
     *
     * @param descriptor descriptor for the color to create
     * @return the Color described by the given ColorDescriptor (not null)
     * @throws DeviceResourceException if unable to create the color
     */
    public final Color createColor(RGB descriptor) {
        return createColor(new RGBColorDescriptor(descriptor));
    }

    /**
     * Undoes everything that was done by a call to {@link #createColor(RGB)}.
     *
     * @since 3.1
     *
     * @param descriptor RGB value of the color to dispose
     */
    public final void destroyColor(RGB descriptor) {
        destroyColor(new RGBColorDescriptor(descriptor));
    }

    /**
     * Undoes everything that was done by a call to {@link #createColor(ColorDescriptor)}.
     *
     *
     * @since 3.1
     *
     * @param descriptor identifier for the color to dispose
     */
    public final void destroyColor(ColorDescriptor descriptor) {
        destroy(descriptor);
    }

    /**
     * Returns the Font described by the given FontDescriptor. Any Font
     * allocated in this manner must be deallocated by calling disposeFont(...),
     * or by an eventual call to {@link #dispose()}.  The method {@link Font#dispose()}
     * must never be called directly on the returned font.
     *
     * @since 3.1
     *
     * @param descriptor description of the font to create
     * @return the Font described by the given descriptor
     * @throws DeviceResourceException if unable to create the font
     */
    public final Font createFont(FontDescriptor descriptor) {
        return (Font)create(descriptor);
    }

    /**
     * Undoes everything that was done by a previous call to {@link #createFont(FontDescriptor)}.
     *
     * @since 3.1
     *
     * @param descriptor description of the font to destroy
     */
    public final void destroyFont(FontDescriptor descriptor) {
        destroy(descriptor);
    }

    /**
     * Disposes any remaining resources allocated by this manager.
     */
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

    /**
     * Returns a previously allocated resource associated with the given descriptor, or
     * null if none exists yet.
     *
     * @since 3.1
     *
     * @param descriptor descriptor to find
     * @return a previously allocated resource for the given descriptor or null if none.
     */
    public abstract Object find(DeviceResourceDescriptor descriptor);

    /**
     * Causes the <code>run()</code> method of the runnable to
     * be invoked just before the receiver is disposed. The runnable
     * can be subsequently canceled by a call to <code>cancelDisposeExec</code>.
     *
     * @param r runnable to execute.
     */
    public void disposeExec(Runnable r) {
        Assert.isNotNull(r);

        if (disposeExecs == null) {
            disposeExecs = new ArrayList<>();
        }

        disposeExecs.add(r);
    }

    /**
     * Cancels a runnable that was previously scheduled with <code>disposeExec</code>.
     * Has no effect if the given runnable was not previously registered with
     * disposeExec.
     *
     * @param r runnable to cancel
     */
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
