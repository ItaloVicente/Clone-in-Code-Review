    if (!pageItr.hasNext() && page.size() < docsPerPage) {
      return false;
    } else if (!(rowsIterated < docsPerPage)) {
      lastRow = pageItr.next();
      query.setStartkeyDocID(lastRow.getId());
      query.setRangeStart(lastRow.getKey());
      getNextPage(query);
