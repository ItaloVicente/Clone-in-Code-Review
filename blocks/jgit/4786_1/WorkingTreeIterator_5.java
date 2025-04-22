		InputStream rawis = current().openInputStream();
		InputStream is;
		if (getOptions().getAutoCRLF() != AutoCRLF.FALSE)
			is = new EolCanonicalizingInputStream(rawis);
		else
			is = rawis;
		return is;
