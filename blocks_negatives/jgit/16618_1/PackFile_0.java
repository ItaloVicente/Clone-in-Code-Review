			if (packChecksum == null)
				packChecksum = idx.packChecksum;
			else if (!Arrays.equals(packChecksum, idx.packChecksum))
				throw new PackMismatchException(
						JGitText.get().packChecksumMismatch);

			bitmapIdx = idx;
