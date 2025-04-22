    private ReftableStack stack;

    public ReftableStack stack() throws IOException {
        getLock().lock();
        try {
            if (stack == null) {
                stack = stackSupplier.get();
            }

            return stack;
        } finally {
            getLock().unlock();
        }
    }

    public ReflogReader getReflogReader(String refname) throws IOException {
        return new ReftableReflogReader(lock
    }

    public static ReceiveCommand toCommand(Ref oldRef
        ObjectId oldId = toId(oldRef);
        ObjectId newId = toId(newRef);
        String name = oldRef != null ? oldRef.getName() : newRef.getName();

        if (oldRef != null && oldRef.isSymbolic()) {
            if (newRef != null) {
                if (newRef.isSymbolic()) {
                    return ReceiveCommand.link(oldRef.getTarget().getName()
                            newRef.getTarget().getName()
                } else {
                    return ReceiveCommand.unlink(oldRef.getTarget().getName()
                            newId
                }
            } else {
                return ReceiveCommand.unlink(oldRef.getTarget().getName()
                        ObjectId.zeroId()
            }
        }

        if (newRef != null && newRef.isSymbolic()) {
            if (oldRef != null) {
                if (oldRef.isSymbolic()) {
                    return ReceiveCommand.link(oldRef.getTarget().getName()
                            newRef.getTarget().getName()
                } else {
                    return ReceiveCommand.link(oldId
                            newRef.getTarget().getName()
                }
            } else {
                return ReceiveCommand.link(ObjectId.zeroId()
                        newRef.getTarget().getName()
            }
        }

        return new ReceiveCommand(oldId
    }

    private static ObjectId toId(Ref ref) {
        if (ref != null) {
            ObjectId id = ref.getObjectId();
            if (id != null) {
                return id;
            }
        }
        return ObjectId.zeroId();
    }
