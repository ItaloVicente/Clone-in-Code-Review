								} catch (UnsafeCRLFConversionException e) {
									throw new UnsafeCRLFException(
											MessageFormat.format(
													JGitText.get().unsafeCrlfConversionIn
													new File(
															repo.getWorkTree()
															path).getPath())
