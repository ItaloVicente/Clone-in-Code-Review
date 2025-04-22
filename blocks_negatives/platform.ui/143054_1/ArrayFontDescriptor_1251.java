        if (originalFont != null) {
            return originalFont.hashCode();
        }

        int code = 0;

        for (FontData fd : data) {
            code += fd.hashCode();
        }
        return code;
    }
