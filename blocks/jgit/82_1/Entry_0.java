
  public boolean isRandomAccessSupported();

  public byte[] readRandomly(long position
                             int size
                             byte[] buffer
                             int offset)
          throws UnsupportedOperationException
                 IllegalArgumentException
                 IOException;
