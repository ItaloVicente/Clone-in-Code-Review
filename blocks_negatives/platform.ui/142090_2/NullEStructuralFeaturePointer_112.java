        if (parent == null || parent.isContainer()) {
            throw new JXPathInvalidAccessException(
                "Cannot set property "
                    + asPath()
                    + ", the target object is null");
        }
        if (parent instanceof EStructuralFeatureOwnerPointer
                && ((EStructuralFeatureOwnerPointer) parent)
                        .isDynamicPropertyDeclarationSupported()) {
            EStructuralFeaturePointer propertyPointer =
                ((EStructuralFeatureOwnerPointer) parent).getPropertyPointer();
            propertyPointer.setPropertyName(propertyName);
            propertyPointer.setValue(value);
        }
        else {
            throw new JXPathInvalidAccessException(
                "Cannot set property "
                    + asPath()
                    + ", path does not match a changeable location");
        }
    }

    @Override
