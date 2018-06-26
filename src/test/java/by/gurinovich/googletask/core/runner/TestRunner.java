package by.gurinovich.googletask.core.runner;

import org.testng.TestNG;
import org.testng.xml.XmlSuite;

import java.util.ArrayList;
import java.util.List;

public class TestRunner {

    public static void main(String[] args) {
        RunnerManager manager = new RunnerManager();
        manager.parseCommandLineForTestStart(args);
        TestNG tng = new TestNG();

        XmlSuite xmlSuite = new XmlSuite();

        List<String> xmlFiles = new ArrayList<>();

        xmlFiles.add("./src/test/resources/" + manager.getSuitName());

        xmlSuite.setSuiteFiles(xmlFiles);

        List<XmlSuite> xmlSuites = new ArrayList<>();
        xmlSuites.add(xmlSuite);
        XmlSuite.ParallelMode mode = XmlSuite.ParallelMode.getValidParallel(manager.getParallelMode());
        tng.setParallel(mode);
        tng.setThreadCount(manager.getThreadCount());
        tng.setXmlSuites(xmlSuites);

        tng.run();
    }
}