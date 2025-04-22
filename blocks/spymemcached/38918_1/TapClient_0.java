    public TapClient(String bucketname, String password, List<InetSocketAddress> addrs) {

        AuthDescriptor ad = new AuthDescriptor(new String[]{"PLAIN"},
                new PlainCallbackHandler(bucketname, password));

        cfb = new net.spy.memcached.ConnectionFactoryBuilder();
        cfb.setProtocol(Protocol.BINARY).setAuthDescriptor(ad);

        this.rqueue = new LinkedBlockingQueue<Object>();
        this.omap = new HashMap<TapStream, TapConnectionProvider>();
        this.addrs = addrs;
        this.messagesRead = 0;
    }

