                        Map<ObjectId
                                        .filter(ref -> {
                                                ObjectId id = ref.getObjectId();
                                                try {
                                                        return Boolean.TRUE.equals(allTags) || (id != null && (w.parseAny(id) instanceof RevTag));
                                                } catch (Exception e) {
                                                        return false;
                                                }
                                        })
