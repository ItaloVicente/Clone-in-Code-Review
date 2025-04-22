	private static String computeNewName(String str) {
		String fileNameNoExtension = str;
		Pattern p = Pattern.compile("[0-9]+$"); //$NON-NLS-1$
		Matcher m = p.matcher(fileNameNoExtension);
		if (m.find()) {
			int newNumber = Integer.parseInt(m.group()) + 1;
			return m.replaceFirst(Integer.toString(newNumber));
		}
		return fileNameNoExtension + "2"; //$NON-NLS-1$
