        switch (type) {
            case BINARY:
                return new KeyValueService(hostname, bucket, password, port, env, responseBuffer);
            case VIEW:
                return new ViewService(hostname, bucket, password, port, env, responseBuffer);
            case CONFIG:
                return new ConfigService(hostname, bucket, password, port, env, responseBuffer);
            case QUERY:
                return new QueryService(hostname, bucket, password, port, env, responseBuffer);
            case DCP:
                return new DCPService(hostname, bucket, password, port, env, responseBuffer);
            case SEARCH:
                return new SearchService(hostname, bucket, password, port, env, responseBuffer);
            default:
                throw new IllegalArgumentException("Unknown Service Type: " + type);
