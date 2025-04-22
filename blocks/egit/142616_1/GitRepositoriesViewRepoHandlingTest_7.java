				try {
					clp.clearContents();
					clp.setContents(new Object[] { "x" },
							new TextTransfer[] { TextTransfer.getInstance() });
					String value = (String) clp
							.getContents(TextTransfer.getInstance());
					assertEquals("Clipboard content should be x", "x", value);

					ContextMenuHelper.clickContextMenuSync(tree,
							myUtil.getPluginLocalizedValue("CopyPathCommand"));
					value = (String) clp
							.getContents(TextTransfer.getInstance());
					assertTrue(
							"Clipboard content (" + value
									+ ")should be a repository path",
							FileKey.isGitRepository(new File(value),
									FS.DETECTED));
				} finally {
					clp.dispose();
				}
