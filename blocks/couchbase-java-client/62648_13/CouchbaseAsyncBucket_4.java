                        fields = new HashMap<String, String>(fieldsJson.size());
                        for (String f : fieldsJson.getNames()) {
                            fields.put(f, String.valueOf(fieldsJson.get(f)));
                        }
                    } else {
                        fields = Collections.emptyMap();
