            return null;
        }
    }

    /**
     * A comparator that will sort <code>IThemeElementDefinition</code> elements
     * by id depth.  You may use this on both <code>String</code> and
     * <code>IThemeElementDefinition</code> objects in order to perform
     * searching.
     *
     * @since 3.0
     */
    public static final Comparator ID_COMPARATOR = new Comparator() {

        @Override
