      ioReactor = new DefaultConnectingIOReactor(IOReactorConfig.custom()
        .setConnectTimeout(connectionTimeout)
        .setSoTimeout(socketTimeout)
        .setTcpNoDelay(tcpNoDelay)
        .setIoThreadCount(ioThreadCount)
        .build());
    } catch (IOReactorException ex) {
      throw new IllegalStateException("Could not create IO reactor");
