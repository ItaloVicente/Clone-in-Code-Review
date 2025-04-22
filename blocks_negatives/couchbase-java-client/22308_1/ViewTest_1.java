      @Override
      public void gotData(ViewResponse response) {
        assert response.getErrors().size() == 2;
        Iterator<RowError> row = response.getErrors().iterator();
        assert row.next().getFrom().equals("127.0.0.1:5984");
        assert response.size() == 0;
      }
    });
