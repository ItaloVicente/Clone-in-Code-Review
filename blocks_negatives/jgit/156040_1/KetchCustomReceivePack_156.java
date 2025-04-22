    @Override
    public void setAdvertisedRefs(final Map<String, Ref> allRefs,
                                  final Set<ObjectId> additionalHaves) {
        super.setAdvertisedRefs(allRefs,
                                additionalHaves);
        final Map<String, Ref> refs = getAdvertisedRefs();
        if (getRepository().getRefDatabase() instanceof RefTreeDatabase) {
            final RefDatabase bootstrap = ((RefTreeDatabase) getRepository().getRefDatabase()).getBootstrap();
            try {
                for (Map.Entry<String, Ref> entry : bootstrap.getRefs("").entrySet()) {
                    refs.put(entry.getKey(),
                             entry.getValue());
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
    }
