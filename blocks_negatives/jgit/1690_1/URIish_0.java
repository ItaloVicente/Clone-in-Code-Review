			user = matcher.group(2);
			pass = matcher.group(3);
			host = matcher.group(4);
			if (matcher.group(5) != null)
				port = Integer.parseInt(matcher.group(5));
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
