			final long sz = in.getChannel().size();
			if (sz > max)
				throw new IOException(MessageFormat.format(
						JGitText.get().fileIsTooLarge, path));
			final byte[] buf = new byte[(int) sz];
			IO.readFully(in, buf, 0);
