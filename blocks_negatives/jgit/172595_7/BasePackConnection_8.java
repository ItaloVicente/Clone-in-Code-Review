				name = name.substring(0, name.length() - 3);
				final Ref prior = avail.get(name);
				if (prior == null)
					throw new PackProtocolException(uri, MessageFormat.format(
							JGitText.get().advertisementCameBefore, name, name));

				if (prior.getPeeledObjectId() != null)

				avail.put(name, new ObjectIdRef.PeeledTag(
						Ref.Storage.NETWORK, name, prior.getObjectId(), id));
