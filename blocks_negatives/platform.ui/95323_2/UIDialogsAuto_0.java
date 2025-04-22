         * current perspective, or default; currently only //gets first
         * perspective in the registry. persp = new
         * Perspective((PerspectiveDescriptor)getWorkbench().getPerspectiveRegistry().getPerspectives()[0],
         * (WorkbenchPage)getWorkbench().getActiveWorkbenchWindow().getActivePage() );
         * dialog = new CustomizePerspectiveDialog(getShell(), persp); } catch
         * (WorkbenchException e) { dialog = null; }
         * DialogCheck.assertDialogTexts(dialog, this); if (persp != null) {
         * persp.dispose(); }
         */
