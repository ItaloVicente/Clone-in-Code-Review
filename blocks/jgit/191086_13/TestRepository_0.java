
				PackFile oidx = pack.create(PackExt.OBJECT_SIZE_INDEX);
				try (OutputStream out =
						new BufferedOutputStream(new FileOutputStream(oidx))) {
					pw.writeObjectSizeIndex(out);
				}
				oidx.setReadOnly();
