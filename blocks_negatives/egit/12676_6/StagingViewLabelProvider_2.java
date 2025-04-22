		if (fileNameMode) {
			IPath parsed = Path.fromOSString(c.getPath());
			if (parsed.segmentCount() > 1) {
				styled.append(parsed.lastSegment());
				if (suffix != null)
					styled.append(suffix, StyledString.DECORATIONS_STYLER);
				styled.append(' ');
				styled.append('-', StyledString.QUALIFIER_STYLER);
				styled.append(' ');
				styled.append(parsed.removeLastSegments(1).toString(),
						StyledString.QUALIFIER_STYLER);
