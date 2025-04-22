            for (Object o : sort) {
                if (o instanceof String) {
                    this.sort.add((String) o);
                } else if (o instanceof SearchSort) {
                    JsonObject params = JsonObject.create();
                    ((SearchSort) o).injectParams(params);
                    this.sort.add(params);
                } else if (o instanceof JsonObject) {
                    this.sort.add(o);
                } else {
                    throw new IllegalArgumentException("Only String ort SearchSort " +
                        "instances are allowed as sort arguments!");
                }
            }
