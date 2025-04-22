                boolean hasClosingQuote = responseContent.readableBytes() > 0;
                if (hasClosingQuote) {
                    responseContent.skipBytes(1);
                }
                int openingNextToken = findNextChar(responseContent, '"');
                if (openingNextToken > -1) {
                    responseContent.skipBytes(openingNextToken);
                }
