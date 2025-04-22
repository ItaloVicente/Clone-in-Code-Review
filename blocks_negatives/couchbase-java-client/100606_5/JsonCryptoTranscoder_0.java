            if (content == null || content.encryptionPathInfo().size() == 0) {
                return;
            }

            for (Map.Entry<String, String> entry : content.encryptionPathInfo().entrySet()) {
                String providerName = entry.getValue();
                String[] pathSplit = entry.getKey().split("/");

                int i = 0;
                for (String path : pathSplit) {
                    pathSplit[i] = path.replace("~1", "/").replace("~0", "~");
                    i++;
