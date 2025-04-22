  public boolean isRandomAccessSupported() {
    return true;
  }

  public byte[] readRandomly(long position
                             int size
                             byte[] buffer
                             int offset)
          throws UnsupportedOperationException
                 IllegalArgumentException
                 IOException {
    if (position < 0 || size <= 0 ||
        (buffer != null && (offset < 0 || (offset + size) > buffer.length))) {
      throw new IllegalArgumentException("Position must be non-negative
                                         "size must be non-zero positive
                                         "offset+size must be < buffer.length!");
    }
    if(!getLocalFile().exists()) {
      throw new IOException("File does not exist");
    }
    final byte[] result;
    if(buffer == null) {
      result = new byte[size];
      offset = 0;
    }
    else {
      result = buffer;
    }
    RandomAccessFile raf = new RandomAccessFile(getLocalFile()
    raf.getChannel().read(ByteBuffer.wrap(result
    raf.close();
    return result;
  }

