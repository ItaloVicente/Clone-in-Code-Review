		else if (!result.containsConflicts()) {
			of = File.createTempFile("merge_"
			fos = new FileOutputStream(of);
			try {
				fmt.formatMerge(fos
						Constants.CHARACTER_ENCODING);
			} finally {
				fos.close();
			}
		}

