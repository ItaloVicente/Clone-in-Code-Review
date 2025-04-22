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
        } //else this should be a scalar, skip processor

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
