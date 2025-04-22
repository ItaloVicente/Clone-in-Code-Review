    * Validates dropping on the given object. This method is called whenever some
    * aspect of the drop operation changes.
    * <p>
    * Subclasses must implement this method to define which drops make sense.
    * </p>
    *
    * @param target the object that the mouse is currently hovering over, or
    *   <code>null</code> if the mouse is hovering over empty space
    * @param operation the current drag operation (copy, move, etc.)
    * @param transferType the current transfer type
    * @return <code>true</code> if the drop is valid, and <code>false</code>
    *   otherwise
    */
