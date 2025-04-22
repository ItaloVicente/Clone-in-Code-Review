
            int i = 0;
            for (String path: pathSplit) {
                pathSplit[i] = path.replaceAll("~1", "/").replaceAll("~0", "~");
                i++;
            }

