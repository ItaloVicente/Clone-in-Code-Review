                    response = handleGenericQueryResponse(lastChunk);
                    if (response != null) {
                        parseQueryResponse(lastChunk);
                    }
                } else {
                    parseQueryResponse(lastChunk);
