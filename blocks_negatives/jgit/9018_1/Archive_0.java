			final ObjectLoader loader = reader.open(idBuf);
					name, mode, loader.getSize());

			out.putArchiveEntry(entry);
			loader.copyTo(out);
			out.closeArchiveEntry();
