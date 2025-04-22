    public byte[] rawContent(int index) {
        return interpretResultRaw(resultList.get(index));
    }

    public byte[] rawContent(String path) {
        if (path == null) {
            return null;
        }
        for (SubdocOperationResult<OPERATION> result : resultList) {
            if (path.equals(result.path())) {
                return interpretResultRaw(result);
            }
        }
        return null;
    }

