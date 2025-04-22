		ArrayList<int[]> linkRanges = new ArrayList<int[]>();
		ArrayList<String> links = new ArrayList<String>();
        
        
		while(urlSeparatorOffset >= 0) {
	
			boolean startDoubleQuote= false;
	
			int urlOffset= urlSeparatorOffset;
			char ch;
			do {
				urlOffset--;
				ch= ' ';
				if (urlOffset > -1)
					ch= s.charAt(urlOffset);
				startDoubleQuote= ch == '"';
			} while (Character.isUnicodeIdentifierStart(ch));
			urlOffset++;
			
	
			StringTokenizer tokenizer= new StringTokenizer(s.substring(urlSeparatorOffset + 3), " \t\n\r\f<>", false); //$NON-NLS-1$
			if (!tokenizer.hasMoreTokens())
				return null;
	
			int urlLength= tokenizer.nextToken().length() + 3 + urlSeparatorOffset - urlOffset;
	
			if (startDoubleQuote) {
				int endOffset= -1;
				int nextDoubleQuote= s.indexOf('"', urlOffset);
				int nextWhitespace= s.indexOf(' ', urlOffset);
				if (nextDoubleQuote != -1 && nextWhitespace != -1)
					endOffset= Math.min(nextDoubleQuote, nextWhitespace);
				else if (nextDoubleQuote != -1)
					endOffset= nextDoubleQuote;
				else if (nextWhitespace != -1)
					endOffset= nextWhitespace;
				if (endOffset != -1)
					urlLength= endOffset - urlOffset;
			}
			
			linkRanges.add(new int[] { urlOffset, urlLength });
			links.add(s.substring(urlOffset, urlOffset+urlLength));
			
		}
		return new AboutItem(s, linkRanges.toArray(new int[linkRanges.size()][2]),
				links.toArray(new String[links.size()]));
