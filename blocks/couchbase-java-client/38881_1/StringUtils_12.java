
package com.couchbase.client.java.convert.util;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public abstract class BaseSerializer {

  protected static final int DEFAULT_COMPRESSION_THRESHOLD = 16384;

  protected byte[] encodeObject(final Object content) {
    if (content == null) {
      throw new NullPointerException("Can't serialize null");
    }

    ByteArrayOutputStream bos = null;
    ObjectOutputStream os = null;

    try {
      bos = new ByteArrayOutputStream();
      os = new ObjectOutputStream(bos);
      os.writeObject(content);
      os.close();
      bos.close();

      return bos.toByteArray();
    } catch (IOException e) {
      throw new IllegalArgumentException("Non-serializable object", e);
    } finally {
      close(os);
      close(bos);
    }
  }

  protected Object decodeObject(final byte[] data) {
    if (data == null) {
      return null;
    }

    ByteArrayInputStream bis = null;
    ObjectInputStream is = null;

    try {
      bis = new ByteArrayInputStream(data);
      is = new ObjectInputStream(bis);

      return is.readObject();
    } catch (IOException e) {
      throw new IllegalArgumentException("IOException on decoding object with " + data.length + " bytes", e);
    } catch (ClassNotFoundException e) {
      throw new IllegalArgumentException("ClassNotFoundException on on decoding object with " + data.length + " bytes", e);
    } finally {
      close(is);
      close(bis);
    }
  }

  protected byte[] compress(byte[] uncompressedData) {
    if (uncompressedData == null) {
      throw new NullPointerException("Can't compress null");
    }

    ByteArrayOutputStream outputStream = null;
    GZIPOutputStream gzipOutputStream = null;

    try {
      outputStream = new ByteArrayOutputStream();
      gzipOutputStream = new GZIPOutputStream(outputStream);
      gzipOutputStream.write(uncompressedData);

      gzipOutputStream.flush();
      gzipOutputStream.close();
      outputStream.close();

      return outputStream.toByteArray();
    } catch (IOException e) {
      throw new RuntimeException("IO exception compressing data", e);
    } finally {
      close(gzipOutputStream);
      close(outputStream);
    }
  }

  protected byte[] decompress(byte[] compressedData) {
    if (compressedData == null) {
      return null;
    }

    byte[] uncompressed = null;

    ByteArrayInputStream inputStream = null;
    GZIPInputStream gzipInputStream = null;
    ByteArrayOutputStream outputStream = null;

    try {
      inputStream = new ByteArrayInputStream(compressedData);
      gzipInputStream = new GZIPInputStream(inputStream);
      outputStream = new ByteArrayOutputStream();

      byte[] buf = new byte[8192];
      int r;
      while ((r = gzipInputStream.read(buf)) > 0) {
        outputStream.write(buf, 0, r);
      }

      uncompressed = outputStream.toByteArray();
    } catch (IOException ignored) {
    } finally {
      close(outputStream);
      close(gzipInputStream);
      close(inputStream);
    }

    return uncompressed;
  }

  private void close(final Closeable closeable) {
    if (closeable != null) {
      try {
        closeable.close();
      } catch (Exception ignored) {
      }
    }
  }
}
