    private static void trimPort(List<String> input) {
        for (int i = 0; i < input.size(); i++) {
            String[] parts =  input.get(i).split(":");
            input.set(i, parts[0]);
        }
    }

