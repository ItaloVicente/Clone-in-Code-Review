  @Test
  public void shouldWorkWithEmptyOrNullStrings() {
    assertFalse(StringUtils.isJsonObject(""));
    assertFalse(StringUtils.isJsonObject(null));
    assertFalse(StringUtils.isJsonObject("\r\n"));
    assertFalse(StringUtils.isJsonObject("\n"));
    assertFalse(StringUtils.isJsonObject(" "));
  }

