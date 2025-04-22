    @Override
    public UsingPath indexes(List<String> indexNames) {
        if (indexNames.isEmpty()) {
            throw new IllegalArgumentException("indexNames must have at least one name");
        }
        String first = indexNames.get(0);
        if (indexNames.size() > 1) {
            String[] others = indexNames.subList(1, indexNames.size()).toArray(new String[indexNames.size() - 1]);
            element(new IndexNamesElement(first, others));
        } else {
            element(new IndexNamesElement(first));
        }
        return new DefaultUsingPath(this);
    }

