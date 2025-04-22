        if (portInfos == null) {
            this.nodeInfo = nodeInfos;
        } else {
            List<NodeInfo> modified = new ArrayList<NodeInfo>();
            for (int i = 0; i < nodeInfos.size(); i++) {
                modified.add(new DefaultNodeInfo(nodeInfos.get(i).viewUri(), nodeInfos.get(i).hostname(), portInfos.get(i).ports(), portInfos.get(i).sslPorts()));
            }
            this.nodeInfo = modified;
        }
