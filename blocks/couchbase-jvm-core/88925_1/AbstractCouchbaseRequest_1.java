        return dispatchRemote();
    }

    @Override
    public void dispatchLocal(String dispatchLocal) {
        this.dispatchLocal = dispatchLocal;
    }

    @Override
    public void dispatchRemote(String dispatchRemote) {
        this.dispatchRemote = dispatchRemote;
    }

    @Override
    public String dispatchLocal() {
        return dispatchLocal;
