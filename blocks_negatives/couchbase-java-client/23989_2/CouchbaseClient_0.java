      private ViewResponse vr = null;

      @Override
      public void receivedStatus(OperationStatus status) {
        if (vr != null) {
          Collection<String> ids = new LinkedList<String>();
          Iterator<ViewRow> itr = vr.iterator();
          while (itr.hasNext()) {
            ids.add(itr.next().getId());
