            extras.release();
        }

        final String key = (current instanceof AbstractKeyAwareBinaryRequest) ? ((AbstractKeyAwareBinaryRequest) current).key() : null;
        final boolean isJson = (msg.getDataType() == 1 || msg.getDataType() == 3);
        final ResponseStatus status = convertStatus(msg.getStatus());

        final CoreDocument document = new CoreDocument(key, content, flags, expiration, msg.getCAS(), isJson, status);
        final CouchbaseRequest currentRequest = (status == ResponseStatus.RETRY) ? current : null;

        if (current instanceof GetBucketConfigRequest) {
            final InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();
            in.add(new GetBucketConfigResponse(status, bucket, document.content(), InetAddress.getByName(address.getHostName())));
        } else if (current instanceof GetRequest) {
            in.add(new GetResponse(document, bucket, currentRequest));
