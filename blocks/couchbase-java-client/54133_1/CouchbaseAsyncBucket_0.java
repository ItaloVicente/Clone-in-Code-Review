                    if (response.mutationToken() == null) {
                        return (D) transcoder.newDocument(document.id(), document.expiry(),
                            document.content(), response.cas());
                    } else {
                        return (D) transcoder.newDocument(document.id(), document.expiry(),
                            document.content(), response.cas(), response.mutationToken());
                    }
