
        if (redactionLevel != RedactionLevel.NONE) {
            f.newInstance(name).info("Log redaction enabled. Previous log entries might not be redacted. " +
                    "Logs have reduced identifying information. Diagnosis and support of issues may be challenging " +
                    "or not possible in this configuration.");
        }

