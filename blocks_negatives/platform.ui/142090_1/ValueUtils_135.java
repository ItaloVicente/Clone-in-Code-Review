            while (c.size() < size) {
                c.add(null);
            }
            return collection;
        }
        throw new JXPathException(
            "Cannot turn "
                + collection.getClass().getName()
                + " into a collection of size "
                + size);
    }
