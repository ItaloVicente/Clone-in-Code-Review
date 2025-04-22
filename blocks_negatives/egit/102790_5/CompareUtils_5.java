				int length;
				byte[] content;
				WorkingTreeOptions workingTreeOptions = repository.getConfig()
						.get(WorkingTreeOptions.KEY);
				AutoCRLF autoCRLF = workingTreeOptions.getAutoCRLF();
				switch (autoCRLF) {
				case FALSE:
					content = newContent;
					length = newContent.length;
					break;
				case INPUT:
				case TRUE:
					AutoLFInputStream in = new AutoLFInputStream(
							new ByteArrayInputStream(newContent), true);
					content = new byte[newContent.length];
					length = IO.readFully(in, content, 0);
					break;
				default:
					throw new IllegalArgumentException(
