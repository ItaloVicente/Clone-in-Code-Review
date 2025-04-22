
		if (path.equals(pattern))
			if (dirOnly && !assumeDirectory)
				return false;
			else
				return true;

