	private static final String EXPECTED_WORKSPACE_PATCH_CONTENT = //
	"### Eclipse Workspace Patch 1.0\n" //
			+ "#P "	+ PROJ1	+ "\n"
			+ "diff --git folder/test.txt folder/test.txt\n" //
			+ "index e256dbb..d070357 100644\n" //
			+ "--- folder/test.txt\n" //
			+ "+++ folder/test.txt\n" //
			+ "@@ -1 +1 @@\n" //
			+ "-oldContent\n" //
			+ "\\ No newline at end of file\n" //
			+ "+newContent\n" //
			+ "\\ No newline at end of file\n"
			+ "diff --git folder/test2.txt folder/test2.txt\n"
			+ "deleted file mode 100644\n" //
			+ "index 8f4e8d3..0000000\n" //
			+ "--- folder/test2.txt\n" //
			+ "+++ /dev/null\n" //
			+ "@@ -1 +0,0 @@\n" //
			+ "-Some more content\n" //
			+ "\\ No newline at end of file\n" //
			+ "#P " + PROJ2 + "\n" //
			+ "diff --git test.txt test.txt\n" //
			+ "new file mode 100644\n" //
			+ "index 0000000..dbe9dba\n" //
			+ "--- /dev/null\n" //
			+ "+++ test.txt\n" //
			+ "@@ -0,0 +1 @@\n" //
			+ "+Hello, world\n" //
			+ "\\ No newline at end of file";

