  private final Query query;
  private final int limit;

  private volatile State currentState;

  private ViewResponse nextResponse = null;

  private int currentPage;

  private String nextStartKeyDocID = null;

  private String nextStartKey = null;
