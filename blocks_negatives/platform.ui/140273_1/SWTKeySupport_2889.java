    /**
     * Given an SWT accelerator value, provide the corresponding key stroke.
     *
     * @param accelerator
     *            The accelerator to convert; should be a valid SWT accelerator
     *            value.
     * @return The equivalent key stroke; never <code>null</code>.
     */
    public static KeyStroke convertAcceleratorToKeyStroke(int accelerator) {
        final SortedSet modifierKeys = new TreeSet();
