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
                                } else {
                                    if (query.stopOnError()) {
                                        throw new ViewQueryException(jsonInfo.getString("error"), jsonInfo.getString("reason"));
                                    } else {
                                        error = jsonInfo;
