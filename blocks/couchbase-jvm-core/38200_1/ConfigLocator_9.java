        if (request instanceof FlushRequest) {
            int item = (int) counter % nodes.size();
            int i = 0;
            for (Node node : nodes) {
                if (i++ == item) {
                    return new Node[] { node };
                }
            }
        } else if (request instanceof BucketConfigRequest) {
