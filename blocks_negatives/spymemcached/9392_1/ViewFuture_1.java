    if (multigetRef.get() == null) {
      return null;
    }

    Map<String, Object> docMap = multigetRef.get().get();
    final ViewResponseWithDocs view = (ViewResponseWithDocs) objRef.get();
    Collection<ViewRow> rows = new LinkedList<ViewRow>();
    Iterator<ViewRow> itr = view.iterator();

    while (itr.hasNext()) {
      ViewRow r = itr.next();
      rows.add(new ViewRowWithDocs(r.getId(), r.getKey(), r.getValue(),
          docMap.get(r.getId())));
