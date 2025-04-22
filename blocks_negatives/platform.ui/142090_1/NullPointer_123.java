        JXPathContext context,
        QName name,
        int index,
        Object value) {
        return createPath(context).createChild(context, name, index, value);
    }

    @Override
