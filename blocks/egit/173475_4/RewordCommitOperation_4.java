					newParents[i++] = newId != null ? newId : p.getId();
				}
				if (!hadNew) {
					continue;
				}
				committer = new PersonIdent(committer); // Update when
				builder = copy(c, newParents, committer, c.getFullMessage());
				if (gpgSigner != null
						&& (signAllCommits || c.getRawGpgSignature() != null)) {
					gpgSigner = sign(builder, gpgSigner, signAllCommits, keyId,
							committer, c.getCommitterIdent(), c);
