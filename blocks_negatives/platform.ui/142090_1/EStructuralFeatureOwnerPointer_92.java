            NodePointer startWith) {
        if (test == null) {
            return createNodeIterator(null, reverse, startWith);
        }
        if (test instanceof NodeNameTest) {
            NodeNameTest nodeNameTest = (NodeNameTest) test;
            QName testName = nodeNameTest.getNodeName();
            if (isValidProperty(testName)) {
                return createNodeIterator(nodeNameTest.isWildcard() ? null
                        : testName.toString(), reverse, startWith);
            }
            return null;
        }
        return test instanceof NodeTypeTest && ((NodeTypeTest) test).getNodeType() == Compiler.NODE_TYPE_NODE
                ? createNodeIterator(null, reverse, startWith) : null;
    }

    /**
     * Create a NodeIterator.
     * @param property property name
     * @param reverse whether to iterate in reverse
     * @param startWith first pointer to return
     * @return NodeIterator
     */
    public NodeIterator createNodeIterator(String property, boolean reverse,
            NodePointer startWith) {
        return new EStructuralFeatureIterator(this, property, reverse, startWith);
    }

    @Override
