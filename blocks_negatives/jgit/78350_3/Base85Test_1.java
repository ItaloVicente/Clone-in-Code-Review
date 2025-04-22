    @Test
    public void failOnBadLength() {
        try {
            byte[] bytes = "hi".getBytes();
            decode85(bytes, 0, bytes.length, 2);
            fail("Accepted string with length not multiple of 5");
        } catch (IllegalArgumentException fail) {
        }
    }
