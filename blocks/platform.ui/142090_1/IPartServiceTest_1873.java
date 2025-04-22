				super.partOpened(part);
				assertEquals(editorId, part.getSite().getId());
				assertNotNull(fPage.findEditor(editorInput));
				IEditorPart[] editors = fPage.getEditors();
				assertEquals(1, editors.length);
				assertEquals(editorId, editors[0].getSite().getId());
				eventReceived[0] = true;
			}
		};
		IPartListener2 listener2 = new TestPartListener2() {
			@Override
