            if (queryRowClosingPositionProcessor == null) {
                queryRowClosingPositionProcessor = new ClosingPositionBufProcessor('{', '}', true);
                queryRowClosingProcessorIndex = responseContent.readerIndex();
            }

            int lengthToScan = responseContent.writerIndex() - this.queryRowClosingProcessorIndex;
            int closeBracketPos = responseContent.forEachByte(queryRowClosingProcessorIndex,
                lengthToScan, queryRowClosingPositionProcessor);

