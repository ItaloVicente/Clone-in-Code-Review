
            int i = 0;
            for (String path: pathSplit) {
                pathSplit[i] = path.replace("~1", "/").replace("~0", "~");
                i++;
            }

