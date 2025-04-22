    /**
     * @return true if a button should be added (i.e., the argument has an image
     *         and it does not already have a button)
     */
    public boolean add(AboutBundleGroupData info) {
        Long crc = info.getFeatureImageCrc();
        if (crc == null) {
