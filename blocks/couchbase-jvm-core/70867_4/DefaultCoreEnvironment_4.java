        if (kvIoPool != null) {
            sb.append(", kvIoPool=").append(kvIoPool.getClass().getSimpleName());
            if (kvIoPoolShutdownHook == null || kvIoPoolShutdownHook instanceof  NoOpShutdownHook) {
                sb.append("!unmanaged");
            }
        } else {
            sb.append(", kvIoPool=").append("null");
        }
        if (viewIoPool != null) {
            sb.append(", viewIoPool=").append(viewIoPool.getClass().getSimpleName());
            if (viewIoPoolShutdownHook == null || viewIoPoolShutdownHook instanceof  NoOpShutdownHook) {
                sb.append("!unmanaged");
            }
        } else {
            sb.append(", viewIoPool=").append("null");
        }
        if (searchIoPool != null) {
            sb.append(", searchIoPool=").append(searchIoPool.getClass().getSimpleName());
            if (searchIoPoolShutdownHook == null || searchIoPoolShutdownHook instanceof  NoOpShutdownHook) {
                sb.append("!unmanaged");
            }
        } else {
            sb.append(", searchIoPool=").append("null");
        }
        if (queryIoPool != null) {
            sb.append(", queryIoPool=").append(queryIoPool.getClass().getSimpleName());
            if (queryIoPoolShutdownHook == null || queryIoPoolShutdownHook instanceof  NoOpShutdownHook) {
                sb.append("!unmanaged");
            }
        } else {
            sb.append(", queryIoPool=").append("null");
        }

