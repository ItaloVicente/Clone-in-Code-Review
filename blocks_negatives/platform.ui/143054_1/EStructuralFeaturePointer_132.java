        if (getImmediateNode() == null) {
            AbstractFactory factory = getAbstractFactory(context);
            int inx = (index == WHOLE_COLLECTION ? 0 : index);
            boolean success =
                factory.createObject(
                    context,
                    this,
                    getBean(),
                    getPropertyName(),
                    inx);
            if (!success) {
                throw new JXPathAbstractFactoryException("Factory " + factory
                        + " could not create an object for path: " + asPath());
            }
        }
        return this;
    }

    @Override
