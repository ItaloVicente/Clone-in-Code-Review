
    @Override
    public boolean serviceEnabled(final ServiceType type, final InetAddress node) {
        Integer services = nodeServices.get(node);
        if (services == null) {
            return false;
        }
        return (services & (1 << type.ordinal())) != 0;
    }
