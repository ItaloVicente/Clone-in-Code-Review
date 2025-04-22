				super.partOpened(ref);
				assertEquals(editorId, ref.getId());
				IEditorReference[] refs = fPage.getEditorReferences();
				assertEquals(1, refs.length);
				assertEquals(editorId, refs[0].getId());
				eventReceived[1] = true;
			}
		};
		fPage.addPartListener(listener);
		fPage.addPartListener(listener2);
