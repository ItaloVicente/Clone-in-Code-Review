        if (map == null) {
            return;
        }

        RefCount count = map.get(descriptor);
        if (count != null) {
            count.count--;
            if (count.count == 0) {
                deallocate(count.resource, descriptor);
                map.remove(descriptor);
            }
        }

        if (map.isEmpty()) {
            map = null;
        }
    }

    /**
     * Deallocates any resources allocated by this registry that have not yet been
     * deallocated.
     *
     * @since 3.1
     */
    @Override
