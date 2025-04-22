	private static Repository repo;

	private static final String TAG_NAME = "savepoint";

	private static final String PATCH_FILE = "test.patch";

	private static final String EXPECTED_PATCH_CONTENT = //
	"diff --git a/GeneralProject/folder/test.txt b/GeneralProject/folder/test.txt\n"
			+ "index e256dbb..d070357 100644\n"
			+ "--- a/GeneralProject/folder/test.txt\n"
			+ "+++ b/GeneralProject/folder/test.txt\n"
			+ "@@ -1 +1 @@\n"
			+ "-oldContent\n"
			+ "\\ No newline at end of file\n"
			+ "+newContent\n" //
			+ "\\ No newline at end of file";

