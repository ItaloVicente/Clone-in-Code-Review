
    @Test
    public void shouldSelectByteArrayEagerlyAndJsonObjectLazily() throws NoSuchFieldException, IllegalAccessException {
        N1qlQuery query = N1qlQuery.simple(select("*").from(i(ctx.bucketName())).limit(3), WITH_CONSISTENCY);

        N1qlQueryResult result = ctx.bucket().query(query);
        assertTrue(result.allRows().size() > 0);
        assertTrue(result.errors().isEmpty());
        assertTrue(result.finalSuccess());

        for (N1qlQueryRow row : result) {
            assertNotNull(row.byteValue());

            Field asyncRowField = row.getClass().getDeclaredField("asyncRow");
            asyncRowField.setAccessible(true);
            DefaultAsyncN1qlQueryRow asyncRow = (DefaultAsyncN1qlQueryRow) asyncRowField.get(row);
            assertNotNull(asyncRow);

            Field valueField = asyncRow.getClass().getDeclaredField("value");
            valueField.setAccessible(true);
            Object valueBeforeGetterCall = valueField.get(asyncRow);
            assertNull(valueBeforeGetterCall);

            assertNotNull(row.value());
            assertEquals(new String(row.byteValue(), CharsetUtil.UTF_8).replaceAll("\\s", "")
                    , row.value().toString().replaceAll("\\s", ""));
        }
    }
