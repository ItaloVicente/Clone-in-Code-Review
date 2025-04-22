				.append("<project path=\"shallow-please\" ")
					.append("name=\"").append(defaultUri).append("\" ")
					.append("clone-depth=\"1\" />")
				.append("<project path=\"non-shallow\" ")
					.append("name=\"").append(defaultUri).append("\" />")
				.append("</manifest>");
			JGitTestUtil.writeTrashFile(tempDb, "manifest.xml",
