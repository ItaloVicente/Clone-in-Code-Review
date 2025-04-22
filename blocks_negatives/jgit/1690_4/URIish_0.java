			path = matcher.group(6);
			if (path.length() >= 3
			&& path.charAt(0) == '/'
			&& path.charAt(2) == ':'
			&& (path.charAt(1) >= 'A' && path.charAt(1) <= 'Z'
			 || path.charAt(1) >= 'a' && path.charAt(1) <= 'z'))
				path = path.substring(1);
			else if (scheme != null && path.length() >= 2
					&& path.charAt(0) == '/' && path.charAt(1) == '~')
				path = path.substring(1);
