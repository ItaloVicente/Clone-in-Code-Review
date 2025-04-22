                        }).map(new Func1<JsonObject, AsyncViewResult>() {
                            @Override
                            public AsyncViewResult call(JsonObject jsonInfo) {
                                JsonObject error = null;
                                JsonObject debug = null;
                                int totalRows = 0;
                                boolean success = response.status().isSuccess();
                                if (success) {
                                    debug = jsonInfo.getObject("debug_info");
                                    Integer trows = jsonInfo.getInt("total_rows");
                                    if (trows != null) {
                                        totalRows = trows;
                                    }
                                    JsonArray errors = jsonInfo.getArray("errors");
                                    if (error != null) {
                                        if (query.stopOnError()) {
                                            ViewQueryException ex = new ViewQueryException();
                                            for (Object err : errors) {
                                                JsonObject obj = (JsonObject)err;
                                                ex.addError(obj.getString("error"), obj.getString("from"));
                                            }
                                            throw ex;
                                        } else {
                                            error = JsonObject.create().put("errors", errors);
                                        }
