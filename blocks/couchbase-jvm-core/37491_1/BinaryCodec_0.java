        } else if (current instanceof GetRequest) {
            in.add(new GetResponse(status, cas, bucket, msg.content().copy(), currentRequest));
        } else if (current instanceof InsertRequest) {
            in.add(new InsertResponse(status, cas, bucket, msg.content().copy(), currentRequest));
        } else if (current instanceof UpsertRequest) {
            in.add(new UpsertResponse(status, cas, bucket, msg.content().copy(), currentRequest));
        } else if (current instanceof ReplaceRequest) {
            in.add(new ReplaceResponse(status, cas, bucket, msg.content().copy(), currentRequest));
        } else if (current instanceof RemoveRequest) {
            in.add(new RemoveResponse(convertStatus(msg.getStatus()), bucket, msg.content().copy(), currentRequest));
