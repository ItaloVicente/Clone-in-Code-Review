    @Test
    public void shouldSucceedWithLargeKeysArray() throws IOException {
        InputStream ras = this.getClass().getResourceAsStream("/data/view/key_many.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(ras));
        String[] keys = reader.readLine().split(",");
        reader.close();
        JsonArray keysArray = JsonArray.from((Object[]) keys);

        ViewResult result = bucket().query(
                ViewQuery.from("users", "by_name")
                         .keys(keysArray)
        );

        assertTrue(result.success());
        assertNull(result.error());
    }

