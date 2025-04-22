	private static String computeNewName(String str) {
		String fileExtension = ""; //$NON-NLS-1$
		String fileNameNoExtension = str;
		Pattern p = Pattern.compile("[0-9]+$"); //$NON-NLS-1$
		Matcher m = p.matcher(fileNameNoExtension);
		if (m.find()) {
			int newNumber = Integer.parseInt(m.group()) + 1;
			String numberStr = m.replaceFirst(Integer.toString(newNumber));
			return numberStr + fileExtension;
		}
		return fileNameNoExtension + "2" + fileExtension; //$NON-NLS-1$
