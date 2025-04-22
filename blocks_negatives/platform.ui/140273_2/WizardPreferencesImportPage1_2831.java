                    }
                }

                PreferenceTransferElement[] destTransfers = new PreferenceTransferElement[index];
                System.arraycopy(returnTransfers, 0, destTransfers, 0, index);
                return destTransfers;
            } catch (CoreException e) {
            } finally {
                try {
                    fis.close();
                } catch (IOException e) {
                    WorkbenchPlugin.log(e.getMessage(), e);
                }
            }
        }

        return new PreferenceTransferElement[0];
    }

    /**
     * Return whether or not the file is valid.
     * @return <code>true</code> of the file is an existing
     * file and not a directory
     */
    private boolean validFromFile() {
        File fromFile = new File(getDestinationValue());
        return fromFile.exists() && !fromFile.isDirectory();
    }

    @Override
