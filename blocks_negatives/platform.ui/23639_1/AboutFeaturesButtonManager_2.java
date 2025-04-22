    /**
     * Return an array of all bundle groups that share the argument's provider and
     * image.  Returns an empty array if there isn't any related information.
     */
    public AboutBundleGroupData[] getRelatedInfos(AboutBundleGroupData info) {
        Long crc = info.getFeatureImageCrc();
        if (crc == null) {
