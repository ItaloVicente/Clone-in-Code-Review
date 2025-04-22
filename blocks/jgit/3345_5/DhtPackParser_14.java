			if (s == null)
				s = Collections.emptyList();
			if (!s.isEmpty()) {
				ChunkMeta.Builder m = edit(meta);
				ChunkMeta.PrefetchHint.Builder h = m.getTreePrefetchBuilder();
				for (ChunkKey k : s)
					h.addSequential(k.asString());
				meta = m.build();
			}
