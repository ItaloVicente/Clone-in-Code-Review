        updateGatherThread.firstMatch = 0;
        updateGatherThread.lastMatch = descriptorsSize - 1;
        updateGatherThread.start();
    }

    /**
     * Return an image for a resource descriptor.
     *
     * @param desc resource descriptor to return image for
     * @return an image for a resource descriptor.
     */
    private Image getImage(ResourceDescriptor desc) {
        IResource r = (IResource) desc.resources.get(0);
        return labelProvider.getImage(r);
    }

    /**
     * Use a binary search to get the last match for the patternString.
     * This method assumes the patternString does not contain any '?'
     * characters and that it contains only one '*' character at the end
     * of the string.
     */
    private int getLastMatch() {
        int high = descriptorsSize;
        int low = -1;
        boolean match = false;
        ResourceDescriptor desc = new ResourceDescriptor();
        desc.label = patternString.substring(0, patternString.length() - 1);
        while (high - low > 1) {
            int index = (high + low) / 2;
            String label = descriptors[index].label;
            if (match(label)) {
                low = index;
                match = true;
            } else {
                int compare = descriptors[index].compareTo(desc);
                if (compare == -1) {
                    low = index;
                } else {
                    high = index;
                }
            }
        }
        if (match) {
