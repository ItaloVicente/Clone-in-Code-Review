    ioReactor = new DefaultConnectingIOReactor(IOReactorConfig.custom()
      .setConnectTimeout(5000)
      .setSoTimeout(5000)
      .setTcpNoDelay(true)
      .setIoThreadCount(1)
      .build());
    } catch (IOReactorException ex) {
      throw new IllegalStateException("Could not create IO reactor");
