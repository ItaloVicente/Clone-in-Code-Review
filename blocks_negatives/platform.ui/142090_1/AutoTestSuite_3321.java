            XMLMemento output = XMLMemento.createWriteRoot("unknown");
            logger.getUnknownTests().saveState(output);
            try {
                XmlUtil.write(unknownPath.toFile(), output);
            } catch (WorkbenchException e) {
                e.printStackTrace();
            }
        }
    }
