        marker = toCopy;
        refresh();
    }

    /**
     * Clears any cached information. This frees up some memory, but will slow down
     * the next comparison operation. It is a good idea to call this on a set of markers
     * after sorting them, in order to reduce their memory cost.
     */
    public void clearCache() {
        resourceNameKey = null;
        descriptionKey = null;
    }

    /**
     * Refresh the properties of this marker from the underlying IMarker instance
     */
    public void refresh() {
        clearCache();

        description = Util.getProperty(IMarker.MESSAGE, marker);
        resourceName = Util.getResourceName(marker);
        inFolder = Util.getContainerName(marker);
        shortFolder = null;
        line = marker.getAttribute(IMarker.LINE_NUMBER, -1);
        locationString = marker.getAttribute(IMarker.LOCATION,
