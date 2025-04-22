  public static final int DEFAULT_COMPRESSION_THRESHOLD = 16384;

  private static final String DEFAULT_CHARSET = "UTF-8";

  protected int compressionThreshold = DEFAULT_COMPRESSION_THRESHOLD;
  protected String charset = DEFAULT_CHARSET;

  private final int maxSize;

  public BaseSerializingTranscoder(int max) {
    super();
    maxSize = max;
  }

  public boolean asyncDecode(CachedData d) {
    return false;
  }

  public void setCompressionThreshold(int to) {
    compressionThreshold = to;
  }

  public void setCharset(String to) {
    try {
      new String(new byte[97], to);
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
    charset = to;
  }

  protected byte[] serialize(Object o) {
    if (o == null) {
      throw new NullPointerException("Can't serialize null");
    }
    byte[] rv = null;
    try {
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      ObjectOutputStream os = new ObjectOutputStream(bos);
      os.writeObject(o);
      os.close();
      bos.close();
      rv = bos.toByteArray();
    } catch (IOException e) {
      throw new IllegalArgumentException("Non-serializable object", e);
    }
    return rv;
  }

  protected Object deserialize(byte[] in) {
    Object rv = null;
    try {
      if (in != null) {
        ByteArrayInputStream bis = new ByteArrayInputStream(in);
        ObjectInputStream is = new ObjectInputStream(bis);
        rv = is.readObject();
        is.close();
        bis.close();
      }
    } catch (IOException e) {
      getLogger().warn("Caught IOException decoding %d bytes of data",
          in == null ? 0 : in.length, e);
    } catch (ClassNotFoundException e) {
      getLogger().warn("Caught CNFE decoding %d bytes of data",
          in == null ? 0 : in.length, e);
    }
    return rv;
  }

  protected byte[] compress(byte[] in) {
    if (in == null) {
      throw new NullPointerException("Can't compress null");
    }
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    GZIPOutputStream gz = null;
    try {
      gz = new GZIPOutputStream(bos);
      gz.write(in);
    } catch (IOException e) {
      throw new RuntimeException("IO exception compressing data", e);
    } finally {
      CloseUtil.close(gz);
      CloseUtil.close(bos);
    }
    byte[] rv = bos.toByteArray();
    getLogger().debug("Compressed %d bytes to %d", in.length, rv.length);
    return rv;
  }

  protected byte[] decompress(byte[] in) {
    ByteArrayOutputStream bos = null;
    if (in != null) {
      ByteArrayInputStream bis = new ByteArrayInputStream(in);
      bos = new ByteArrayOutputStream();
      GZIPInputStream gis;
      try {
        gis = new GZIPInputStream(bis);

        byte[] buf = new byte[8192];
        int r = -1;
        while ((r = gis.read(buf)) > 0) {
          bos.write(buf, 0, r);
        }
      } catch (IOException e) {
        getLogger().warn("Failed to decompress data", e);
        bos = null;
      }
    }
    return bos == null ? null : bos.toByteArray();
  }

  protected String decodeString(byte[] data) {
    String rv = null;
    try {
      if (data != null) {
        rv = new String(data, charset);
      }
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
    return rv;
  }

  protected byte[] encodeString(String in) {
    byte[] rv = null;
    try {
      rv = in.getBytes(charset);
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
    return rv;
  }

  public int getMaxSize() {
    return maxSize;
  }
