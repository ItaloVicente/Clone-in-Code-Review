    private static CouchbaseResponse handleSubdocumentResponseMessages(BinaryRequest request, FullBinaryMemcacheResponse msg,
         ChannelHandlerContext ctx, ResponseStatus status, boolean seqOnMutation) {
        if (!(request instanceof BinarySubdocRequest))
            return null;
        BinarySubdocRequest subdocRequest = (BinarySubdocRequest) request;
        long cas = msg.getCAS();
        short statusCode = msg.getStatus();
        String bucket = request.bucket();

        MutationToken mutationToken = null;
        if (msg.getExtrasLength() > 0) {
            mutationToken = extractToken(seqOnMutation, status.isSuccess(), msg.getExtras(), request.partition());
        }

        ByteBuf fragment;
        if (msg.content() != null && msg.content().readableBytes() > 0) {
            fragment = msg.content();
        } else if (msg.content() != null) {
            msg.content().release();
            fragment = Unpooled.EMPTY_BUFFER;
        } else {
            fragment = Unpooled.EMPTY_BUFFER;
        }

        return new SimpleSubdocResponse(status, statusCode, bucket, fragment, subdocRequest, cas, mutationToken);
    }

    private static CouchbaseResponse handleSubdocumentMultiLookupResponseMessages(BinaryRequest request,
            FullBinaryMemcacheResponse msg, ChannelHandlerContext ctx, ResponseStatus status) {
        if (!(request instanceof BinarySubdocMultiLookupRequest))
            return null;
        BinarySubdocMultiLookupRequest subdocRequest = (BinarySubdocMultiLookupRequest) request;

        short statusCode = msg.getStatus();
        String bucket = request.bucket();

        ByteBuf body = msg.content();
        List<LookupResult> responses;
        if (status.isSuccess() || ResponseStatus.SUBDOC_MULTI_PATH_FAILURE.equals(status)) {
            long bodyLength = body.readableBytes();
            List<LookupCommand> commands = subdocRequest.commands();
            responses = new ArrayList<LookupResult>(commands.size());
            for (LookupCommand cmd : commands) {
                if (msg.content().readableBytes() < 6) {
                    body.release();
                    throw new IllegalStateException("Expected " + commands.size() + " lookup responses, only got " +
                            responses.size() + ", total of " + bodyLength + " bytes");
                }
                short cmdStatus = body.readShort();
                int valueLength = body.readInt();
                ByteBuf value = ctx.alloc().buffer(valueLength, valueLength);
                value.writeBytes(body, valueLength);

                responses.add(new LookupResult(cmdStatus, ResponseStatusConverter.fromBinary(cmdStatus),
                        cmd.path(), cmd.lookup(), value));
            }
        } else {
            responses = Collections.emptyList();
        }
        body.release();

        return new MultiLookupResponse(status, statusCode, bucket, responses, subdocRequest);
    }

    private static CouchbaseResponse handleSubdocumentMultiMutationResponseMessages(BinaryRequest request,
            FullBinaryMemcacheResponse msg, ChannelHandlerContext ctx, ResponseStatus status, boolean seqOnMutation) {
        if (!(request instanceof BinarySubdocMultiMutationRequest))
            return null;
        BinarySubdocMultiMutationRequest subdocRequest = (BinarySubdocMultiMutationRequest) request;

        long cas = msg.getCAS();
        short statusCode = msg.getStatus();
        String bucket = request.bucket();

        MutationToken mutationToken = null;
        if (msg.getExtrasLength() > 0) {
            mutationToken = extractToken(seqOnMutation, status.isSuccess(), msg.getExtras(), request.partition());
        }

        ByteBuf body = msg.content();
        MultiMutationResponse response;
        if (status.isSuccess()) {
            response = new MultiMutationResponse(bucket, subdocRequest, cas, mutationToken);
        } else if (ResponseStatus.SUBDOC_MULTI_PATH_FAILURE.equals(status)) {
            short firstErrorCode = body.readShort();
            byte firstErrorIndex = body.readByte();
            response = new MultiMutationResponse(status, statusCode, bucket, firstErrorIndex, firstErrorCode,
                    subdocRequest, cas, mutationToken);
        } else {
            response = new MultiMutationResponse(status, statusCode, bucket, subdocRequest, cas, mutationToken);
        }
        body.release();
        return response;
    }

