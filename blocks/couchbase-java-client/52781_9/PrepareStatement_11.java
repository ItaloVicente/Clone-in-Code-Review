        } else if (statement instanceof PreparedPayload) {
            PreparedPayload preparedPayload = (PreparedPayload) statement;
            return new PrepareStatement(preparedPayload.originalStatement(), preparedPayload.preparedName());
        } else {
            String preparedName = DigestUtils.digestSha1Hex(statement.toString());
            return new PrepareStatement(statement, preparedName);
        }
    }

    public static PrepareStatement prepare(Statement statement, String preparedName) {
        Statement original;
        if (statement instanceof PrepareStatement) {
            original = ((PrepareStatement) statement).originalStatement();
        } else if (statement instanceof PreparedPayload) {
            original = ((PreparedPayload) statement).originalStatement();
