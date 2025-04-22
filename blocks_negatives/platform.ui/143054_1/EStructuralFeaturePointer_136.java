        if (object == this) {
            return true;
        }

        if (!(object instanceof EStructuralFeaturePointer)) {
            return false;
        }

        EStructuralFeaturePointer other = (EStructuralFeaturePointer) object;
        if (parent != other.parent && (parent == null || !parent.equals(other.parent))) {
            return false;
        }

        if (getPropertyIndex() != other.getPropertyIndex()
            || !getPropertyName().equals(other.getPropertyName())) {
            return false;
        }

        int iThis = (index == WHOLE_COLLECTION ? 0 : index);
        int iOther = (other.index == WHOLE_COLLECTION ? 0 : other.index);
        return iThis == iOther;
    }

    @Override
