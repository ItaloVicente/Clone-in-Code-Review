        int openPos = findNextChar(responseContent, '{');
            int closePos = findSectionClosingPosition(responseContent, '{', '}');
            if (closePos > 0) {
                int length = closePos - openPos - responseContent.readerIndex() + 1;
                responseContent.skipBytes(openPos);
                ByteBuf signature = responseContent.readSlice(length);
                querySignatureObservable.onNext(signature.copy());
            } else {
                return;
            }
