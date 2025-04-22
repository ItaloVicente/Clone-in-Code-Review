    @Test
    public void shouldReuseNameWhenRepreparingPayload() {
        PreparedPayload fakePayload = new PreparedPayload(select("*"), "testName");
        PrepareStatement preparedStatement = PrepareStatement.prepare(fakePayload);

        assertEquals(fakePayload.originalStatement().toString(), preparedStatement.originalStatement().toString());
        assertEquals(fakePayload.preparedName(), preparedStatement.preparedName());
