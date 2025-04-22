        IContributionItem[] result = new IContributionItem[mapItemToWrapper
                .size()];
        mapItemToWrapper.keySet().toArray(result);
        return result;
    }

    /**
     * Returns the parent manager.
     *
     * @return the parent manager
     */
    public IContributionManager getParent() {
        return parentMgr;
    }

    @Override
