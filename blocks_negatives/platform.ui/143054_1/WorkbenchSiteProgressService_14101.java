        }
        try {
        	decrementBusy();
        } catch (Exception ex) {
        	WorkbenchPlugin.log(ex);
        }
    }

    @Override
