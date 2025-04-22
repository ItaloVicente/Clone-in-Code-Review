    /**
     * Checks if there's not another section opened before the current one,
     * which starts at openBracketPos
     *
     * @param openBracketPos the position of the current section's opening bracket
     * @return true if transition to next state should be made because there's a new
     * section opening.
     */
    private boolean isEmptySection(int openBracketPos) {
        int nextColon = findNextChar(responseContent, ':');
        return nextColon > -1 && nextColon < openBracketPos;
    }

    /**
     * Base method to handle the response for the generic query request.
     *
     * It waits for the first few bytes on the actual response to determine if an error is raised or if a successful
     * response can be expected. The actual error and/or chunk parsing is deferred to other parts of this handler.
     *
     * @return a {@link CouchbaseResponse} if eligible.
     */
    private CouchbaseResponse handleGenericQueryResponse(boolean lastChunk) {
        String requestId;
        String clientId = "";

        if (responseContent.readableBytes() < MINIMUM_WINDOW_FOR_REQUESTID + MINIMUM_WINDOW_FOR_CLIENTID_TOKEN
                && !lastChunk) {
        }

        int startIndex = responseContent.readerIndex();

        if (responseContent.readableBytes() >= MINIMUM_WINDOW_FOR_REQUESTID) {
            responseContent.skipBytes(findNextChar(responseContent, ':'));
            responseContent.skipBytes(findNextChar(responseContent, '"') + 1);
            int endOfId = findNextChar(responseContent, '"');
            ByteBuf slice = responseContent.readSlice(endOfId);
            requestId = slice.toString(CHARSET);
        } else {
            return null;
        }


        if (responseContent.readableBytes() >= MINIMUM_WINDOW_FOR_CLIENTID_TOKEN
                && findNextChar(responseContent, ':') < MINIMUM_WINDOW_FOR_CLIENTID_TOKEN) {
            responseContent.markReaderIndex();
            ByteBuf slice = responseContent.readSlice(findNextChar(responseContent, ':'));
            if (slice.toString(CHARSET).contains("clientContextID")) {
                responseContent.skipBytes(findNextChar(responseContent, '"') + 1); //opening of clientId
                int clientIdSize = findNextCharNotPrefixedBy(responseContent, '"', '\\');
                if (clientIdSize < 0) {
                    responseContent.readerIndex(startIndex);
                    return null;
                }
                clientId = responseContent.readSlice(clientIdSize).toString(CHARSET);
                boolean hasClosingQuote = responseContent.readableBytes() > 0;
                if (hasClosingQuote) {
                    responseContent.skipBytes(1);
                }
                int openingNextToken = findNextChar(responseContent, '"');
                if (openingNextToken > -1) {
                    responseContent.skipBytes(openingNextToken);
                }
            } else {
                responseContent.resetReaderIndex();
            }
        }

        boolean success = true;
        if (responseContent.readableBytes() >= 20) {
            ByteBuf peekForErrors = responseContent.slice(responseContent.readerIndex(), 20);
            if (peekForErrors.toString(CHARSET).contains("errors")) {
                success = false;
            }
        } else {
            responseContent.readerIndex(startIndex);
            return null;
        }

        ResponseStatus status = ResponseStatusConverter.fromHttp(responseHeader.getStatus().code());
        if (!success) {
            status = ResponseStatus.FAILURE;
        }

        Scheduler scheduler = env().scheduler();
        long ttl = env().autoreleaseAfter();
        queryRowObservable = UnicastAutoReleaseSubject.create(ttl, TimeUnit.MILLISECONDS, scheduler);
        queryErrorObservable = UnicastAutoReleaseSubject.create(ttl, TimeUnit.MILLISECONDS, scheduler);
        queryStatusObservable = AsyncSubject.create();
        queryInfoObservable = UnicastAutoReleaseSubject.create(ttl, TimeUnit.MILLISECONDS, scheduler);
        querySignatureObservable = UnicastAutoReleaseSubject.create(ttl, TimeUnit.MILLISECONDS, scheduler);

        String rid = clientId == null ? requestId : clientId + " / " + requestId;
        queryRowObservable.withTraceIdentifier("queryRow." + rid);
        queryErrorObservable.withTraceIdentifier("queryError." + rid);
        queryInfoObservable.withTraceIdentifier("queryInfo." + rid);
        querySignatureObservable.withTraceIdentifier("querySignature." + rid);

        return new GenericQueryResponse(
                queryErrorObservable.onBackpressureBuffer().observeOn(scheduler),
                queryRowObservable.onBackpressureBuffer().observeOn(scheduler),
                querySignatureObservable.onBackpressureBuffer().observeOn(scheduler),
                queryStatusObservable.onBackpressureBuffer().observeOn(scheduler),
                queryInfoObservable.onBackpressureBuffer().observeOn(scheduler),
                currentRequest(),
                status, requestId, clientId
        );
    }

    /**
     * Generic dispatch method to parse the query response chunks.
     *
     * Depending on the state the parser is currently in, several different sub-methods are called
     * which do the actual handling.
     *
     * @param lastChunk if the current emitted content body is the last one.
     */
    private void parseQueryResponse(boolean lastChunk) {
        if (sectionDone || queryParsingState == QUERY_STATE_INITIAL) {
            queryParsingState = transitionToNextToken(lastChunk);
        }

        if (queryParsingState == QUERY_STATE_SIGNATURE) {
            parseQuerySignature(lastChunk);
        }

        if (queryParsingState == QUERY_STATE_ROWS_DECIDE) {
            decideBetweenRawAndObjects(lastChunk);
        }
        if (queryParsingState == QUERY_STATE_ROWS) {
            parseQueryRows(lastChunk);
        } else if (queryParsingState == QUERY_STATE_ROWS_RAW) {
            parseQueryRowsRaw(lastChunk);
        }

        if (queryParsingState == QUERY_STATE_ERROR) {
            parseQueryError(lastChunk);
        }

        if (queryParsingState == QUERY_STATE_WARNING) {
        }

        if (queryParsingState == QUERY_STATE_STATUS) {
            parseQueryStatus(lastChunk);
        }

        if (queryParsingState == QUERY_STATE_INFO) {
            parseQueryInfo(lastChunk);
        } else if (queryParsingState == QUERY_STATE_NO_INFO) {
            finishInfo();
        }

        if (queryParsingState == QUERY_STATE_DONE) {
            sectionDone = lastChunk;
            if (sectionDone) {
                cleanupQueryStates();
            }
        }
    }

    /**
     * Peek the next token, returning the QUERY_STATE corresponding to it and placing the readerIndex just after
     * the token's ':'. Must be at the end of the previous token.
     *
     * @param lastChunk true if this is the last chunk
     * @return the next QUERY_STATE
     */
    private byte transitionToNextToken(boolean lastChunk) {
        int endNextToken = findNextChar(responseContent, ':');
        if (endNextToken < 0 && !lastChunk) {
            return queryParsingState;
        }

        if (endNextToken < 0 && lastChunk && queryParsingState >= QUERY_STATE_STATUS) {
            return QUERY_STATE_NO_INFO;
        }

        byte newState;
        ByteBuf peekSlice = responseContent.readSlice(endNextToken + 1);
        String peek = peekSlice.toString(CHARSET);
        if (peek.contains("\"signature\":")) {
            newState = QUERY_STATE_SIGNATURE;
        } else if (peek.endsWith("\"results\":")) {
            newState = QUERY_STATE_ROWS_DECIDE;
        } else if (peek.endsWith("\"status\":")) {
            newState = QUERY_STATE_STATUS;
        } else if (peek.endsWith("\"errors\":")) {
            newState = QUERY_STATE_ERROR;
        } else if (peek.endsWith("\"warnings\":")) {
            newState = QUERY_STATE_WARNING;
        } else if (peek.endsWith("\"metrics\":")) {
            newState = QUERY_STATE_INFO;
        } else {
            if (lastChunk) {
                IllegalStateException e = new IllegalStateException("Error parsing query response (in TRANSITION) at \""
                        + peek + "\", enable trace to see response content");
                if (LOGGER.isTraceEnabled()) {
                    LOGGER.trace(responseContent.toString(CHARSET), e);
                }
                throw e;
            } else {
                return queryParsingState;
            }
        }

        sectionDone = false;
        return newState;
    }

    private void decideBetweenRawAndObjects(boolean lastChunk) {
        responseContent.markReaderIndex();
        int openArrayPos = findNextChar(responseContent, '[');
        if (openArrayPos > -1) {
            responseContent.skipBytes(openArrayPos + 1);
        } else {
            responseContent.resetReaderIndex();
            if (lastChunk == true) {
                throw new IllegalStateException("Unable to decide between raw and objects with content " + responseContent.toString(CHARSET));
            }
        }

        int spaceToSkip = responseContent.forEachByte(new WhitespaceSkipper());
        if (spaceToSkip > -1) {
            responseContent.readerIndex(spaceToSkip);
        } else {
            responseContent.resetReaderIndex();
            return;
        }

        if (responseContent.isReadable()) {
            byte first = responseContent.getByte(responseContent.readerIndex());
            if (first == '{') {
                queryParsingState = QUERY_STATE_ROWS;
            } else if (first == ']') {
                sectionDone();
                queryParsingState = transitionToNextToken(lastChunk);
            } else {
                queryParsingState = QUERY_STATE_ROWS_RAW;
            }
        } else {
            responseContent.resetReaderIndex();
        }
    }

    private void sectionDone() {
        this.sectionDone = true;
        responseContent.discardReadBytes();
    }

    /**
     * Parse the signature section in the N1QL response.
     */
    private void parseQuerySignature(boolean lastChunk) {
        ByteBufProcessor processor = null;
        int openPos = responseContent.forEachByte(new WhitespaceSkipper()) - responseContent.readerIndex();
        if (openPos < 0) {
            return;
        }
        char openChar = (char) responseContent.getByte(responseContent.readerIndex() + openPos);
        if (openChar == '{') {
            processor = new ClosingPositionBufProcessor('{', '}', true);
        } else if (openChar == '[') {
            processor = new ClosingPositionBufProcessor('[', ']', true);
        } else if (openChar == '"') {
            processor = new StringClosingPositionBufProcessor();

        int closePos;
        if (processor != null) {
            closePos = responseContent.forEachByte(processor) - responseContent.readerIndex();
        } else {
            closePos = findNextChar(responseContent, ',') - 1;
        }
        if (closePos > 0) {
            responseContent.skipBytes(openPos);
            int length = closePos - openPos + 1;
            ByteBuf signature = responseContent.readSlice(length);
            querySignatureObservable.onNext(signature.copy());
        } else {
            return;
        }
        sectionDone();
        queryParsingState = transitionToNextToken(lastChunk);
    }

    /**
     * Parses the query rows from the content stream as long as there is data to be found.
     */
    private void parseQueryRows(boolean lastChunk) {
        while (true) {
            int openBracketPos = findNextChar(responseContent, '{');
            if (isEmptySection(openBracketPos) || (lastChunk && openBracketPos < 0)) {
                sectionDone();
                queryParsingState = transitionToNextToken(lastChunk);
                break;
            }

            int closeBracketPos = findSectionClosingPosition(responseContent, '{', '}');
            if (closeBracketPos == -1) {
                break;
            }

            int length = closeBracketPos - openBracketPos - responseContent.readerIndex() + 1;
            responseContent.skipBytes(openBracketPos);
            ByteBuf resultSlice = responseContent.readSlice(length);
            queryRowObservable.onNext(resultSlice.copy());
            responseContent.discardSomeReadBytes();
        }
    }

    /**
     * Parses the query raw results from the content stream as long as there is data to be found.
     */
    private void parseQueryRowsRaw(boolean lastChunk) {
        while (responseContent.isReadable()) {
            int splitPos = findSplitPosition(responseContent, ',');
            int arrayEndPos = findSplitPosition(responseContent, ']');

            boolean doSectionDone = false;

            if (splitPos == -1 && arrayEndPos == -1) {
                break;
            } else if (arrayEndPos > 0 && (arrayEndPos < splitPos || splitPos == -1)) {
                splitPos = arrayEndPos;
                doSectionDone = true;
            }

            int length = splitPos - responseContent.readerIndex();
            ByteBuf resultSlice = responseContent.readSlice(length);
            queryRowObservable.onNext(resultSlice.copy());
            responseContent.skipBytes(1);
            responseContent.discardReadBytes();

            if (doSectionDone) {
                sectionDone();
                queryParsingState = transitionToNextToken(lastChunk);
                break;
            }
        }
    }

    /**
     * Parses the errors and warnings from the content stream as long as there are some to be found.
     */
    private void parseQueryError(boolean lastChunk) {
        while (true) {
            int openBracketPos = findNextChar(responseContent, '{');
            if (isEmptySection(openBracketPos) || (lastChunk && openBracketPos < 0)) {
                sectionDone();
                break;
            }

            int closeBracketPos = findSectionClosingPosition(responseContent, '{', '}');
            if (closeBracketPos == -1) {
                break;
            }

            int length = closeBracketPos - openBracketPos - responseContent.readerIndex() + 1;
            responseContent.skipBytes(openBracketPos);
            ByteBuf resultSlice = responseContent.readSlice(length);
            queryErrorObservable.onNext(resultSlice.copy());
        }
    }

    /**
     * Last before the end of the stream, we can now parse the final result status
     * (including full execution of the query).
     */
    private void parseQueryStatus(boolean lastChunk) {
        querySignatureObservable.onCompleted();
        queryRowObservable.onCompleted();
        queryErrorObservable.onCompleted();

        responseContent.markReaderIndex();
        responseContent.skipBytes(findNextChar(responseContent, '"') + 1);
        int endStatus = findNextChar(responseContent, '"');
        if (endStatus > -1) {
            ByteBuf resultSlice = responseContent.readSlice(endStatus);
            queryStatusObservable.onNext(resultSlice.toString(CHARSET));
            queryStatusObservable.onCompleted();
            sectionDone();
            queryParsingState = transitionToNextToken(lastChunk);
        } else {
            responseContent.resetReaderIndex();
        }
    }

    /**
     * At the end of the response stream, parse out the info portion (metrics).
     *
     * For the sake of easiness, since we know it comes at the end, we wait until the full data is together and read
     * the info json objects off in one shot (but they are still emitted separately).
     *
     * @param last if this batch is the last one.
     */
    private void parseQueryInfo(boolean last) {
        int openBracketPos = findNextChar(responseContent, '{');
        int closeBracketPos = findSectionClosingPosition(responseContent, '{', '}');
        if (closeBracketPos == -1) {
            if (last) {
                throw new IllegalStateException("Could not find metrics closing in last chunk");
            } else {
            }
        }

        int from = responseContent.readerIndex() + openBracketPos;
        int to = closeBracketPos - openBracketPos - responseContent.readerIndex() + 1;
        queryInfoObservable.onNext(responseContent.slice(from, to).copy());
        responseContent.readerIndex(to + openBracketPos);

        finishInfo();
    }

    private void finishInfo() {
        queryInfoObservable.onCompleted();
        sectionDone();
        queryParsingState = QUERY_STATE_DONE;
    }

    /**
     * Clean up the query states after all rows have been consumed.
     */
    private void cleanupQueryStates() {
        finishedDecoding();
        queryInfoObservable = null;
        queryRowObservable = null;
        queryErrorObservable = null;
        queryStatusObservable = null;
        querySignatureObservable = null;
        queryParsingState = QUERY_STATE_INITIAL;
