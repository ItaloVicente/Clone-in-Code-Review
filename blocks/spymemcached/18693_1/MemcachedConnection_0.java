      } catch (Throwable e) {
    	logRunException(e);
        for (MemcachedNode qa : locator.getAll()) {
          qa.setupResend();
          try {
            qa.getChannel().close();
            qa.setChannel(SocketChannel.open(qa.getSocketAddress()));
          } catch(IOException ex) {
            logRunException(ex);
          }
        }
