
        JsonObject content1 = JsonObject.create().put("foreignKey", "baz");
        JsonObject content2 = JsonObject.create().put("foo", "bar");
        ctx.bucket().upsert(JsonDocument.create("join", content1));
        ctx2.bucket().upsert(JsonDocument.create("baz", content2));
        ctx3.bucket().upsert(JsonDocument.create("baz", content2));
        Authenticator authenticator = new ClassicAuthenticator()
                .bucket(ctx.bucketName(), "protected")
                .bucket(ctx2.bucketName(), "protected");
        ctx.cluster().authenticate(authenticator);
