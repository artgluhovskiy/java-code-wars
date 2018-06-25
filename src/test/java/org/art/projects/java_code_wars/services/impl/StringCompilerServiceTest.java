package org.art.projects.java_code_wars.services.impl;

import org.art.projects.java_code_wars.entities.JavaTask;
import org.art.projects.java_code_wars.services.JavaTaskService;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StringCompilerServiceTest {

    @Test
    public void compileTask() throws Exception {

        JavaTaskService javaTaskService = JavaTaskServiceImpl.getInstance();

        //Finding an appropriate task from DB for user
        JavaTask javaTask1 = javaTaskService.get(17);
        System.out.println(javaTask1);

        //Solution of the task
        String solution1 = "public class MaxFinder {\n" +
                           "    public int findMaxIndex(int[] array) {\n" +
                           "        int maxInt = array[0];\n" +
                           "        int maxIndex = 0;\n" +
                           "        for (int i = 1; i < array.length; i++) {\n" +
                           "            if (array[i] > maxInt) {\n" +
                           "                maxInt = array[i];\n" +
                           "                maxIndex = i;\n" +
                           "            }\n" +
                           "        }\n" +
                           "        return maxIndex;\n" +
                           "    }\n" +
                           "}";

        StringCompilerService stringCompiler = new StringCompilerService(false);
        StringCompilerService.TaskResults results1 = stringCompiler.compileTask(javaTask1, solution1);
        ResultsAnalyzer.analyzeResults(javaTask1, results1);

        assertEquals(javaTask1.getResult(), results1.getMethodResult());

        JavaTask javaTask2 = javaTaskService.get(18);
        System.out.println(javaTask2);

        //Solution of the task
        String solution2 = "public class Factorial {\n" +
                           "    public long factorial(int num) {\n" +
                           "        long fact;\n" +
                           "        if (num == 0) {\n" +
                           "            return 1;\n" +
                           "        }\n" +
                           "        fact = num * factorial(num - 1);\n" +
                           "        return fact;\n" +
                           "    }\n" +
                           "}";

        StringCompilerService.TaskResults results2 = stringCompiler.compileTask(javaTask2, solution2);
        ResultsAnalyzer.analyzeResults(javaTask2, results2);

        assertEquals(javaTask2.getResult(), results2.getMethodResult());
    }
}