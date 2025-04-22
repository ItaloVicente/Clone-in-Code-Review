    @Override
    public InetAddress sendTo() {
        return targetNode;
    }

    public void sendTo(InetAddress targetNode) {
        this.targetNode = targetNode;
    }

