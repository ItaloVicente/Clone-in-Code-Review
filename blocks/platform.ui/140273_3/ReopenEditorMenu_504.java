		String fileName = name;
		String pathName = toolTip;
		if (pathName.equals(fileName)) {
			pathName = ""; //$NON-NLS-1$
		}
		IPath path = new Path(pathName);
		if (path.segmentCount() > 1 && path.segment(path.segmentCount() - 1).equals(fileName)) {
			path = path.removeLastSegments(1);
			pathName = path.toString();
		}

		if ((fileName.length() + pathName.length()) <= (MAX_TEXT_LENGTH - 4)) {
			sb.append(fileName);
			if (pathName.length() > 0) {
				sb.append("  ["); //$NON-NLS-1$
				sb.append(pathName);
				sb.append("]"); //$NON-NLS-1$
			}
		} else {
			int length = fileName.length();
			if (length > MAX_TEXT_LENGTH) {
				sb.append(fileName.substring(0, MAX_TEXT_LENGTH - 3));
				sb.append("..."); //$NON-NLS-1$
			} else if (length > MAX_TEXT_LENGTH - 7) {
				sb.append(fileName);
			} else {
				sb.append(fileName);
				int segmentCount = path.segmentCount();
				if (segmentCount > 0) {
					length += 7; // 7 chars are taken for " [...]"

					sb.append("  ["); //$NON-NLS-1$

					int i = 0;
					while (i < segmentCount && length < MAX_TEXT_LENGTH) {
						String segment = path.segment(i);
						if (length + segment.length() < MAX_TEXT_LENGTH) {
							sb.append(segment);
							sb.append(IPath.SEPARATOR);
							length += segment.length() + 1;
							i++;
						} else if (i == 0) {
							sb.append(segment.substring(0, MAX_TEXT_LENGTH - length));
							length = MAX_TEXT_LENGTH;
							break;
						} else {
							break;
						}
					}

					sb.append("..."); //$NON-NLS-1$

					i = segmentCount - 1;
					while (i > 0 && length < MAX_TEXT_LENGTH) {
						String segment = path.segment(i);
						if (length + segment.length() < MAX_TEXT_LENGTH) {
							sb.append(IPath.SEPARATOR);
							sb.append(segment);
							length += segment.length() + 1;
							i--;
						} else {
							break;
						}
					}

					sb.append("]"); //$NON-NLS-1$
				}
			}
		}
		final String process;
		if (rtl) {
			process = sb + " " + nm; //$NON-NLS-1$
		} else {
			process = nm + " " + sb; //$NON-NLS-1$
		}
		return TextProcessor.process(process, TextProcessor.getDefaultDelimiters() + "[]");//$NON-NLS-1$
	}

	@Override
