    if(numDocs > 0) {
      this.docsPerPage = numDocs;
    } else {
      throw new IllegalArgumentException("Number of documents per page "
        + "must be greater than zero.");
    }
