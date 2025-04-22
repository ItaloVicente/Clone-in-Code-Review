		if (presentation == StagingView.PRESENTATION_FLAT) {
			if (fileNameMode) {
				IPath parsed = Path.fromOSString(stagingEntry.getPath());
				if (parsed.segmentCount() > 1) {
					styled.append(parsed.lastSegment());
					if (suffix != null)
						styled.append(suffix, StyledString.DECORATIONS_STYLER);
					styled.append(' ');
					styled.append('-', StyledString.QUALIFIER_STYLER);
					styled.append(' ');
					styled.append(parsed.removeLastSegments(1).toString(),
							StyledString.QUALIFIER_STYLER);
				} else {
					styled.append(stagingEntry.getPath());
					if (suffix != null)
						styled.append(suffix, StyledString.DECORATIONS_STYLER);
				}
