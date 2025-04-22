        if (index == WHOLE_COLLECTION) {
            setValue(null);
        }
        else if (isCollection()) {
            Object o = getBaseValue();
            Object collection = ValueUtils.remove(getBaseValue(), index);
            if (collection != o) {
                ValueUtils.setValue(getBean(), getPropertyDescriptor(), collection);
            }
        }
        else if (index == 0) {
            index = WHOLE_COLLECTION;
            setValue(null);
        }
    }
