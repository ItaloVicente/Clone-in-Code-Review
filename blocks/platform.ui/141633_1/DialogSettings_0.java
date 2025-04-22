    public void load(String fileName) throws IOException {
    	FileInputStream stream = new FileInputStream(fileName);
    	try (BufferedReader reader = new BufferedReader(new InputStreamReader(
    			stream, StandardCharsets.UTF_8))) {
    		load(reader);
    	}
