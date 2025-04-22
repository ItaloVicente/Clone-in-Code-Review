     * Returns true if the preferred length of the given control is
     * independent of the width and vice versa. If this returns true,
     * then changing the widthHint argument to control.computeSize will
     * never change the resulting height and changing the heightHint
     * will never change the resulting width. Returns false if unknown.
     * <p>
     * This information can be used to improve caching. Incorrectly returning
     * a value of false may decrease performance, but incorrectly returning
     * a value of true will generate incorrect layouts... so always return
     * false if unsure.
     * </p>
     *
     * @param control
     * @return <code>true</code> iff the preferred length of the given control is
     * independent of the width and vice versa
     */
