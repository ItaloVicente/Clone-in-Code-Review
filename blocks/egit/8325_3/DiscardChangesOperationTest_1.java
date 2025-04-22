	@Test
	public void shouldWorkWhenProjectIsRootOfRepository() throws Exception {
		IFile file = project2.getFile(new Path("file.txt"));
		String contents = testUtils.slurpAndClose(file.getContents());
		assertEquals("initial", contents);
		setNewFileContent(file, "changed");

		DiscardChangesOperation dcop = new DiscardChangesOperation(new IResource[] { project2 });
		dcop.execute(new NullProgressMonitor());

		String replacedContents = testUtils.slurpAndClose(file.getContents());
		assertEquals("initial", replacedContents);
	}

	private static void trackAllFiles(IProject project, final TestRepository testRepository) throws CoreException {
		project.accept(new IResourceVisitor() {

			public boolean visit(IResource resource) throws CoreException {
				if (resource instanceof IFile) {
					try {
						testRepository
								.track(EFS.getStore(resource.getLocationURI())
										.toLocalFile(0, null));
					} catch (Exception e) {
						throw new CoreException(Activator.error(e.getMessage(),
								e));
					}
				}
				return true;
			}
		});
	}

