            @Override
            public ViewResult call(JsonObject object) {
                return new ViewResult(object.getString("id"), object.getString("key"), object.get("value"));
            }
        }
    );
