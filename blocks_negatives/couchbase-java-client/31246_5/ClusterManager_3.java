      if (!conn.isOpen()) {
        Socket socket = new Socket(host.getHostName(), host.getPort());
        conn.bind(socket, new SyncBasicHttpParams());
      }
      return true;
    } catch (IOException e) {
      return false;
