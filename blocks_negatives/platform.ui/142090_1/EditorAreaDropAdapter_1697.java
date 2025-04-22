                result = page.openEditor(new FileEditorInput(file), editorDesc
                        .getId(), true);
            }
        } catch (PartInitException e) {
            result = null;
        }
        return result;
    }
