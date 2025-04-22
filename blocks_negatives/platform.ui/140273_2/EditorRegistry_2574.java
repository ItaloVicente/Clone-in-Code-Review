            }
        } catch (IOException e) {
            return false;
        } catch (WorkbenchException e) {
            ErrorDialog.openError((Shell) null, WorkbenchMessages.EditorRegistry_errorTitle,
                    WorkbenchMessages.EditorRegistry_errorMessage,
                    e.getStatus());
            return false;
