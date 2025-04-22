        if (analyticsIoPool != null) {
            sb.append(", analyticsIoPool=").append(analyticsIoPool.getClass().getSimpleName());
            if (analyticsIoPoolShutdownHook == null || analyticsIoPoolShutdownHook instanceof  NoOpShutdownHook) {
                sb.append("!unmanaged");
            }
        } else {
            sb.append(", analyticsIoPool=").append("null");
        }
