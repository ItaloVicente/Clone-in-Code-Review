			try {
				byte[] packedRefsContent = Files
						.readAllBytes(packedRefsFile.toPath());
				if (oldPackedRefs.hasTheSamePackedRefsBytes(packedRefsContent)) {
					return oldPackedRefs;
				}

				try (BufferedReader br = new BufferedReader(
						new InputStreamReader(
								new ByteArrayInputStream(packedRefsContent)
