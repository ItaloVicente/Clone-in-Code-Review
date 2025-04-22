    AsyncConnectionManager connMgr =
        new AsyncConnectionManager(
            new HttpHost(a.getHostName(), a.getPort()), NUM_CONNS,
            protocolHandler, params);
    getLogger().info("Added %s to connect queue", a);

    ViewNode node = connFactory.createViewNode(a, connMgr);
    node.init();
    nodeList.add(node);
