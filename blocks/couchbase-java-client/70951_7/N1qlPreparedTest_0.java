        final JsonObject jsonObject = JsonObject.create();
        for (int i=0; i < 10; i++) {
            jsonObject.put("field" + i,  UUID.randomUUID().toString());
        }
        List<String> keys = new ArrayList<String>();
        for (int i=0; i < 15000; i++) {
            keys.add("Key" + i);
        }
