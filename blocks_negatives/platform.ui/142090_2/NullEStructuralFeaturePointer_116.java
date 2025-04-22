        if (!byNameAttribute) {
            return super.asPath();
        }
        StringBuilder buffer = new StringBuilder();
        buffer.append(getImmediateParentPointer().asPath());
        buffer.append("[@name='");
        buffer.append(escape(getPropertyName()));
        buffer.append("']");
        if (index != WHOLE_COLLECTION) {
            buffer.append('[').append(index + 1).append(']');
        }
        return buffer.toString();
    }

    /**
     * Create a "bad factory" JXPathAbstractFactoryException for the specified AbstractFactory.
     * @param factory AbstractFactory
     * @return JXPathAbstractFactoryException
     */
    private JXPathAbstractFactoryException createBadFactoryException(AbstractFactory factory) {
        return new JXPathAbstractFactoryException("Factory " + factory
                + " reported success creating object for path: " + asPath()
                + " but object was null.  Terminating to avoid stack recursion.");
    }
