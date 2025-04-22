      LOGGER.log(Level.INFO, "Successfully loaded properties file \"{0}\".",
        filename);
    } catch (Exception e) {
      LOGGER.log(Level.WARNING, "Could not load properties file \"{0}\" "
        + "because: {1}", new Object[]{filename, e.getMessage()});
    } finally {
      if (fs != null) {
        CloseUtil.close(fs);
      }
    }
