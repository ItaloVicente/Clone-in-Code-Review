        return getString("bootstrap.sslKeystorePassword");
    }

    @Override
    public boolean queryEnabled() {
        return getBoolean("queryEnabled");
    }

    @Override
    public int queryPort() {
        return getInt("queryPort");
