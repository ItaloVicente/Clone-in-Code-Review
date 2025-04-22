        if (propertyIndex != index) {
            super.setPropertyIndex(index);
            propertyName = null;
            propertyDescriptor = null;
            baseValue = UNINITIALIZED;
            value = UNINITIALIZED;
        }
    }
