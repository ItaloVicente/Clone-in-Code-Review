			List<ChunkKey> e = edges != null ? edges.commitEdges : null;
			List<ChunkKey> s = sequentialHint(key
			if (e == null)
				e = Collections.emptyList();
			if (s == null)
				s = Collections.emptyList();
			if (!e.isEmpty() || !s.isEmpty()) {
				ChunkMeta.Builder m = edit(meta);
				ChunkMeta.PrefetchHint.Builder h = m.getCommitPrefetchBuilder();
				for (ChunkKey k : e)
					h.addEdge(k.asString());
				for (ChunkKey k : s)
					h.addSequential(k.asString());
				meta = m.build();
