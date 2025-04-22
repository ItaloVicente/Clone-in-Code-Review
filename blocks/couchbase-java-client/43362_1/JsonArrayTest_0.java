
    @Test(expected = IndexOutOfBoundsException.class)
    public void shouldNotNullPointerOnGetNumber() {
        JsonArray obj = JsonArray.empty();
        obj.get(0);
    }

