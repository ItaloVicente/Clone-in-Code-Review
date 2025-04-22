
        managementTimeout = longPropertyOr("managementTimeout", builder.managementTimeout());
        queryTimeout = longPropertyOr("queryTimeout", builder.queryTimeout());
        viewTimeout = longPropertyOr("viewTimeout", builder.viewTimeout());
        binaryTimeout = longPropertyOr("binaryTimeout", builder.binaryTimeout());
        connectTimeout = longPropertyOr("connectTimeout", builder.connectTimeout());
        disconnectTimeout = longPropertyOr("disconnectTimeout", builder.disconnectTimeout());
    }

    private static long longPropertyOr(String path, long def) {
        String found = System.getProperty(NAMESPACE + path);
        if (found == null) {
            return def;
        }
        return Integer.parseInt(found);
