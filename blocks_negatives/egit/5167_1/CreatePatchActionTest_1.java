		StringBuilder sb = new StringBuilder();
		sb.append(
				"diff --git a/GeneralProject/folder/test.txt b/GeneralProject/folder/test.txt")
				.append("\n");
		sb.append("index e256dbb..d070357 100644").append("\n");
		sb.append("--- a/GeneralProject/folder/test.txt").append("\n");
		sb.append("+++ b/GeneralProject/folder/test.txt").append("\n");
		sb.append("@@ -1 +1 @@").append("\n");
		sb.append("-oldContent").append("\n");
		sb.append("\\ No newline at end of file").append("\n");
		sb.append("+newContent").append("\n");
		sb.append("\\ No newline at end of file");

		assertClipboard(sb.toString());
