        return elements == null ? 0 : elements.length;
    }

    @Override
	public Object[] toArray() {
        return elements == null ? new Object[0] : (Object[]) elements.clone();
    }

    @Override
	public List toList() {
        return Arrays.asList(elements == null ? new Object[0] : elements);
    }

    /**
     * Internal method which returns a string representation of this
     * selection suitable for debug purposes only.
     *
     * @return debug string
     */
    @Override
