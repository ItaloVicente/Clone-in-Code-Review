        String rid = clientId == null ? requestId : clientId + " / " + requestId;
        queryRowObservable.withTraceIdentifier("queryRow." + rid);
        queryErrorObservable.withTraceIdentifier("queryError." + rid);
        queryInfoObservable.withTraceIdentifier("queryInfo." + rid);
        querySignatureObservable.withTraceIdentifier("querySignature." + rid);

