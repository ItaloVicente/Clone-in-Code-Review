    public MarkerType[] getSupertypes() {
        ArrayList<MarkerType> result = new ArrayList<>();
        for (String supertypeId : supertypeIds) {
            MarkerType sup = model.getType(supertypeId);
            if (sup != null) {
                result.add(sup);
            }
        }
        return result.toArray(new MarkerType[result.size()]);
    }

    /**
