        return new ViewerContribution(provider);
    }

    /**
     * Dispose of the action builder
     */
    public void dispose() {
        if (cache != null) {
            for (int i = 0; i < cache.size(); i++) {
                ((BasicContribution) cache.get(i)).dispose();
            }
            cache = null;
        }
    }

    @Override
