		attrFiles.add(writeAttributesFile(".git/info/attributes",
				"*.txt eol=crlf"));
		attrFiles
				.add(writeAttributesFile(".gitattributes", "*.txt custom=root"));
		attrFiles
				.add(writeAttributesFile("level1/.gitattributes", "*.txt text"));
