    } else if (key.equals(DESCENDING)
      || key.equals(GROUP)
      || key.equals(INCLUSIVEEND)
      || key.equals(REDUCE)
      || key.equals(DEBUG)
      || key.equals(GROUPLEVEL)
      || key.equals(LIMIT)
      || key.equals(SKIP)
      || value instanceof Stale
      || value instanceof OnError
      || StringUtils.isJsonObject(value.toString())
      || value.toString().startsWith("\"")) {
