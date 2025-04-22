
    @Override
    public Statement withDeferAndNodes(String... nodeNames) {
        return with(true, nodeNames);
    }

    @Override
    public Statement withDeferAndNodes(Collection<String> nodeNames) {
        String[] nodeNamesArray;
        if (nodeNames == null || nodeNames.isEmpty()) {
            nodeNamesArray = null;
        } else {
            nodeNamesArray = nodeNames.toArray(new String[nodeNames.size()]);
        }
        return with(true, nodeNamesArray);
    }

