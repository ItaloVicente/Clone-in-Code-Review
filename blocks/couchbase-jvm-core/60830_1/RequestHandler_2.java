        }

        Node[] locatedNodes = locator(request).locate(request, nodes, config);

        if (locatedNodes == null) {
            return;
        }

        if (locatedNodes.length > 0) {
            for (Node locatedNode : locatedNodes) {
