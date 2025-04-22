                                    return new DocumentFragment<Mutation>(docId, response.cas(), response.mutationToken(), Collections.singletonList(singleResult));
                                } catch (NumberFormatException e) {
                                    throw new TranscodingException("Couldn't parse counter response into a long", e);
                                } finally {
                                    if (response.content() != null) {
                                        response.content().release();
                                    }
                                }
                            } else {
