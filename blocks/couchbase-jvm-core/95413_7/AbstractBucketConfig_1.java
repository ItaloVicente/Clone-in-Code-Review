    @Override
    public String useAlternateNetwork() {
        return useAlternateNetwork;
    }

    @Override
    public void useAlternateNetwork(String useAlternateNetwork) {
        this.useAlternateNetwork = useAlternateNetwork;
        for (NodeInfo node : nodes()) {
            node.useAlternateNetwork(useAlternateNetwork);
        }
    }

