        return Collections.unmodifiableSet(stringToRGB.keySet());
    }

    /**
     * Returns the color data associated with the given symbolic color name.
     *
     * @param symbolicName symbolic color name.
     * @return the <code>RGB</code> data, or <code>null</code> if the symbolic name
     * is not valid.
     */
    public RGB getRGB(String symbolicName) {
        Assert.isNotNull(symbolicName);
        return stringToRGB.get(symbolicName);
    }

    /**
