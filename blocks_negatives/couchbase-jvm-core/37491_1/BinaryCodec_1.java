        } else if (clazz.equals(GetRequest.class)) {
            in.add(new GetResponse(status, cas, bucket, msg.content().copy()));
        } else if (clazz.equals(InsertRequest.class)) {
            in.add(new InsertResponse(status, cas, bucket, msg.content().copy()));
        } else if (clazz.equals(UpsertRequest.class)) {
            in.add(new UpsertResponse(status, cas, bucket, msg.content().copy()));
        } else if (clazz.equals(ReplaceRequest.class)) {
            in.add(new ReplaceResponse(status, cas, bucket, msg.content().copy()));
        } else if (clazz.equals(RemoveRequest.class)) {
            in.add(new RemoveResponse(convertStatus(msg.getStatus()), bucket, msg.content().copy()));
