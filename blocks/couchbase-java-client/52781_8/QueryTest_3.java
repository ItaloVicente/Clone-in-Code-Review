        String preparedName = "testPreparedNamed";

        Statement statement = select(x("*")).from(i(bucketName())).where(x("item").eq(x("$1")));
        PrepareStatement prepareStatement = PrepareStatement.prepare(statement, preparedName);
        PreparedPayload payload = bucket().prepare(prepareStatement);
        assertNotNull(bucket().get("test2"));
