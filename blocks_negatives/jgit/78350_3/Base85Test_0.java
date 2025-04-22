    @Test
    public void testFailOnIllegalChars() {
        for (char c : ILLEGAL_CHARS) {
            try {
                String s = c + "2345";
                byte[] bytes = s.getBytes();
                decode85(bytes, 0, bytes.length, 5);
                fail("Accepted bad string in decode");
            } catch (IllegalArgumentException fail) {
            }
        }
    }
