        StringBuilder buffer = new StringBuilder();
        NodePointer parent = getImmediateParentPointer();
        if (parent != null) {
            buffer.append(parent.asPath());
        }
        if (index != WHOLE_COLLECTION) {
            if (parent != null && parent.getIndex() != WHOLE_COLLECTION) {
                buffer.append("/.");
            }
            else if (parent != null
                    && parent.getImmediateParentPointer() != null
                    && parent.getImmediateParentPointer().getIndex() != WHOLE_COLLECTION) {
                buffer.append("/.");
            }
            buffer.append("[").append(index + 1).append(']');
        }

        return buffer.toString();
    }
