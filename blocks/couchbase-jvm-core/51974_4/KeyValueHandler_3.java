    private static MutationDescriptor extractDescriptor(boolean seqOnMutation, boolean success, ByteBuf extras) {
        if (success && seqOnMutation) {
            return new MutationDescriptor(extras.readLong(), extras.readLong());
        }
        return null;
    }

