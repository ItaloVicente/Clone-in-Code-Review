            }
        } catch (ParserConfigurationException e) {
            exception = e;
            errorMessage = WorkbenchMessages.XMLMemento_parserConfigError;
        } catch (IOException e) {
            exception = e;
            errorMessage = WorkbenchMessages.XMLMemento_ioError;
        } catch (SAXException e) {
            exception = e;
            errorMessage = WorkbenchMessages.XMLMemento_formatError;
        }

        String problemText = null;
        if (exception != null) {
