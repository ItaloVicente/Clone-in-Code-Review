				@SuppressWarnings("unchecked")
				final HashMap<String, ChannelSftp.LsEntry> files;
				final HashMap<String, Integer> mtimes;

				files = new HashMap<>();
				mtimes = new HashMap<>();

				for (ChannelSftp.LsEntry ent : list)
					files.put(ent.getFilename(), ent);
				for (ChannelSftp.LsEntry ent : list) {
					final String n = ent.getFilename();
