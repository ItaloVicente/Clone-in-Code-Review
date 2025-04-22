        int nodeSize = nodes.size();
        int offset = (int) MathUtils.floorMod(counter++, nodeSize);
        Node node = nodes.get(offset);
        if (node != null) {
            node.send(request);
        } else {
            LOGGER.warn("Locator found selected node to be null, this is a bug. {}, {}", request, nodes);
            RetryHelper.retryOrCancel(env, request, responseBuffer);
