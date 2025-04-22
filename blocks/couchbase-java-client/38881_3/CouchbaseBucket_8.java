               @Override
               public ViewResult call(final JsonObject object) {
                 return new ViewResult(object.getString("id"), object.getString("key"), object.get("value"));
               }
             }
        );
