	private static abstract class Format {
		abstract String name();
		abstract String[] listEntries(byte[] file) throws Exception;
		abstract String[] entryContent(byte[] file

		String cmd() {
			return "git archive --format=" + name() + " ";
		}
	}
	private final List<Format> formats = Arrays.asList(
		new Format() {
			@Override
			String name() {
				return "tar";
			}

			@Override
			String[] listEntries(byte[] data) throws Exception {
				return listTarEntries(data);
			}

			@Override
			String[] entryContent(byte[] data
				return tarEntryContent(data
			}
		}
			@Override
			String name() {
				return "zip";
			}

			@Override
			String[] listEntries(byte[] data) throws Exception {
				return listZipEntries(data);
			}

			@Override
			String[] entryContent(byte[] data
				return zipEntryContent(data
			}
		});

