    protected PreparedPayload extractPreparedPayloadFromResponse(PrepareStatement prepared, JsonObject response) {
        return new PreparedPayload(
                prepared.originalStatement(),
                response.getString("name"),
                response.getString("encoded_plan")
        );
    }

