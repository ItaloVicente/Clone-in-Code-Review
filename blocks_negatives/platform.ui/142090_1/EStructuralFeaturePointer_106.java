        JXPathContext context,
        QName name,
        int index) {
        EStructuralFeaturePointer prop = (EStructuralFeaturePointer) clone();
        if (name != null) {
            prop.setPropertyName(name.toString());
        }
        prop.setIndex(index);
        return prop.createPath(context);
    }

    @Override
