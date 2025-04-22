			try {
				byte[] packedRefsContent = Files
						.readAllBytes(packedRefsFile.toPath());
				if (oldPackedRefs != null
						&& oldPackedRefs.packedRefsBytes != null
						&& Arrays.equals(
						oldPackedRefs.packedRefsBytes
					return oldPackedRefs;
				}

				try (BufferedReader br = new BufferedReader(
						new InputStreamReader(
								new ByteArrayInputStream(packedRefsContent)
