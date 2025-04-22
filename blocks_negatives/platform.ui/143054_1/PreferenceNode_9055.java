        subNodes.add(node);
    }

    /**
     * Creates a new instance of the given class <code>className</code>.
     *
     * @param className
     * @return new Object or <code>null</code> in case of failures.
     */
    private Object createObject(String className) {
        Assert.isNotNull(className);
        try {
            Class<?> cl = Class.forName(className);
