        } else if (request instanceof ObserveSeqnoRequest) {
            if (status.isSuccess()) {
                byte format = content.readByte();
                switch(format) {
                    case 0:
                        response = new NoFailoverObserveSeqnoResponse(
                            ((ObserveSeqnoRequest) request).master(),
                            content.readShort(),
                            content.readLong(),
                            content.readLong(),
                            content.readLong(),
                            status,
                            statusCode,
                            bucket,
                            request
                        );
                        break;
                    case 1:
                        response = new FailoverObserveSeqnoResponse(
                            ((ObserveSeqnoRequest) request).master(),
                            content.readShort(),
                            content.readLong(),
                            content.readLong(),
                            content.readLong(),
                            content.readLong(),
                            content.readLong(),
                            status,
                            statusCode,
                            bucket,
                            request
                        );
                        break;
                    default:
                        throw new IllegalStateException("Unknown format for observe-seq: " + format);
                }
            } else {
                response = new NoFailoverObserveSeqnoResponse(((ObserveSeqnoRequest) request).master(), (short) 0, 0,
                    0, 0, status, statusCode, bucket, request);
            }
            releaseContent(content);
