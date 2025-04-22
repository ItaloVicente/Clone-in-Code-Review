    protected ByteBuf doEncodeMulti(MultiValue<?> multiValue, String transcodingErrorMessage) throws TranscodingException {
        final ByteBufOutputStream out = new ByteBufOutputStream(Unpooled.buffer(4 * multiValue.size()));
        try {
            for (Iterator<?> iterator = multiValue.iterator(); iterator.hasNext(); ) {
                Object o = iterator.next();
                mapper.writeValue(out, o);
                if (iterator.hasNext()) {
                   out.writeBytes(",);
-                }
-            }
-            return out.buffer();
-        } catch (IOException e) {
-            throw new TranscodingException(transcodingErrorMessage, e);
-        }
-        //changing the OutputStream concrete implementation would probably require to close() in a finally block
+    protected <T> T byteArrayToClass(byte[] byteArray, int offset, int length, Class<? extends T> clazz) throws IOException {
+        throw new UnsupportedOperationException(byteArrayToClass is unused by custom decodeWithMessage");
