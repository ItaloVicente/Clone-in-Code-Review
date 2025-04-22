			final int crc32 = (int) crc.getValue();
			if (visit.id.crc != crc32)
				throw new IOException(MessageFormat.format(JGitText.get().corruptionDetectedReReadingAt

			PackedObjectInfo oe = visit.oe;
			if (oe == null) {
				objectDigest.update(Constants.encodedTypeString(type));
				objectDigest.update((byte) ' ');
				objectDigest.update(Constants.encodeASCII(visit.data.length));
				objectDigest.update((byte) 0);
				objectDigest.update(visit.data);
				tempObjectId.fromRaw(objectDigest.digest()

				verifySafeObject(tempObjectId
				oe = new PackedObjectInfo(pos
				addObjectAndTrack(oe);
			}
