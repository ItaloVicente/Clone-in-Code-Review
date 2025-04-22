
    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> result = Events.identityMap(this);
        result.put("host", host().toString());
        return result;
    }
