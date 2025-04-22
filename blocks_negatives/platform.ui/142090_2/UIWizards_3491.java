    /**
     * 1GJWD2E: ITPUI:ALL - Test classes should not be released in public packages.
     *
     public void testFileSystemExport() {
     Dialog dialog = exportWizard( DataTransferTestStub.newFileSystemResourceExportPage1(null) );
     DialogCheck.assertDialog(dialog);
     }
     public void testZipFileExport() {
     Dialog dialog = exportWizard( DataTransferTestStub.newZipFileResourceExportPage1(null) );
     DialogCheck.assertDialog(dialog);
     }
     */
        Dialog dialog = importWizard(null);
        DialogCheck.assertDialog(dialog);
    }
