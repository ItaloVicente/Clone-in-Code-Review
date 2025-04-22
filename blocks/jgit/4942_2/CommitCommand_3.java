							} catch (UnsafeCRLFConversionException e) {
								throw new UnsafeCRLFConversionException(
										MessageFormat.format(
												JGitText.get().unsafeCrlfConversionIn
												new File(repo.getWorkTree()
														path).getPath()));
