        if (value == UNINITIALIZED) {
            if (index == WHOLE_COLLECTION) {
                value = ValueUtils.getValue(getBaseValue());
            }
            else {
                EStructuralFeature pd = getPropertyDescriptor();
                if (pd == null) {
                    value = null;
                }
                else {
                    value = ValueUtils.getValue(getBean(), pd, index);
                }
            }
        }
        return value;
    }
