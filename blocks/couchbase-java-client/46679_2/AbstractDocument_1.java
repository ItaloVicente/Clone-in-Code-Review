
    protected void writeToSerializedStream(ObjectOutputStream stream) throws IOException {
        stream.writeLong(cas);
        stream.writeInt(expiry);
        stream.writeUTF(id);
        stream.writeObject(content);
    }

    @SuppressWarnings("unchecked")
    protected void readFromSerializedStream(final ObjectInputStream stream) throws IOException, ClassNotFoundException {
        cas = stream.readLong();
        expiry = stream.readInt();
        id = stream.readUTF();
        content = (T) stream.readObject();
    }
