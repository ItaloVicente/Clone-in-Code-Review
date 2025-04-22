        if (!ignoreNonCbRx) {
            return all;
        } else {
            Set<String> result = new HashSet<String>(all.size());
            for (String s : all) {
                if (s.startsWith("cb-") || s.contains("Rx")) {
                    result.add(s);
                }
            }
            return result;
        }
