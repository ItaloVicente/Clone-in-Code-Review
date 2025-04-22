		}
        String name = null;
        int height = 0;
        int style = 0;
        try {
            int length = value.length();
            int heightIndex = value.lastIndexOf(SEPARATOR);
            if (heightIndex == -1) {
