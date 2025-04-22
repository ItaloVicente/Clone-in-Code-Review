
    synchronized (clones) {
      Iterator<Operation> i = clones.iterator();
      while(i.hasNext()) {
        i.next().timeOut();
      }
    }

