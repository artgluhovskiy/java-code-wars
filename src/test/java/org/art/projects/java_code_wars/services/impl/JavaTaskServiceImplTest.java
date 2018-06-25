package org.art.projects.java_code_wars.services.impl;

import org.art.projects.java_code_wars.entities.DifficultyGroup;
import org.art.projects.java_code_wars.entities.JavaTask;
import org.art.projects.java_code_wars.entities.User;
import org.art.projects.java_code_wars.services.JavaTaskService;
import org.art.projects.java_code_wars.services.TaskOrderService;
import org.art.projects.java_code_wars.services.UserService;
import org.art.projects.java_code_wars.services.exceptions.ServiceBusinessException;
import org.junit.Test;

import java.sql.Date;

import static org.junit.Assert.assertEquals;

public class JavaTaskServiceImplTest {

    private JavaTaskService taskService = JavaTaskServiceImpl.getInstance();

    private UserService userService = UserServiceImpl.getInstance();

    private TaskOrderService orderService = TaskOrderServiceImpl.getInstance();

    @Test(expected = ServiceBusinessException.class)
    public void getFirstTaskByDiffGroup() throws Exception {

        int initUserAmount = userService.getAllUsers().size();
        //Creation of new user (BEGINNER)
        User user = new User("TestClan", "tester", "12345", "Test",
                "Test", "test@gmail.com", new Date(System.currentTimeMillis()), "user",
                "ACTIVE", UserServiceImpl.toSQLDate("24-02-1993"), DifficultyGroup.BEGINNER.toString());

        //User registration and increasing rating by 1
        user.setRating(user.getRating() + 1);
        userService.save(user);
        assertEquals(initUserAmount + 1, userService.getAllUsers().size());

        //User authentication
        user = userService.getUserByLogin("tester");
        assertEquals("tester", user.getLogin());

        //Getting the first task (with task ID = 0)
        JavaTask task1 = taskService.getNextTaskByDiffGroup(user, 0);
        assertEquals("Finding the index of max element in the array", task1.getShortDescr());

        //Solution of the task
        String userSolution1 = "public class MaxFinder {\n" +
                               "  public int findMaxIndex(int[] array) {\n" +
                               "      int maxInt = array[0];\n" +
                               "      int maxIndex = 0;\n" +
                               "      for (int i = 1; i < array.length; i++) {\n" +
                               "            if (array[i] > maxInt) {\n" +
                               "                    maxInt = array[i];\n" +
                               "                    maxIndex = i;\n" +
                               "            }\n" +
                               "      }\n" +
                               "      return maxIndex;\n" +
                               "  }\n" +
                               "}";

        //Compilation of the task
        StringCompilerService stringCompiler = new StringCompilerService(false);
        StringCompilerService.TaskResults results1 = stringCompiler.compileTask(task1, userSolution1);
        ResultsAnalyzer.analyzeResults(task1, results1);
        assertEquals(task1.getResult(), results1.getMethodResult());

        //Modifying user information in the database and acquiring new task (creation of new "NOT SOLVED" order)
        orderService.createNewOrder(user, task1, 0);

        //Acquiring of a new task (from "NOT SOLVED" task order)
        JavaTask task2 = taskService.getNotSolvedTask(user);

        //Solution of the second task
        String userSolution2 = "public class Factorial {\n" +
                               "    public long factorial(int num) {\n" +
                               "        long fact;\n" +
                               "        if (num == 0) {\n" +
                               "            return 1;\n" +
                               "        }\n" +
                               "        fact = num * factorial(num - 1);\n" +
                               "        return fact;\n" +
                               "    }\n" +
                               "}";

        //Compilation of the second task
        StringCompilerService.TaskResults results2 = stringCompiler.compileTask(task2, userSolution2);
        ResultsAnalyzer.analyzeResults(task2, results2);
        assertEquals(task2.getResult(), results2.getMethodResult());

        //Modifying user information in the database and acquiring new task (creation of new "NOT SOLVED" order)
        orderService.createNewOrder(user, task2, 0);

        //Acquiring of a new task (from "NOT SOLVED" task order)
        JavaTask task3 = taskService.getNotSolvedTask(user);

        //Solution of the second task
        String userSolution3 = "public class AdvancedArraySorting {\n" +
                               "    private int[] numbers;\n" +
                               "    private int number;\n" +
                               "    public int[] sort(int[] array) {\n" +
                               "        this.numbers = array;\n" +
                               "        this.number = array.length;\n" +
                               "        quickSort(0, number - 1);\n" +
                               "        return array;\n" +
                               "     }\n" +
                               "    private void quickSort(int low, int high) {\n" +
                               "        int i = low, j = high;\n" +
                               "        int pivot = numbers[low + (high-low)/2];\n" +
                               "        while (i <= j) {\n" +
                               "            while (numbers[i] < pivot) {\n" +
                               "                i++;\n" +
                               "            }\n" +
                               "            while (numbers[j] > pivot) {\n" +
                               "                j--;\n" +
                               "            }\n" +
                               "            if (i <= j) {\n" +
                               "                exchange(i, j);\n" +
                               "                i++;\n" +
                               "                j--;\n" +
                               "            }\n" +
                               "        }\n" +
                               "        if (low < j)\n" +
                               "            quickSort(low, j);\n" +
                               "        if (i < high)\n" +
                               "            quickSort(i, high);\n" +
                               "    }\n" +
                               "    private void exchange(int i, int j) {\n" +
                               "        int temp = numbers[i];\n" +
                               "        numbers[i] = numbers[j];\n" +
                               "        numbers[j] = temp;\n" +
                               "    }\n" +
                               "}";

        //Compilation of the second task
        StringCompilerService.TaskResults results3 = stringCompiler.compileTask(task3, userSolution3);
        ResultsAnalyzer.analyzeResults(task3, results3);

        assertEquals(task3.getResult(), results3.getMethodResult());

        //Modifying user information in the database and acquiring new task (creation of new "NOT SOLVED" order)
        orderService.createNewOrder(user, task3, 0);
    }
}