    public boolean isSubtypeOf(MarkerType superType) {
        if (id.equals(superType.getId())) {
            return true;
        }
        for (String supertypeId : supertypeIds) {
            MarkerType sup = model.getType(supertypeId);
            if (sup != null && sup.isSubtypeOf(superType)) {
                return true;
            }
        }
        return false;
    }

    @Override
