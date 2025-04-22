
        Set<String> clusterNodes = new HashSet<String>();
        for (NetworkAddress node : this.clusterNodes) {
            clusterNodes.add(node.toString());
        }
        result.put("clusterNodes", clusterNodes);

