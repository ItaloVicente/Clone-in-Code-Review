
			final StringBuilder w = new StringBuilder();
			for (PackFile p : ((ObjectDirectory) odb).getPacks()) {
				w.append("P ");
				w.append(p.getPackFile().getName());
				w.append('\n');
			}
			writeFile("objects/info/packs"
