        return new OutputStream() {

          @Override
          public void write(int b)
                  throws IOException {
            fileOutputStream.write(b);
          }

          @Override
          public void close()
                  throws IOException {
            fileLock.release();
            fileOutputStream.close();
          }

          @Override
          public void flush()
                  throws IOException {
            fileOutputStream.flush();
          }

          @Override
          public void write(byte[] b)
                  throws IOException {
            fileOutputStream.write(b);
          }

          @Override
          public void write(byte[] b
                            int off
                            int len)
                  throws IOException {
            fileOutputStream.write(b
          }
        };
