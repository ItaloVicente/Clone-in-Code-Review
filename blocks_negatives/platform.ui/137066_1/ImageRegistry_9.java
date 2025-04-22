     * Adds (or replaces) an image descriptor to this registry. The first time
     * this new entry is retrieved, the image descriptor's image will be computed
     * (via </code>ImageDescriptor.createImage</code>) and remembered.
     * This method replaces an existing image descriptor associated with the
     * given key, but fails if there is a real image associated with it.
     *
     * @param key the key
     * @param descriptor the ImageDescriptor
     * @exception IllegalArgumentException if the key already exists
     */
