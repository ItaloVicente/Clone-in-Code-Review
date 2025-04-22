
        for (Node node : nodes) {
            if (node.hostname().equals(nodeInfo.hostname())) {
                return new Node[] { node };
            }
        }

