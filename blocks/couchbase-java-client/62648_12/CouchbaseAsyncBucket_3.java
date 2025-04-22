
                JsonObject jsonStatus = json.getObject("status");
                SearchStatus status = new DefaultSearchStatus(
                        jsonStatus.getLong("total"),
                        jsonStatus.getLong("failed"),
                        jsonStatus.getLong("successful"));

