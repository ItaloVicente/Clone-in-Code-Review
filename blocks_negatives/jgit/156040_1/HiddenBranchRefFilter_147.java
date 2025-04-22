    @Override
    public Map<String, Ref> filter(final Map<String, Ref> refs) {
        return refs.entrySet()
                .stream()
                .filter(ref -> !HiddenBranchRefFilter.isHidden(ref.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey,
                                          Map.Entry::getValue));
    }
