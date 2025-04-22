			if (!performAction) {
				return;
			}
			List<String> files = new ArrayList<>();
			List<String> inaccessibleFiles = new ArrayList<>();
			getSelectedFiles(files, inaccessibleFiles);
			replaceWith(files, inaccessibleFiles);
