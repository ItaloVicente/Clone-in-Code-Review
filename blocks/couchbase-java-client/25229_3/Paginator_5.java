
    nextResponse = client.query(view, query);

    if (nextResponse.size() == limit + 1) {
      ViewRow nextRow = nextResponse.removeLastElement();
      if (!query.willReduce()) {
        nextStartKeyDocID = nextRow.getId();
        nextStartKey = nextRow.getKey();
      }
