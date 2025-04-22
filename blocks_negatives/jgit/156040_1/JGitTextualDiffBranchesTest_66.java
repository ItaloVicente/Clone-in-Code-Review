    @Test
    public void testDiffWithRemovedFile() {
        new Commit(git, DEVELOP_BRANCH, "name", "name@example.com", "Removing file",
                   null, null, false,
                   new HashMap<String, File>() {{
                       put(TXT_FILES.get(0), null);
                   }}).execute();
