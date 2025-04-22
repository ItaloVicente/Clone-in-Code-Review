				Set<String> files = list.stream()
						.map(FtpChannel.DirEntry::getFilename)
						.collect(Collectors.toSet());
				HashMap<String

				for (FtpChannel.DirEntry ent : list) {
					String n = ent.getFilename();
