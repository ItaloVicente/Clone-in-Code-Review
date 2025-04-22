			final char[] tmp = new char[Constants.OBJECT_ID_STRING_LENGTH];
			for (final RevCommit a : assume) {
				w.write('-');
				a.copyTo(tmp
				if (a.getRawBuffer() != null) {
					w.write(' ');
					w.write(a.getShortMessage());
				}
				w.write('\n');
			}
			for (final Map.Entry<String
				e.getValue().copyTo(tmp
