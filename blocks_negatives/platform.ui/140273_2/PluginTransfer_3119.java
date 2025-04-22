        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            DataOutputStream dataOut = new DataOutputStream(out);
            dataOut.writeUTF(realData.getExtensionId());
            dataOut.writeInt(realData.getData().length);
            dataOut.write(realData.getData());
            dataOut.close();
            super.javaToNative(out.toByteArray(), transferData);
        } catch (IOException e) {
