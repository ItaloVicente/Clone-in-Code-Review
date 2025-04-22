			if (output != null)
				stream = new FileOutputStream(output);
			else
				stream = outs;

			try {
				ArchiveCommand cmd = new Git(db).archive()
					.setTree(tree)
					.setFormat(format)
					.setOutputStream(stream);
				if (output != null)
					cmd.setFilename(output);
				cmd.call();
