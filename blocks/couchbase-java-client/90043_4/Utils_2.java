        if (request != null) {
            return "localId: " + request.lastLocalId() + ", opId: " + request.operationId() + ", local: "
                + request.lastLocalSocket()
                + ", remote: " + request.lastRemoteSocket() + ", timeout: " + timeout + "us";
        } else {
            return "localId: unknown, opId: unknown, local: unknown, remote: unknown, timeout: " + timeout + "us";
        }
