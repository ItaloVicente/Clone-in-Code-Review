        BinarySubdocMultiMutationRequest subdocRequest = (BinarySubdocMultiMutationRequest) request;

        long cas = msg.getCAS();
        short statusCode = msg.getStatus();
        String bucket = request.bucket();

        MutationToken mutationToken = null;
        if (msg.getExtrasLength() > 0) {
            mutationToken = extractToken(bucket, seqOnMutation, status.isSuccess(), msg.getExtras(), request.partition());
        }

        MultiMutationResponse response;
        ByteBuf body = msg.content();
        List<MultiResult<Mutation>> responses;
        if (status.isSuccess()) {
            List<MutationCommand> commands = subdocRequest.commands();
            responses = new ArrayList<MultiResult<Mutation>>(commands.size());
            ListIterator<MutationCommand> it = commands.listIterator();
            int explicitResultSize = 0;
            while(msg.content().readableBytes() >= 7) {
                explicitResultSize++;
                byte responseIndex = body.readByte();
                short responseStatus = body.readShort(); //will this always be SUCCESS?
                int responseLength = body.readInt();
                ByteBuf responseValue;
                if (responseLength > 0) {
                    responseValue = ctx.alloc().buffer(responseLength, responseLength);
                    responseValue.writeBytes(body, responseLength);
                } else {
                    responseValue = Unpooled.EMPTY_BUFFER; //can an explicit response be 0-length (empty)?
                }

                if (it.nextIndex() > responseIndex) {
                    body.release();
                    throw new IllegalStateException("Unable to interpret multi mutation response, responseIndex = " +
                        responseIndex + " while next available command was #" + it.nextIndex());
                }

                while(it.nextIndex() < responseIndex) {
                    MutationCommand noResultCommand = it.next();
                    responses.add(MultiResult.create(KeyValueStatus.SUCCESS.code(), ResponseStatus.SUCCESS,
                            noResultCommand.path(), noResultCommand.mutation(),
                            Unpooled.EMPTY_BUFFER));
                }

                MutationCommand cmd = it.next();
                responses.add(MultiResult.create(responseStatus, ResponseStatusConverter.fromBinary(responseStatus),
                        cmd.path(), cmd.mutation(), responseValue));
            }
            while(it.hasNext()) {
                MutationCommand noResultCommand = it.next();
                responses.add(MultiResult.create(KeyValueStatus.SUCCESS.code(), ResponseStatus.SUCCESS,
                        noResultCommand.path(), noResultCommand.mutation(),
                        Unpooled.EMPTY_BUFFER));
            }

            if (responses.size() != commands.size()) {
                body.release();
                throw new IllegalStateException("Multi mutation spec size and result size differ: " + commands.size() +
                    " vs " + responses.size() + ", including " + explicitResultSize + " explicit results");
            }

            response = new MultiMutationResponse(bucket, subdocRequest, cas, mutationToken, responses);
        } else if (ResponseStatus.SUBDOC_MULTI_PATH_FAILURE.equals(status)) {
            byte firstErrorIndex = body.readByte();
            short firstErrorCode = body.readShort();
            response = new MultiMutationResponse(status, statusCode, bucket, firstErrorIndex, firstErrorCode,
                    subdocRequest, cas, mutationToken);
        } else {
            response = new MultiMutationResponse(status, statusCode, bucket, subdocRequest, cas, mutationToken);
        }
        body.release();
        return response;
