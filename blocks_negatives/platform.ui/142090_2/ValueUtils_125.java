    /**
     * If the parameter is a container, opens the container and
     * return the contents.  The method is recursive.
     * @param object to read
     * @return Object
     */
    public static Object getValue(Object object) {
        while (object instanceof Container) {
            object = ((Container) object).getValue();
        }
        return object;
    }
