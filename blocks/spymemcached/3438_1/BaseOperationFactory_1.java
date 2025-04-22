        if (!op.getNotMyVbucketNodes().isEmpty()) {
            for (Operation operation : rv) {
                if (operation instanceof KeyedOperation) {
                    Collection<MemcachedNode> notMyVbucketNodes = op.getNotMyVbucketNodes();
                    ((KeyedOperation) operation).setNotMyVbucketNodes(notMyVbucketNodes);
                }
            }
        }
