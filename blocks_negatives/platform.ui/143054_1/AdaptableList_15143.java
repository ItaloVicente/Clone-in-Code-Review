        if (adapter == IWorkbenchAdapter.class)
            return (T)this;
        return null;
    }

    /**
     * Returns the elements in this list.
     */
    public Object[] getChildren() {
        return children.toArray();
    }

    @Override
