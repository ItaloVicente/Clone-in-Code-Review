  private ViewResponse getNextPage(Query q) {
    int remaining = totalLimit > 0 ? totalLimit - totalDocs : docsPerPage;
    q.setLimit(remaining);
    page = client.query(view, q);
    docsOnPage = page.size();
    totalDocs += docsOnPage;
    if (docsOnPage >= docsPerPage) {
      q.setSkip(docsOnPage);
      q.setLimit(1);
      finalRow = client.query(view, q);
      if(!finalRow.iterator().hasNext()) {
        done = true;
      }
    } else {
      done = true;
    }
    return page;
