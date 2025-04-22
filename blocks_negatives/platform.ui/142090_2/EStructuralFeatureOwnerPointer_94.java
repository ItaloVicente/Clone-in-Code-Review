        if (value == UNINITIALIZED) {
            value = index == WHOLE_COLLECTION ? ValueUtils.getValue(getBaseValue())
                    : ValueUtils.getValue(getBaseValue(), index);
        }
        return value;
    }

    @Override
