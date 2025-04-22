
        if (FORCE_OLD_SERVICES) {
            switch (type) {
                case BINARY:
                    return new OldKeyValueService(hostname, bucket, password, port, env, responseBuffer);
                case VIEW:
                    return new OldViewService(hostname, bucket, password, port, env, responseBuffer);
                case CONFIG:
                    return new ConfigService(hostname, bucket, password, port, env, responseBuffer);
                case QUERY:
                    return new OldQueryService(hostname, bucket, password, port, env, responseBuffer);
                case DCP:
                    return new DCPService(hostname, bucket, password, port, env, responseBuffer);
                case SEARCH:
                    return new OldSearchService(hostname, bucket, password, port, env, responseBuffer);
                default:
                    throw new IllegalArgumentException("Unknown Service Type: " + type);
            }
        } else {
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
            }
