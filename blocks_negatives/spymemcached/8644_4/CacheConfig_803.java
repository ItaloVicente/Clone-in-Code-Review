    public String getServer(int serverIndex) {
        if (serverIndex > servers.size() - 1) {
            throw new IllegalArgumentException("Server index is out of bounds, index = "
                    + serverIndex + ", servers count = " + servers.size());
        }
        return servers.get(serverIndex);
