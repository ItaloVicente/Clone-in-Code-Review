        this.sslServices = new HashMap<ServiceType, Integer>();
    }

    public DefaultNodeInfo(String viewUri, InetAddress hostname, Map<ServiceType, Integer> direct, Map<ServiceType, Integer> ssl) {
        this.viewUri = viewUri;
        this.hostname = hostname;
        this.directServices = direct;
        this.sslServices = ssl;
