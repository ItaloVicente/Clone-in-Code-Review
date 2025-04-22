			return false;
		} catch (FileNotFoundException packGone) {
			return false;
		}
	}

	private PackFile[] getPacks() throws FileNotFoundException {
		if (packs == null) {
			PackFile[] p = new PackFile[packNames.length];
			for (int i = 0; i < packNames.length; i++)
				p[i] = getPackFile(packNames[i]);
			packs = p;
