        this.nodeInfo = portInfos == null ? nodeInfos : nodeInfoFromExtended(portInfos);
    }

    private static List<NodeInfo> nodeInfoFromExtended(final List<PortInfo> nodesExt) {
        List<NodeInfo> converted = new ArrayList<NodeInfo>(nodesExt.size());
        for (PortInfo nodeExt : nodesExt) {
            converted.add(new DefaultNodeInfo(nodeExt.hostname(), nodeExt.ports(), nodeExt.sslPorts()));
