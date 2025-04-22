				cnt += r;
				if (keep)
					off += r;
			} while (cnt < inflatedSize);

			if (!inf.finished() || cnt != inflatedSize) {
				throw new CorruptObjectException(MessageFormat.format(JGitText
						.get().packfileCorruptionDetected
						JGitText.get().wrongDecompressedLength));
