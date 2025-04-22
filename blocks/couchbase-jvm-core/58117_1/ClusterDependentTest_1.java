            String stringVersion = (String) node.get("version");
            int[] version = extractVersion(stringVersion);

            if (version[0] < min[0]
                    || (version[0] == min[0] && version[1] < min[1])) {
                min = version;
