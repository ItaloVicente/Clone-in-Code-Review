        return result[0];
    }

    /**
     * Sets a result element at the given position.
     * @param position
     * @param element
     */
    protected void setResult(int position, Object element) {
        Object[] result = getResult();
        result[position] = element;
        setResult(Arrays.asList(result));
    }

    /**
     * Compute the result and return it.
     */
    protected abstract void computeResult();

    @Override
