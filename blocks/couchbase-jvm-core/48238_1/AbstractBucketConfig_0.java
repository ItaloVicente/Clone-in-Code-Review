        for (int i = 0; i < nodesExt.size(); i++) {
            InetAddress hostname = nodesExt.get(i).hostname();
            if (hostname == null) {
                hostname = nodeInfos.get(i).hostname();
            }
            converted.add(new DefaultNodeInfo(hostname, nodesExt.get(i).ports(), nodesExt.get(i).sslPorts()));
