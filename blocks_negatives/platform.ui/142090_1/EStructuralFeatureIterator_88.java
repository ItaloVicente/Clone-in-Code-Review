                if (index <= position && position < index + length) {
                    propertyNodePointer.setIndex(offset);
                    return true;
                }
                index += length;
            }
        }
        return false;
    }
