                if (response.status().isSuccess()) {
                    int resultSize = response.responses().size();
                    List<SubdocOperationResult<Mutation>> results = new ArrayList<SubdocOperationResult<Mutation>>(resultSize);
                    for (MultiResult<Mutation> result : response.responses()) {
                        try {
                            Object content = null;
                            if (result.value().isReadable()) {
                                content = subdocumentTranscoder.decode(result.value(), Object.class);
