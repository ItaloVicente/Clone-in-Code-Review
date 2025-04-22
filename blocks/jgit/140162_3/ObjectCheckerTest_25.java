		return (AnyObjectId objectId) -> {
                    for (ObjectId id : ids) {
                        if (id.equals(objectId)) {
                            return true;
                        }
                    }
                    return false;
                };
