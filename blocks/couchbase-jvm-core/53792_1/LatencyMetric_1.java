
    public Map<String, Object> export() {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("min", min());
        result.put("max", max());
        result.put("count", count());
        result.put("percentiles", percentiles());
        return result;
    }
