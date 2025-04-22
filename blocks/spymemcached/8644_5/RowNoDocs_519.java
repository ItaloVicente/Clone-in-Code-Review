  public RowNoDocs(String id, String key, String value) {
    super(key, value);
    if (id != null && id.equals("null")) {
      this.id = null;
    } else {
      this.id = id;
    }
  }
