        if (useAlternateNetwork) {
            AlternateAddress aa = alternateAddresses.get(AlternateAddressType.EXTERNAL);
            if (aa == null) {
                throw new IllegalStateException("external addresses selected, but none found! this is a bug.");
            } else {
                return aa.rawHostname();
            }
        } else {
            return rawHostname;
        }
    }

    @Override
    public boolean useAlternateNetwork() {
        return useAlternateNetwork;
    }

    @Override
    public void useAlternateNetwork(boolean useAlternateNetwork) {
        this.useAlternateNetwork = useAlternateNetwork;
