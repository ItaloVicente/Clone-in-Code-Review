                        try {
                            ResponseStatus status = response.status();
                            Object value;
                            if (status.isSuccess()) {
                                try {
                                    value = Long.parseLong(response.content().toString(CharsetUtil.UTF_8));
                                    SubdocOperationResult<Mutation> singleResult = SubdocOperationResult
