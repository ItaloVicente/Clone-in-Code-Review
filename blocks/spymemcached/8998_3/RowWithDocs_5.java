    this.id = parseField(id);
    this.key = parseField(key);
    this.value = parseField(value);
    this.doc = parseField((String)doc);
  }

  private String parseField(String field) {
    if (field != null && field.equals("null")) {
      return null;
