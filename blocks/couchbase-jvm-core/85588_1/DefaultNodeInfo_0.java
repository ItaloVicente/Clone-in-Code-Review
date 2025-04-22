        configPort = Integer.parseInt(parts[parts.length - 1]);

        if (parts.length > 2) {
            System.out.println(Arrays.asList(parts));
            String assembledHost = "";
            for (int i = 0; i < parts.length - 1; i++) {
                assembledHost += parts[i];
                if (parts[i].endsWith("]")) {
                    break;
                } else {
                    assembledHost += ":";
                }
            }
            return assembledHost;
        } else {
            return parts[0];
        }
