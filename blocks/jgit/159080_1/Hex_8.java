			int left = Character.digit(s.charAt(i)
			int right = Character.digit(s.charAt(i + 1)

			if (left == -1 || right == -1) {
				throw new IllegalArgumentException(MessageFormat.format(
						JGitText.get().invalidHexString
						s));
			}

			b[i / 2] = (byte) (left << 4 | right);
