    @Override
    public Statement withNodes(String... nodeNames) {
        return with(false, nodeNames);
    }

    @Override
    public Statement withNodes(Collection<String> nodeNames) {
        String[] nodeNamesArray;
        if (nodeNames == null || nodeNames.isEmpty()) {
            nodeNamesArray = null;
        } else {
            nodeNamesArray = nodeNames.toArray(new String[nodeNames.size()]);
        }
        return with(false, nodeNamesArray);
    }

