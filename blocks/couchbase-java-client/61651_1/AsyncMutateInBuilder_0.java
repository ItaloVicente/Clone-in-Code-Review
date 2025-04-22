                    return new DocumentFragment<Mutation>(docId, response.cas(), response.mutationToken(), Collections.singletonList(singleResult));
                } catch (SubDocumentException e) {
                    if (SubdocHelper.isSubdocLevelError(responseStatus)) {
                        throw new MultiMutationException(0, responseStatus, Collections.singletonList(spec), e);
                    } else {
                        throw e;
                    }
                }
