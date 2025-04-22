    docsOnPage = page.size();
    totalDocs += docsOnPage;
    if (docsOnPage >= docsPerPage) {
      q.setSkip(docsOnPage);
      q.setLimit(1);
      finalRow = client.query(view, q);
    } else {
      done = true;
    }
    return page;
