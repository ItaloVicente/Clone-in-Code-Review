        for (String attribute : attributes) {
            hashCode = hashCode * 37 + attribute.hashCode();
        }
    }

    /**
     * Returns the targetted marker type. May be
     * <code>null</code>
     *
     * @return the targetted marker type
     */
    public String getType() {
        return type;
    }

    /**
     * Returns the targetted attributes.
     * The array may be empty.
     *
     * @return the targetted attributes
     */
    public String[] getAttributes() {
        return attributes;
    }
