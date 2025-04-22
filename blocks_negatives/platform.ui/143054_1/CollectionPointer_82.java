        if (index == WHOLE_COLLECTION) {
            if (test == null) {
                return true;
            }
            if (test instanceof NodeNameTest) {
                return false;
            }
            return test instanceof NodeTypeTest && ((NodeTypeTest) test).getNodeType() == Compiler.NODE_TYPE_NODE;
        }
        return getValuePointer().testNode(test);
    }
