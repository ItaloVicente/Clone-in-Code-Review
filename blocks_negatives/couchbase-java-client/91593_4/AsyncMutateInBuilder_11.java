                            results.add(SubdocOperationResult
                                    .createResult(result.path(), result.operation(), result.status(), content));
                        } catch (TranscodingException e) {
                            LOGGER.error(
                              "Couldn't decode multi-mutation {} for {}/{}",
                              user(result),
                              user(docId),
                              user(result.path()),
                              e
                            );
                            results.add(SubdocOperationResult.createFatal(result.path(), result.operation(), e));
                        } finally {
                            if (result.value() != null) {
                                result.value().release();
