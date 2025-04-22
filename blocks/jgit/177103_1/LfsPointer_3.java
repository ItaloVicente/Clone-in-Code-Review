	private static boolean checkVersion(byte[] data) {
		try (BufferedReader r = new BufferedReader(
				new InputStreamReader(new ByteArrayInputStream(data)
			String s = r.readLine();
				return checkVersionLine(s);
			}
		} catch (IOException e) {
		}
		return false;
	}

	private static boolean checkVersionLine(String s) {
		if (s.length() < 8 || s.charAt(7) != ' ') {
		}
		String rest = s.substring(8).trim();
		return VERSION.equals(rest) || VERSION_LEGACY.equals(rest);
	}

