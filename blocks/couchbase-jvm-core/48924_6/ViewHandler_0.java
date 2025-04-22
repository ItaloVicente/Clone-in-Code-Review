        if (responseHeader.getStatus().code() == 200) {
            int openBracketPos = responseContent.bytesBefore((byte) '[') + responseContent.readerIndex();
            int closeBracketLength = findSectionClosingPosition(responseContent, '[', ']') - openBracketPos + 1;
            ByteBuf slice = responseContent.slice(openBracketPos, closeBracketLength);
            viewErrorObservable.onNext("{\"errors\":" + slice.toString(CharsetUtil.UTF_8) + "}");
        } else {
            viewErrorObservable.onNext("{\"errors\":[" + responseContent.toString(CharsetUtil.UTF_8) + "]}");
        }

        viewErrorObservable.onCompleted();
