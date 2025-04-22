        if (object == this) {
            return true;
        }

        if (!(object instanceof EObjectPointer)) {
            return false;
        }

        EObjectPointer other = (EObjectPointer) object;
        if (parent != other.parent && (parent == null || !parent.equals(other.parent))) {
            return false;
        }

        if ((name == null && other.name != null)
                || (name != null && !name.equals(other.name))) {
            return false;
        }

        int iThis = (index == WHOLE_COLLECTION ? 0 : index);
        int iOther = (other.index == WHOLE_COLLECTION ? 0 : other.index);
        if (iThis != iOther) {
            return false;
        }

        if (bean instanceof Number
                || bean instanceof String
                || bean instanceof Boolean) {
            return bean.equals(other.bean);
        }
        return bean == other.bean;
    }

    /**
     * {@inheritDoc}
     * If the pointer has a parent, then parent's path.
     * If the bean is null, "null()".
     * If the bean is a primitive value, the value itself.
     * Otherwise - an empty string.
     */
    @Override
