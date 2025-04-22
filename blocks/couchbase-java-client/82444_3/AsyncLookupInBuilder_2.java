                        if (isGetCount) {
                            long count = subdocumentTranscoder.decode(lookupResult.value(), Long.class);
                            return SubdocOperationResult.createResult(path, operation, status, count);
                        } else {
                            byte[] raw = null;
                            if (isIncludeRaw()) {
                                TranscoderUtils.ByteBufToArray rawData = TranscoderUtils.byteBufToByteArray(lookupResult.value());
                                raw = Arrays.copyOfRange(rawData.byteArray, rawData.offset, rawData.offset + rawData.length);
                            }
                            Object content = subdocumentTranscoder.decode(lookupResult.value(), Object.class);
                            return SubdocOperationResult.createResult(path, operation, status, content, raw);
