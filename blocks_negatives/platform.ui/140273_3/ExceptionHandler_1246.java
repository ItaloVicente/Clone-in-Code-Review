            Workbench wb = Workbench.getInstance();
            if (wb != null) {
                wb.getAdvisor().eventLoopException(t);
            }
        } finally {
            exceptionCount--;
        }
    }
