			IEditorPart part2 = new TestKeyBindingMultiPageEditorPart(1);
			addPage(part2, getEditorInput());
		} catch (PartInitException e) {
			throw new RuntimeException(e);
		}
	}
