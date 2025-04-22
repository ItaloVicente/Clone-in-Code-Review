    /**
     * Finds the property descriptor corresponding to the current property
     * index.
     * @return PropertyDescriptor
     */
    private EStructuralFeature getPropertyDescriptor() {
        if (propertyDescriptor == null) {
            int inx = getPropertyIndex();
            if (inx == UNSPECIFIED_PROPERTY) {
                propertyDescriptor =
                    beanInfo.getPropertyDescriptor(propertyName);
            }
            else {
                EStructuralFeature[] propertyDescriptors =
                    getPropertyDescriptors();
                if (inx >= 0 && inx < propertyDescriptors.length) {
                    propertyDescriptor = propertyDescriptors[inx];
                }
                else {
                    propertyDescriptor = null;
                }
            }
        }
        return propertyDescriptor;
    }
