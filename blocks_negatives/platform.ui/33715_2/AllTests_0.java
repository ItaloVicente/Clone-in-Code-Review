    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }
    
    public static Test suite() {
        return new AllTests();
    }
