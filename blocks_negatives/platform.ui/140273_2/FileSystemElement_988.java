        StringBuilder buf = new StringBuilder();
        if (isDirectory()) {
        } else {
        }
        if (!isDirectory()) {
            return buf.toString();
        }
        buf.append(folders);
        buf.append(files);
        return buf.toString();
    }
