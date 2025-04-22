        } else if (msg instanceof UpsertDesignDocumentRequest) {
            method = HttpMethod.PUT;
            UpsertDesignDocumentRequest queryMsg = (UpsertDesignDocumentRequest) msg;
            path.append("/").append(msg.bucket()).append("/_design/");
            path.append(queryMsg.development() ? "dev_" + queryMsg.name() : queryMsg.name());
            content = Unpooled.copiedBuffer(queryMsg.body(), CHARSET);
        } else if (msg instanceof RemoveDesignDocumentRequest) {
            method = HttpMethod.DELETE;
            RemoveDesignDocumentRequest queryMsg = (RemoveDesignDocumentRequest) msg;
            path.append("/").append(msg.bucket()).append("/_design/");
            path.append(queryMsg.development() ? "dev_" + queryMsg.name() : queryMsg.name());
