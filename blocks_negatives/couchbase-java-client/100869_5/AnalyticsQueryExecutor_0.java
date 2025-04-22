                final Observable<AsyncAnalyticsQueryRow> rows = response.rows().map(new Func1<ByteBuf, AsyncAnalyticsQueryRow>() {
                    @Override
                    public AsyncAnalyticsQueryRow call(ByteBuf byteBuf) {
                        try {
                            TranscoderUtils.ByteBufToArray rawData = TranscoderUtils.byteBufToByteArray(byteBuf);
                            byte[] copy = Arrays.copyOfRange(rawData.byteArray, rawData.offset, rawData.offset + rawData.length);
                            return new DefaultAsyncAnalyticsQueryRow(copy);
                        } catch (Exception e) {
                            throw new TranscodingException("Could not decode Analytics Query Row.", e);
                        } finally {
                            byteBuf.release();
                        }
                    }
                });
