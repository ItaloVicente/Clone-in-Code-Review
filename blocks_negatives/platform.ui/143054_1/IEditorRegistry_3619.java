    /**
     * Adds a listener for changes to properties of this registry.
     * Has no effect if an identical listener is already registered.
     * <p>
     * The properties ids are as follows:
     * <ul>
     *   <li><code>PROP_CONTENTS</code>: Triggered when the file editor mappings in
     *       the editor registry change.</li>
     * </ul>
     * </p>
     *
     * @param listener a property listener
     */
    void addPropertyListener(IPropertyListener listener);
