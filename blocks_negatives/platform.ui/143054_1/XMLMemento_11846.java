   			}
   			print(sb.toString());
    	}

    	private void endTag(Element element) {
    		StringBuilder sb = new StringBuilder();
    		sb.append(element.getNodeName());
   			print(sb.toString());
    	}

    	private static void appendEscapedChar(StringBuilder buffer, char c) {
    		String replacement = getReplacement(c);
    		if (replacement != null) {
    			buffer.append('&');
    			buffer.append(replacement);
    			buffer.append(';');
    		} else if (c==9 || c==10 || c==13 || c>=32){
    			buffer.append(c);
    		}
    	}

    	private static String getEscaped(String s) {
    		StringBuilder result = new StringBuilder(s.length() + 10);
    		for (int i = 0; i < s.length(); ++i) {
