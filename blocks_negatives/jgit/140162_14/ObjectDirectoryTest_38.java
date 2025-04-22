			File[] ret = packsFolder.listFiles(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					return name.endsWith(".pack");
				}
			});
