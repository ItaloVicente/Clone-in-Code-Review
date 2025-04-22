  private String convertKey(String original) {
    if(forcedKeyType == null) {
      return original;
    }

    if(forcedKeyType.getSimpleName().equals("Integer")) {
      return ComplexKey.of(Integer.parseInt(original)).toJson();
