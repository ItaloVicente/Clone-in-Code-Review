            int errorBlockPosition = findErrorBlockPosition();

            if (errorBlockPosition > 0 && errorBlockPosition < openBracketPos) {
                responseContent.readerIndex(errorBlockPosition + responseContent.readerIndex());
                viewRowObservable.onCompleted();
                viewParsingState = QUERY_STATE_ERROR;
                return;
            }

