            Map<String, String> fields;
            JsonObject fieldsJson = hit.getObject("fields");
            if (fieldsJson != null) {
                fields = new HashMap<String, String>(fieldsJson.size());
                for (String f : fieldsJson.getNames()) {
                    fields.put(f, String.valueOf(fieldsJson.get(f)));
