                    if (response.mutationToken() == null) {
                        return JsonLongDocument.create(id, returnedExpiry, response.value(),
                            response.cas());
                    } else {
                        return JsonLongDocument.create(id, returnedExpiry, response.value(),
                            response.cas(), response.mutationToken());
                    }

