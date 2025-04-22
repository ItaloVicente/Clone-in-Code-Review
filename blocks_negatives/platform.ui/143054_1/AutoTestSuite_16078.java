            XMLMemento output = XMLMemento.createWriteRoot("errors");
            logger.getErrors().saveState(output);
            try {
                XmlUtil.write(errorsPath.toFile(), output);
            } catch (WorkbenchException e) {
                e.printStackTrace();
            }
        }
