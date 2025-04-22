        mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule("JsonValueModule",
            new Version(1, 0, 0, null, null, null));
        module.addSerializer(JsonObject.class, new JsonObjectSerializer());
        module.addSerializer(JsonArray.class, new JsonArraySerializer());
        module.addDeserializer(JsonObject.class, new JsonObjectDeserializer());
        module.addDeserializer(JsonArray.class, new JsonArrayDeserializer());
        mapper.registerModule(module);
