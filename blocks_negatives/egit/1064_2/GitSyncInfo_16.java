					GitBlobResourceVariant baseBlob = (GitBlobResourceVariant) base;
					GitBlobResourceVariant remoteBlob = (GitBlobResourceVariant) remote;

					if (!baseBlob.getId().equals(remoteBlob.getId())) {
						description = calculateDescBasedOnGitCommits(baseBlob,
								remoteBlob);
