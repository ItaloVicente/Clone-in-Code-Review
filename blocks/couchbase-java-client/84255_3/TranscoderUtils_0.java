        if (input.hasArray()) {
            ByteBufToArray toArray = byteBufToByteArray(input);
            return mapper.readValue(toArray.byteArray, toArray.offset, toArray.length, clazz);
        } else {
            ByteBufInputStream bbis = null;
            try {
                bbis = new ByteBufInputStream(input);
                return mapper.readValue(bbis, clazz);
            }
            finally {
                if (bbis != null) {
                    bbis.close();
                }
            }
        }
