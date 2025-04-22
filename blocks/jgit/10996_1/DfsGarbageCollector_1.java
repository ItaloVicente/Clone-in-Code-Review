	private List<DfsPackFile> packsToRebuild() throws IOException {
		DfsPackFile[] packs = objdb.getPacks();
		List<DfsPackFile> out = new ArrayList<DfsPackFile>(packs.length);
		for (DfsPackFile p : packs) {
			DfsPackDescription d = p.getPackDescription();
			if (d.getPackSource() != UNREACHABLE_GARBAGE)
				out.add(p);
			else if (d.getFileSize(PackExt.PACK) < coalesceGarbageLimit)
				out.add(p);
		}
		return out;
	}

