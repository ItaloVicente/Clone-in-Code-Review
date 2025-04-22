        for (Node node : nodes) {
            if (!configNodes.contains(node.hostname())) {
                removeNode(node);
                node.disconnect().subscribe();
            }
        }
