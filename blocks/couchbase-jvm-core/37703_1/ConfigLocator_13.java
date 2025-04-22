                    return new Node[]{node};
                }
            }
        } else if (request instanceof BucketStreamingRequest) {
            int item = (int) counter % nodes.size();
            int i = 0;
            for (Node node : nodes) {
                if (i++ == item) {
