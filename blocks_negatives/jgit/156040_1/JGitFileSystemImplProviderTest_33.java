        new Commit(origin,
                   "master",
                   "user",
                   "user@example.com",
                   "commit message",
                   null,
                   null,
                   false,
                   new HashMap<String, File>() {{
                       put("path/to/file/myfile.txt",
                           tempFile("temp\n.origin\n.content"));
                   }}).execute();
