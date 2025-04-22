                    try  {
                        byte[] raw = null;
                        if (isIncludeRaw()) {
                            TranscoderUtils.ByteBufToArray rawData = TranscoderUtils.byteBufToByteArray(lookupResult.value());
                            raw = Arrays.copyOfRange(rawData.byteArray, rawData.offset, rawData.offset + rawData.length);
                        }
