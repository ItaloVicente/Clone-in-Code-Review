        return getChildren(element);
    }

    /**
     * Recursively walks over the resource delta and gathers all marker deltas.  Marker
     * deltas are placed into one of the three given vectors depending on
     * the type of delta (add, remove, or change).
     */
