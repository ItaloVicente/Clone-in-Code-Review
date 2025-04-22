
    Map<String, Object> docMap = multigetRef.get().get();
    final ViewResponseWithDocs view = (ViewResponseWithDocs) objRef.get();
    Collection<ViewRow> rows = new LinkedList<ViewRow>();
    Iterator<ViewRow> itr = view.iterator();

    while (itr.hasNext()) {
      ViewRow r = itr.next();
      rows.add(new ViewRowWithDocs(r.getId(), r.getKey(), r.getValue(),
          docMap.get(r.getId())));
    }
    return new ViewResponseWithDocs(rows, view.getErrors());
  }

  public void set(ViewResponse viewResponse,
      BulkFuture<Map<String, Object>> oper, OperationStatus s) {
    objRef.set(viewResponse);
    multigetRef.set(oper);
    status = s;
