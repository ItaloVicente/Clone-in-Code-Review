        if (propKey.equals(IBasicPropertyConstants.P_TEXT))
            return getName();
        return null;
    }

    /**
     * Hook. Implemented by <code>GroupElement</code> for use instead of instanceof
     * @return boolean
     */
    public boolean isGroup() {
        return false;
    }

    @Override
