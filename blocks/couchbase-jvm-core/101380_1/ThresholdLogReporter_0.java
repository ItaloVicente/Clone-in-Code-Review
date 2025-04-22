
            Collections.sort(top, new Comparator<Map<String, Object>>() {
                @Override
                public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                    return ((Long) o2.get(KEY_TOTAL_MICROS)).compareTo((Long) o1.get(KEY_TOTAL_MICROS));
                }
            });

