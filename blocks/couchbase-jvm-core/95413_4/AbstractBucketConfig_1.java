    @Override
    public boolean useAlternateNetwork() {
        return useAlternateNetwork;
    }

    @Override
    public void useAlternateNetwork(boolean useAlternateNetwork) {
        this.useAlternateNetwork = useAlternateNetwork;
        for (NodeInfo node : nodes()) {
            node.useAlternateNetwork(useAlternateNetwork);
        }
    }

