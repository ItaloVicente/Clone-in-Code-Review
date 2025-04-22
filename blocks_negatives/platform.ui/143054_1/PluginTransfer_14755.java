        try {
            byte[] bytes = (byte[]) super.nativeToJava(transferData);
            ByteArrayInputStream in = new ByteArrayInputStream(bytes);
            DataInputStream dataIn = new DataInputStream(in);
            String extensionName = dataIn.readUTF();
            int len = dataIn.readInt();
            byte[] pluginData = new byte[len];
            dataIn.readFully(pluginData);
            return new PluginTransferData(extensionName, pluginData);
        } catch (IOException e) {
