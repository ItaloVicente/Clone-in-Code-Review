							ObjectId.fromRaw(digest.digest(packedRefsContent))
							packedRefsContent);
				}
			} catch (NoSuchFileException noPackedRefs) {
				if (packedRefsFile.exists()) {
					throw noPackedRefs;
				}
				return NO_PACKED_REFS;
			} catch (IOException e) {
