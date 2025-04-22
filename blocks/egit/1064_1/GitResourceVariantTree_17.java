			TreeEntry treeEntry = tree.findBlobMember(resourcePath);
			fileEntry = (FileTreeEntry) treeEntry;
		} catch (IOException e) {
			throw new TeamException(
					NLS.bind(CoreText.GitResourceVariantTree_couldNotFindBlob,
							resource), e);
		}
