    protected void assertEqualsArray(String s, Object[] a1, Object[] a2) {
        int s1 = a1.length;
        int s2 = a2.length;
        assertEquals(s, s1, s2);
        for (int i = 0; i < s1; i++) {
            assertEquals(s, a1[i], a2[i]);
        }
    }
