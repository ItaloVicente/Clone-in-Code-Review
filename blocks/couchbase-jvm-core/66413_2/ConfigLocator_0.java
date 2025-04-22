            int nodeSize = nodes.size();
            int offset = (int) counter++ % nodeSize;

            for (int i = offset; i < nodeSize; i++) {
                Node node = nodes.get(i);
                if (checkNode(node, request)) {
                    node.send(request);
                    return;
                }
            }

            for (int i = 0; i < offset; i++) {
                Node node = nodes.get(i);
                if (checkNode(node, request)) {
