package by.gurinovich.googletask.core.runner;

import org.testng.TestNG;
import org.testng.xml.XmlSuite;

import java.util.ArrayList;
import java.util.List;

public class TestRunner {
    public static void main(String[] args) {
        TestNG tng = new TestNG();

        XmlSuite xmlSuite = new XmlSuite();

        List<String> xmlFiles = new ArrayList<>();

        xmlFiles.add("./src/test/resources/test_suite.xml");

        xmlSuite.setSuiteFiles(xmlFiles);

        List<XmlSuite> xmlSuites = new ArrayList<>();
        xmlSuites.add(xmlSuite);

        tng.setXmlSuites(xmlSuites);

        tng.run();
    }
}