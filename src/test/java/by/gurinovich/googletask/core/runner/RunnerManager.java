package by.gurinovich.googletask.core.runner;

import org.apache.commons.cli.*;

class RunnerManager {

    private CommandLine cmd;

    void parseCommandLineForTestStart(String[] args) {
        Options options = new Options();
        options.addOption("pm", "parallel-mode", true, "Parallel mode");
        options.addOption("th", "thread-count", true, "Thread count");
        options.addOption("xml", "xml-suite", true, "Suite name");
        CommandLineParser parser = new PosixParser();
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    String getParallelMode() {
        return cmd.getOptionValue("pm");
    }

    int getThreadCount() {
        return Integer.parseInt(cmd.getOptionValue("th"));
    }

    String getSuitName() {
        return cmd.getOptionValue("xml");
    }
}
